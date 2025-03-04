@echo off
chcp 65001
setlocal

:: 设置JWT令牌
set JWT_TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEiLCJpYXQiOjE3NDEwMDc5ODksImV4cCI6MTgyNzQwNzk4OX0.z5-sFJSU0iZNUSpwTkrWG_iWFaZOqmBqDrHe0aibUy0

:: 设置基础URL
set BASE_URL=http://localhost:8080

echo 测试订单API接口
echo =====================

:: 1. 获取订单信息
echo.
echo 1. 获取订单信息（ID=1）
curl -X GET "%BASE_URL%/api/order/1" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 2. 获取订单列表
echo.
echo 2. 获取订单列表
curl -X GET "%BASE_URL%/api/order/list" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 3. 创建订单
echo.
echo 3. 创建订单
curl -X POST "%BASE_URL%/api/order" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "{\"userId\": 1, \"customizationId\": 1, \"amount\": 2999.99, \"payType\": 1, \"receiverName\": \"张三\", \"receiverPhone\": \"13800138000\", \"receiverAddress\": \"北京市朝阳区xxx街道\", \"remark\": \"尽快发货\"}"

:: 4. 更新订单
echo.
echo 4. 更新订单
curl -X PUT "%BASE_URL%/api/order" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "{\"id\": 1, \"receiverName\": \"李四\", \"receiverPhone\": \"13900139000\", \"receiverAddress\": \"上海市浦东新区xxx路\", \"remark\": \"请准时送达\"}"

:: 5. 删除订单
echo.
echo 5. 删除订单（ID=2）
curl -X DELETE "%BASE_URL%/api/order/2" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 6. 批量删除订单
echo.
echo 6. 批量删除订单
curl -X DELETE "%BASE_URL%/api/order/batch" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "[3, 4]"

:: 7. 更新订单状态
echo.
echo 7. 更新订单状态（ID=1, 状态=1）
curl -X PUT "%BASE_URL%/api/order/status?id=1&status=1" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 8. 订单支付
echo.
echo 8. 订单支付（ID=1）
curl -X PUT "%BASE_URL%/api/order/pay?id=1&payType=1&payTradeNo=PAY123456789" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 9. 订单发货
echo.
echo 9. 订单发货（ID=1）
curl -X PUT "%BASE_URL%/api/order/ship?id=1&trackingNo=SF1234567890" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 10. 订单确认收货
echo.
echo 10. 订单确认收货（ID=1）
curl -X PUT "%BASE_URL%/api/order/receive?id=1" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 11. 获取订单统计信息
echo.
echo 11. 获取订单统计信息
curl -X GET "%BASE_URL%/api/order/statistics" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

echo.
echo 测试完成
pause 