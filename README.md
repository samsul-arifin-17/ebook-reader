<div align="center">

# 📚 E-Book Reader

### A modern Java desktop application for managing and reading your PDF library

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
![XAMPP](https://img.shields.io/badge/XAMPP-FB7A24?style=for-the-badge&logo=xampp&logoColor=white)

</div>

---

## ✨ Overview

**E-Book Reader** is a full-featured desktop application built with
**Java Swing** and **MySQL**. It lets you organize, search, and read
your entire PDF collection from one clean, modern interface —
with user accounts so every reader has their own personal library.

---


## 🚀 Features

| Feature | Description |
|---|---|
| 🔐 User Authentication | Secure login and registration system |
| 📂 Add PDF Books | Browse your computer and add any PDF file |
| 📊 Smart Dashboard | View all books with stats and status tracking |
| 🔍 Search | Instantly search books by title or author |
| ✅ Read Tracking | Mark books as Read or Unread |
| 🗑️ Delete Books | Remove books you no longer need |
| 📈 Stats Cards | See total, read, and unread count at a glance |
| 🎨 Modern UI | Clean two-panel design with smooth interactions |

---

## 🛠️ Tech Stack

```
Language      →  Java (JDK 8+)
GUI Framework →  Java Swing
Database      →  MySQL
Local Server  →  XAMPP
IDE           →  Apache NetBeans
Connector     →  MySQL Connector/J
```

---

## 📁 Project Structure

```
EBookReader/
│
├── src/
│   └── ebookreader/
│       ├── EBookReader.java        ← Main entry point
│       ├── DatabaseManager.java    ← All database operations
│       ├── LoginFrame.java         ← Login screen
│       ├── RegisterFrame.java      ← Account creation screen
│       ├── DashboardFrame.java     ← Main library dashboard
│       ├── AddBookDialog.java      ← Add new PDF book
│       └── PDFViewerFrame.java     ← Open and read PDF
│
├── sql/
│   └── setup.sql                   ← Database setup script
│
├── lib/
│   └── mysql-connector-j.jar       ← MySQL driver
│
└── README.md
```

---

## ⚙️ Requirements

Before running this project make sure you have:

- ✅ Java JDK 8 or higher — [Download here](https://www.oracle.com/java/technologies/downloads/)
- ✅ XAMPP installed and running — [Download here](https://www.apachefriends.org/)
- ✅ Apache NetBeans IDE — [Download here](https://netbeans.apache.org/)
- ✅ MySQL Connector JAR — [Download here](https://dev.mysql.com/downloads/connector/j/)

---

## 🗄️ Database Setup

**Step 1** — Open XAMPP Control Panel and start **Apache** and **MySQL**

**Step 2** — Open your browser and go to:
```
http://localhost/phpmyadmin
```

**Step 3** — Click **New** on the left side and create a database called:
```
ebookreader
```

**Step 4** — Click the **SQL** tab and run the script from `sql/setup.sql`

Or paste this directly:

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100),
    file_path VARCHAR(500) NOT NULL,
    file_size VARCHAR(20),
    status VARCHAR(20) DEFAULT 'Unread',
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (full_name, username, password)
VALUES ('Administrator', 'admin', 'admin123');
```

---

## ▶️ How to Run

**Step 1** — Clone this repository:
```bash
git clone https://github.com/samsul-arifin-17/ebook-reader.git
```

**Step 2** — Open NetBeans:
```
File → Open Project → Select the EBookReader folder
```

**Step 3** — Add MySQL Connector to Libraries:
```
Right click Libraries → Add JAR/Folder → Select mysql-connector-j.jar
```

**Step 4** — Start XAMPP and run the database setup script

**Step 5** — Press **F6** to run the project

---

## 🔑 Default Login

```
Username  →  admin
Password  →  admin123
```

You can also create your own account using the **Register** button on the login screen.

---

## 📌 Grading System

This app was built as part of a CSE project. The developer also
built a **Student Grade Calculator** — check it out here:

👉 [Student Grade Calculator](https://github.com/samsul-arifin-17/student-grade-calculator)

---

## 👨‍💻 Author

<div align="center">

**Samsul Arifin**


[![GitHub](https://img.shields.io/badge/GitHub-samsul--arifin--17-181717?style=for-the-badge&logo=github)](https://github.com/samsul-arifin-17)
[![Email](https://img.shields.io/badge/Email-smarifin17@gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:smarifin17@gmail.com)

</div>

---

<div align="center">

⭐ If you found this project helpful, please give it a star!

</div>
