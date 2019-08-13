# Hotel Booking Service (Spring)

 Система Заказ гостиницы. Клиент заполняет Заявку, указывая количество мест в номере, класс апартаментов и время пребывания. Администратор просматривает поступившую Заявку, выделяет наиболее подходящий из доступных Номеров, после чего система выставляет Счет Клиенту. 

## Установка

Шаг 1. Скачайте репозиторий
Шаг 2. База данных.
```
mysql -u root -p < src/main/resources/data.sql
```
Создается новый пользователь и база данных. 
Также в базе данных будут созданы два пользователями приложения - admin(пароль - 123) и user(пароль - 123).

## Запуск

```
mvn spring-boot:run
```

Если возникают проблемы с запуском из-за доступа к базе данных (java.sql.SQLNonTransientConnectionException: Public Key Retrieval is not allowed), попробуйте зайти в mysql под новым пользователем (пароль: 12345) и выйти:

```
 mysql -u newuser -p
 mysql> exit
 Bye
```


В случае успешного запуска, сайт будет отображаться на порту 8090:

http://localhost:8090/

## Features

Использовано:
 - Java 8:
   - Streams
 - Spring:
    - Spring Boot
    - Spring Security
    - Conditionals (disable Spring Security with 'security.enabled=false' configuration)
 - Hibernate
 - Thymeleaf + HTML + CSS + Bootstrap

 - Шаблоны:
        - Шаблонный метод
        - Делегат
        - Builder
        
 - Двуязычный сайт (поддерживает укр и англ; язык по умолчанию - укр)
 - Поддерживает интернациональные имена пользователей：
    - Хранение в базе данных:
    
        ![alt text](img/1.png)
    - Авторизация на сайте: 
    
        ![alt text](img/2.png)
        
        ![alt text](img/3.png)
 - Password encrypted (BCrypt)
