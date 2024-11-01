<header>

# Sports Booking Project
This is a web project submitted for the final assignment in the Database Lab (IT3290) course.

</header>

## Members:

- Trần Phúc Sơn 20225666  (Back-end Developer)
- Trần Tiến Dũng 20225614  (Front-end Developer)
- Nguyễn Tuấn Đạt 20225605  (Back-end Developer)
- Đặng Anh Đức 20225609  (Database Designer)

## Programming languages and frameworks used in this project:
- Front-end:
  ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
  ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
  ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
  ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
- Back-end:
  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
- Database:
  ![MicrosoftSQLServer](https://img.shields.io/badge/Microsoft%20SQL%20Server-CC2927?style=for-the-badge&logo=microsoft%20sql%20server&logoColor=white)
- Other:
  ![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
  ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
  ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

## Project's purpose:
With the development of society, the demand for entertainment among people is increasing. One of these forms of entertainment is playing sports. Through sports, individuals can relieve stress, maintain a healthy body, reduce the risk of illness, and also enhance confidence and sleep better. With these benefits in mind, we aim to create a venue that provides sports field rental services, including various types such as archery, tennis, badminton, etc. With the advancement of information technology, we want to allow customers to book tickets online through the internet in addition to selling tickets at the venue. This is why we want to create a website where customers can book sports fields online.<br></br>

### When customers book a field, there are several regulations they need to follow:
  1. Customers must register the booking and return time on the same day.
  2. Cancelling a booking is free. However, depending on the user's rank, they will be fined after about 3 cancellations or more.
  3. Customers cannot cancel a booking made within 2 days of the rental time.

### There are several promotions that customers can receive:
  1. First-time customers renting a field will receive a 20% discount.
  2. Discounts based on the number of hours rented:
     1. Customers renting a field for 4-6 hours will receive a 7% discount.
     2. Customers renting a field for 6 hours or more will receive a 10% discount.
  3. Promotions based on the customer's rank:
     1. Silver rank: Achieved when the total amount paid exceeds 1.5 million VND. These customers will receive a 5% discount and can book up to 10 slots in advance. 2
     2. Gold rank: Achieved when the total amount paid exceeds 3 million VND. These customers will receive an 8% discount and can book up to 10 slots in advance.
     3. Diamond rank: Achieved when the total amount paid exceeds 5 million VND. These customers will receive a 12% discount, can book an unlimited number of slots in advance, and are allowed to cancel a booking up to 1 day before the rental time.

### Functions for customers:
  1. Booking: Only registered account holders can book a field.
  2. Cancel booking: Cancellation is allowed at the latest 2 days before for non-diamond accounts. If multiple consecutive cancellations occur, customers will be fined 200,000 VND depending on their rank.
  3. Manage personal information: View and edit name and email.
  4. View information on booked fields and payment.

### Functions for manager:
  1. Manage fields: View the list of fields and change rental fees.
  2. Manage users: View the list of users, edit users' names and emails, and delete users.
  3. Manage transactions: View the list of transactions and update the status of transactions (Unpaid, Paid, Cancelled).

### ER Diagram:
  <p align="center">
    <img src=https://github.com/user-attachments/assets/2fb65272-49fb-4dc3-9d1a-770de4ecc40d alt=celebrate width=100% align=center>
  </p>

### Tables of relationship:
  <p align="center">
    <img src=https://github.com/user-attachments/assets/9c051389-5104-4416-b318-4441bf968d57 alt=celebrate width=100% align=center>
  </p>

## Some picture of the web:
### Customer:
- Menu
  <p align="center">
    <img src=https://github.com/user-attachments/assets/aa324251-1916-4308-8571-2aa7ed4ec0ab alt=celebrate width=100% align=center>
  </p>
- Login Page
  <p align="center">
    <img src=https://github.com/user-attachments/assets/dd3855d9-8db9-4c10-a7b3-351684c95bf3 alt=celebrate width=100% align=center>
  </p>
- Booking Page
  <p align="center">
    <img src=https://github.com/user-attachments/assets/bb74e8c6-a210-4c0c-a884-405b6c9b375b alt=celebrate width=100% align=center>
  </p>
- Profile Page
  <p align="center">
    <img src=https://github.com/user-attachments/assets/68c42b22-d07a-49cc-bcb8-3d8ee7ff2f55 alt=celebrate width=100% align=center>
  </p>
### Admin
- Customer Manage
  <p align="center">
    <img src=https://github.com/user-attachments/assets/572abc29-a886-4e79-aa93-09c6e7a59b23 alt=celebrate width=100% align=center>
  </p>
- Room Manage
  <p align="center">
    <img src=https://github.com/user-attachments/assets/02519b8f-42bb-4e9c-ae85-528ea591a14b alt=celebrate width=100% align=center>
  </p>
- Transaction Manage
  <p align="center">
    <img src=https://github.com/user-attachments/assets/da5dac53-bbce-4fc0-8f30-2e7370b1c337 alt=celebrate width=100% align=center>
  </p>

## How to run:
Install [Java](https://www.oracle.com/java/technologies/downloads/#java22), [Tomcat](https://tomcat.apache.org/download-10.cgi), [MS SQL Server](https://www.microsoft.com/en-us/sql-server/sql-server-downloads), [Intellij IDEA](https://www.jetbrains.com/idea/download/?section=windows)<br></br>
Setup JPA:
  - Open **SQL Server 2022 Configuration Manager**
  - Go to **SQL Server Netword Configuration** -> **Protocols for SQLEXPRESS**
  - Change **TCP/IP status** to **Enable**
  - Right click in **TCP/IP** then choose **Properties**
  - Go to **IP Addresses**
  - Find **IPALL**
  - Change **TCP Port** to **1433**
  - Open **SQL Server Management Studio 19**
  - Right click on your computer database (DESKTOP-...) then choose **Properties**
  - Go to **Security** tab
  - Choose **SQL Server and Windows Authentication mode**
  - Then click **OK**
  - Then open **Security** folder -> open **Login** folder
  - Right click on profile named **sa** then choose **Properties**
  - Enter your password for this profile
  - Then go to **Status** tab -> **Enable Login**
  - Go back **SQL Server 2022 Configuration Manager**
  - Go to **SQL Server Services**
  - Right click on your database then restart it
Run Project:
  - Open **IntelliJ IDEA** then choose **Open project**
  - Select the path to the project folder you downloaded
  - Press the **Shift + F10** keys to run the project.
  - Open your browser and go to http://localhost:8080/ to open website.
