@echo off
chcp 65001
set TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEiLCJpYXQiOjE3NDA5OTQ4MjMsImV4cCI6MTgyNzM5NDgyM30.r6uHCIYxBFi63W8waT5WhrtNYp6RodvKMblcPAb8izw

echo.
echo === 测试获取用户信息 ===
curl.exe -X GET "http://localhost:8080/api/user/info?userId=4" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试更新用户信息 ===
curl.exe -X PUT "http://localhost:8080/api/user/info" -H "Authorization: Bearer %TOKEN%" -H "Content-Type: application/json" -d "{\"id\": 4, \"username\": \"testuser1\", \"name\": \"张三更新\", \"phone\": \"13800138000\", \"email\": \"zhangsan@example.com\", \"gender\": 1}"
echo.
echo.

echo === 测试更新密码 ===
curl.exe -X PUT "http://localhost:8080/api/user/password?userId=4&password=newpassword123" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试更新头像 ===
curl.exe -X PUT "http://localhost:8080/api/user/avatar?userId=4&avatar=http://example.com/avatar.jpg" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试检查用户名 ===
curl.exe -X GET "http://localhost:8080/api/user/check/username?username=testuser2" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试检查手机号 ===
curl.exe -X GET "http://localhost:8080/api/user/check/phone?phone=13800138001" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试获取用户列表 ===
curl.exe -X GET "http://localhost:8080/api/user/list" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试更新用户状态 ===
curl.exe -X PUT "http://localhost:8080/api/user/status?userId=4&status=1" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo === 测试重置密码 ===
curl.exe -X PUT "http://localhost:8080/api/user/reset/password?userId=4" -H "Authorization: Bearer %TOKEN%"
echo.
echo.

echo 测试完成！
pause 