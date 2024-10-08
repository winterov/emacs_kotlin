INSERT INTO email_properties (type,
                              description,
                              email,
                              password,
                              is_enabled,
                              smtp_host,
                              smtp_protocol,
                              smtp_require_auth,
                              smtp_port_ssl,
                              smtp_port_tls,
                              smtp_connection_timeout,
                              smtp_timeout,
                              incoming_server_type,
                              incoming_imap_server,
                              incoming_enabled_ssl,
                              incoming_port_ssl,
                              incoming_server_connection_timeout,
                              incoming_server_timeout)
VALUES ('ADMIN_SENDER',
        'Почта с которой производится рассылка уведомлений о смене пароля, истечении срока действия пароля, блокировки аккоунта и так далее',
        'test.marketplace20@gmail.com', 'nrfnxvozpeubwijm', true, 'smtp.gmail.com', 'SSL'
           , true, 465, 587,5000,5000, 'IMAP', 'imap.gmail.com',
        true, 993,5000,5000),
       ('MANAGER_TYPE',
        'Почта с которой производится рассылка уведомлений о записи на прием, отмены записи и любых других изменений связанных с раписанием премов',
        'test.marketplace20old@gmail.com', 'nrfnxvozpeubwijm', false, 'smtp.gmail.com', 'SSL', true, 465, 587,5000,5000, 'IMAP',
        'imap.gmail.com',
        true, 993,5000,5000)