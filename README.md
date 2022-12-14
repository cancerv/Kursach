# Курсовой проект "Сетевой чат"

## Описание проекта

Разработано два приложения для обмена текстовыми сообщениями по сети с помощью консоли (терминала) между двумя и более пользователями. 

**Первое приложение - сервер чата**, ожидает подключения пользователей.

**Второе приложение - клиент чата**, подключается к серверу чата и осуществляет доставку и получение новых сообщений.

Все сообщения записываются в server.log на сервере и clien.log на клиенте.

### Требования к серверу

- Установка порта для подключения клиентов через файл настроек (для сервера - server_config.json;);
- Возможность подключиться к серверу в любой момент и присоединиться к чату;
- Отправка новых сообщений клиентам;
- Запись всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки.

### Требования к клиенту

- Выбор имени для участия в чате;
- Прочитать настройки приложения из файла настроек - например, номер порта сервера (для клиента - client_config.json);
- Подключение к указанному в настройках серверу;
- Для выхода из чата нужно набрать команду выхода - “/выход”;
- Каждое сообщение участников должно записываться в текстовый файл - client.log. При каждом запуске приложения файл должен дополняться.

## Реализация

Проект состоит из двух модулей: server и client.

Модуль server содержит код для запуска socket-сервера. Он обрабатывает подключения клиентов и пересылает сообщения.

Модуль ckient содержит код для запуска socket-клиента, который подключается к серверу. Клиент может обмениваться с сервером сообщениями.

Так же проект содержит модуль logger. Этот модуль предназначен для записи логов. Он используется обоими модулями server и client.

Проект собирается с помощью gradle.
