# ChatCosSan
Полностью рабочий TCP чат

Возможность создавать как локальные так и глобальный чаты, при наличии хоста.
Реализовано:
1. Передача сообщений от пользователей
2. Серверные сообщения(подключение-отключение клиентов etc)
3. Полный GUI
4. Темная тема
5. Юинт-тесты для разработки

6а. Логирование зашифрованых сообщений со стороны сервера

6б. Логирование сообщений со стороны пользователя
7. RSA шифрование
8. Поддержка регистрации и логина пользователя
9. Поддержка базы данных для сервера(mySQL), сохранение в базе имён пользователей, паролей, сообщений, времени отправки
10. Прочий функционал 

Использованые библиотеки:
java.net.Socket;
javax.swing.*;
java.net.ServerSocket;
java.awt.*;
java.sql.Connection;
java.sql.DriverManager;
java.sql.PreparedStatement;
java.sql.SQLException;
java.sql.ResultSet;
javax.crypto.Cipher;
java.nio.charset.StandardCharsets;
java.security.*;
java.util.Base64;
java.time.LocalDateTime;
org.junit.jupiter.api.Assertions.*;
...
