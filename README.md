# Hotel Booking Service (Spring)

 Система Заказ гостиницы. Клиент заполняет Заявку, указывая количество мест в номере, класс апартаментов и время пребывания. Администратор просматривает поступившую Заявку, выделяет наиболее подходящий из доступных Номеров, после чего система выставляет Счет Клиенту. 

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
