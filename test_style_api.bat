@echo off
chcp 65001
set TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEiLCJpYXQiOjE3NDEwMDI4ODYsImV4cCI6MTgyNzQwMjg4Nn0.ppoPxka3f5Q1Zb_pUdVtbXEhGHSUW-jUdPfME2pzv2g

echo.
echo === 测试获取款式信息 ===
curl.exe -X GET "http://localhost:8080/api/style/1" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试获取款式列表 ===
curl.exe -X GET "http://localhost:8080/api/style/list" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试新增款式 ===
curl.exe -X POST "http://localhost:8080/api/style" -H "Authorization: Bearer %TOKEN%" -H "Content-Type: application/json" -d "{\"name\": \"经典双排扣西装\", \"code\": \"STYLE001\", \"type\": 1, \"image\": \"http://example.com/images/style001.jpg\", \"model\": \"http://example.com/models/style001.obj\", \"price\": 1999.99, \"description\": \"经典双排扣西装，采用优质面料制作\", \"status\": 1}"
echo.
echo.

echo === 测试更新款式 ===
curl.exe -X PUT "http://localhost:8080/api/style" -H "Authorization: Bearer %TOKEN%" -H "Content-Type: application/json" -d "{\"id\": 1, \"name\": \"经典双排扣西装(更新)\", \"code\": \"STYLE001\", \"type\": 1, \"image\": \"http://example.com/images/style001_update.jpg\", \"model\": \"http://example.com/models/style001_update.obj\", \"price\": 2199.99, \"description\": \"经典双排扣西装，采用优质面料制作，更新版本\", \"status\": 1}"
echo.
echo.

echo === 测试删除款式 ===
curl.exe -X DELETE "http://localhost:8080/api/style/2" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试批量删除款式 ===
curl.exe -X DELETE "http://localhost:8080/api/style/batch" -H "Authorization: Bearer %TOKEN%" -H "Content-Type: application/json" -d "[3, 4, 5]"
echo.
echo.

echo === 测试更新款式状态 ===
curl.exe -X PUT "http://localhost:8080/api/style/status?id=1&status=0" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试检查款式编码是否存在 ===
curl.exe -X GET "http://localhost:8080/api/style/check/code?code=STYLE001" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo 测试完成！
pause 