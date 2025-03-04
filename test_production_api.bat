@echo off
chcp 65001
setlocal

:: 设置JWT令牌
set JWT_TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEiLCJpYXQiOjE3NDEwMDcxMzEsImV4cCI6MTgyNzQwNzEzMX0.QWnZtHbvtBIl6tfI5AIbCYwqEtDblxEUGw6RpL9m-gY





:: 设置基础URL
set BASE_URL=http://localhost:8080

echo 测试生产状态API接口
echo =====================

:: 1. 获取生产状态信息
echo.
echo 1. 获取生产状态信息（ID=1）
curl -X GET "%BASE_URL%/api/production/1" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 2. 根据订单ID获取生产状态
echo.
echo 2. 根据订单ID获取生产状态（订单ID=1）
curl -X GET "%BASE_URL%/api/production/order/1" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 3. 获取生产状态列表
echo.
echo 3. 获取生产状态列表
curl -X GET "%BASE_URL%/api/production/list" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 4. 创建生产状态
echo.
echo 4. 创建生产状态
curl -X POST "%BASE_URL%/api/production" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "{\"orderId\": 1, \"orderNo\": \"ORD20240301001\", \"stage\": 1, \"status\": 1, \"handler\": \"张三\", \"remark\": \"开始裁剪\"}"

:: 5. 更新生产状态
echo.
echo 5. 更新生产状态
curl -X PUT "%BASE_URL%/api/production" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "{\"id\": 1, \"stage\": 2, \"status\": 1, \"handler\": \"李四\", \"remark\": \"开始缝制\"}"

:: 6. 删除生产状态
echo.
echo 6. 删除生产状态（ID=2）
curl -X DELETE "%BASE_URL%/api/production/2" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 7. 批量删除生产状态
echo.
echo 7. 批量删除生产状态
curl -X DELETE "%BASE_URL%/api/production/batch" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "[3, 4]"

:: 8. 更新生产状态
echo.
echo 8. 更新生产状态（ID=1, 状态=2）
curl -X PUT "%BASE_URL%/api/production/status?id=1&status=2" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 9. 更新生产阶段
echo.
echo 9. 更新生产阶段（ID=1, 阶段=2）
curl -X PUT "%BASE_URL%/api/production/stage?id=1&stage=2" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

echo.
echo 测试完成
pause 