<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ResetPassword</title>
</head>
<body>
    <form action="reset" method="POST">
        <table>
            <tr>
                <td>新密码：</td>
                <td><input type="password" name="newpassword" id=""></td>
            </tr>
            <tr>
                <td>确认新密码：</td>
                <td><input type="password" name="check-newpassword" id=""></td>
            </tr>
        </table>
        <input type="submit" value="确认修改">
    </form>
</body>
</html>