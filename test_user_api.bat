@echo off
set TOKEN=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEiLCJpYXQiOjE3NDA5OTQ5NzgsImV4cCI6MTgyNzM5NDk3OH0.b-qj7Er__c0P-W5RYgcgkkkLegmAMO-pNW-EhdEX0jE

echo Testing get user info...
curl.exe -X GET "http://localhost:8080/api/user/info?userId=4" -H "Authorization: Bearer %TOKEN%"
echo.

echo Testing update user info...
curl.exe -X PUT "http://localhost:8080/api/user/info" -H "Authorization: Bearer %TOKEN%" -H "Content-Type: application/json" -d '{"id":4,"username":"testuser1","name":"ZhangSan","phone":"13800138000","email":"zhangsan@example.com","gender":1}'
echo.

echo Testing update password...
curl.exe -X PUT "http://localhost:8080/api/user/password?userId=4&password=newpassword123" -H "Authorization: Bearer %TOKEN%"
echo.

echo Testing update avatar...
curl.exe -X PUT "http://localhost:8080/api/user/avatar?userId=4&avatar=http://example.com/avatar.jpg" -H "Authorization: Bearer %TOKEN%"
echo.

echo Testing check username...
curl.exe -X GET "http://localhost:8080/api/user/check/username?username=testuser2" -H "Authorization: Bearer %TOKEN%"
echo.

echo Testing check phone...
curl.exe -X GET "http://localhost:8080/api/user/check/phone?phone=13800138001" -H "Authorization: Bearer %TOKEN%"
echo.

echo Testing get user list...
curl.exe -X GET "http://localhost:8080/api/user/list" -H "Authorization: Bearer %TOKEN%"
echo.

echo Testing update user status...
curl.exe -X PUT "http://localhost:8080/api/user/status?userId=4&status=1" -H "Authorization: Bearer %TOKEN%"
echo.

echo Testing reset password...
curl.exe -X PUT "http://localhost:8080/api/user/reset/password?userId=4" -H "Authorization: Bearer %TOKEN%"
echo.

pause 