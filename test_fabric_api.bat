@echo off
chcp 65001
setlocal

:: 设置JWT令牌
set JWT_TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEiLCJpYXQiOjE3NDEwMDc5ODksImV4cCI6MTgyNzQwNzk4OX0.z5-sFJSU0iZNUSpwTkrWG_iWFaZOqmBqDrHe0aibUy0

:: 设置基础URL
set BASE_URL=http://localhost:8080

echo 测试面料API接口
echo =====================

:: 1. 获取面料信息
echo.
echo 1. 获取面料信息（ID=1）
curl -X GET "%BASE_URL%/api/fabric/1" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 2. 获取面料列表
echo.
echo 2. 获取面料列表
curl -X GET "%BASE_URL%/api/fabric/list" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 3. 新增面料
echo.
echo 3. 新增面料
curl -X POST "%BASE_URL%/api/fabric" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "{\"code\":\"FB001\",\"name\":\"羊毛面料\",\"type\":4,\"color\":\"深蓝色\",\"pattern\":\"素色\",\"material\":\"100%%羊毛\",\"price\":299.99,\"stock\":1000,\"image\":\"/images/fabrics/FB001.jpg\",\"description\":\"高品质羊毛面料，手感柔软，穿着舒适\",\"status\":1}"

:: 4. 更新面料
echo.
echo 4. 更新面料
curl -X PUT "%BASE_URL%/api/fabric" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"精品羊毛面料\",\"color\":\"藏青色\",\"price\":399.99,\"description\":\"意大利进口高品质羊毛面料\"}"

:: 5. 删除面料
echo.
echo 5. 删除面料（ID=2）
curl -X DELETE "%BASE_URL%/api/fabric/2" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 6. 批量删除面料
echo.
echo 6. 批量删除面料
curl -X DELETE "%BASE_URL%/api/fabric/batch" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json" -d "[3,4]"

:: 7. 更新面料状态
echo.
echo 7. 更新面料状态（ID=1, 状态=1）
curl -X PUT "%BASE_URL%/api/fabric/status?id=1&status=1" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 8. 更新面料库存
echo.
echo 8. 更新面料库存（ID=1, 库存=800）
curl -X PUT "%BASE_URL%/api/fabric/stock?id=1&stock=800" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 9. 检查面料编码是否存在
echo.
echo 9. 检查面料编码是否存在
curl -X GET "%BASE_URL%/api/fabric/check/code?code=FB001" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

:: 10. 检查面料库存是否充足
echo.
echo 10. 检查面料库存是否充足
curl -X GET "%BASE_URL%/api/fabric/check/stock?id=1&quantity=100" -H "Authorization: Bearer %JWT_TOKEN%" -H "Content-Type: application/json"

echo.
echo 测试完成
pause 