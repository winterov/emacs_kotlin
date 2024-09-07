INSERT INTO notification_template (event_class, handler, template, is_enabled, created_at)
VALUES ('UserEmailApprovedTokenSend', 'EMAIL', '

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Добро пожаловать!</title>
  <style>
    body {
      margin: 0;
      padding: 10px;
      font-family: "Arial", sans-serif;
    }
    table {
      border-collapse: collapse;
      border-spacing: 0;
      width: 100%;
    }
    td {
      padding: 0;
    }
    h1 {
      font-size: 24px;
      font-weight: bold;
      margin-bottom: 20px;
    }
    p {
      font-size: 16px;
      line-height: 24px;
      color: #333333;
      margin-bottom: 20px;
    }
    a {
      display: inline-block;
      background-color: #007bff;
      color: #ffffff;
      text-decoration: none;
      padding: 10px 20px;
      border-radius: 5px;
      font-size: 16px;
    }
  </style>
</head>
<body>
  <table width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td align="center" style="padding: 20px 0;">
        <table width="600" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td>
              <h1>Добро пожаловать!</h1>
              <p>Пожалуйста, подтвердите ваш адрес электронной почты, чтобы получить доступ к нашему ресурсу.</p>
              <table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td align="center">
                    <a id="emailApprTag">Подтвердить почту</a>
                  </td>
                </tr>
              </table>
              <p>С уважением,<br>Команда Emacs</p>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>', true, current_timestamp)