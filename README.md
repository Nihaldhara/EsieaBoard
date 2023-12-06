# EsieaBoard

## Overview

This project is an Android application aiming to help student clubs and associations with communication and exposure.
Currently a student in the french computer engineering school ESIEA, the app was first designed to fit our specifical needs. However, I believe its use could be expanded to a lot more with just a few modifications.
The base structure of this application consists of a main board, where users can interact with a list of clubs and events. 

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## Features

- Feature 1: 

## Prerequisites

- Android Studio or IntelliJ installed
- Basic knowledge of Android development
- SQLite database basics

## Installation
1. Clone the repository: `git clone https://github.com/Nihaldhara/EsieaBoard.git`
2. Open the project in Android Studio or IntelliJ
3. Build and run the application on an emulator or physical device

## Usage
- 

## Database Schema

```sql
CREATE TABLE Users(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name VARCHAR(128),
    last_name VARCHAR(128),
    email_address VARCHAR(320) NOT NULL,
    password_hash VARCHAR(72) NOT NULL,
    description TEXT
);

CREATE TABLE Clubs(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(128) NOT NULL,
    email_address VARCHAR(128) NOT NULL,
    description TEXT
);

CREATE TABLE Events(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    club_id INT NOT NULL,
    name VARCHAR(128) NOT NULL,
    description TEXT NOT NULL,
    date DATETIME NOT NULL,
    location VARCHAR(128) NOT NULL,
    capacity INT,
    FOREIGN KEY (club_id)
        REFERENCES clubs (id)
);

CREATE TABLE Administrators(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INT NOT NULL,
    club_id INT NOT NULL,
    rights INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (club_id)
        REFERENCES clubs (id)
);

CREATE TABLE Subscriptions(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INT NOT NULL,
    club_id INT NOT NULL,
    nature INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (club_id)
        REFERENCES clubs (id)
);
```

## Contributing

1. Fork the repository
2. Create a new branch: `git checkout -b feature-name`
3. Implement your feature or bug fix
4. Test your changes thoroughly
5. Submit a pull request

## License 

This project is licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html) - see the [LICENSE](https://github.com/Nihaldhara/EsieaBoard/blob/master/LICENSE) file for details.
