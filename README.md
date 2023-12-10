# EsieaBoard

## Overview

This project involves the development of an Android application designed to facilitate communication and enhance visibility for student clubs and associations. Originally created to address the specific requirements of students at the French computer engineering school ESIEA, the application has the potential to be adapted for broader use through a few modifications. 
The fundamental structure of the application includes a main board, allowing users to engage with a list of clubs and events.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## Features

 - **Club and Event Listings**: The main board serves as a centralized hub where users can seamlessly explore an extensive array of student clubs and upcoming events. Users can browse through the listings, gaining quick insights into each club's description, its upcoming events, and the ability to directly access more detailed information.
 - **Event Management**: Club administrators have robust tools for managing events efficiently. They can effortlessly create new events, edit existing ones, and even cancel events if necessary. Additionally, users can easily interact with events, providing organizers with a clear idea of attendance and enabling better event planning.
 - **User Interaction**: Users can actively engage with the community by following clubs of interest and indicating their participation in upcoming events. The application fosters a sense of community involvement through these interactions, allowing users to stay connected with the clubs and events that align with their preferences.
 - **Customization**: Users have the ability to personalize their profiles by providing a brief description about themselves. While the customization options are currently limited to descriptions, this feature allows users to express their interests and create a more personalized profile within the app.
 - **Admin Features**: Administrators wield key functionalities to manage the application effectively. They can create new clubs, schedule events, and regulate user permissions within the app. Admins play a crucial role in maintaining the integrity of the platform and ensuring a smooth user experience.
 - **Technologies Used**: The application utilizes Java for the front end, providing a robust and familiar user interface for Android users. The backend is powered by SQLite, a lightweight and efficient relational database management system. This combination allows for seamless data storage and retrieval, contributing to the overall performance of the application.

## Prerequisites

- Android Studio or IntelliJ installed
- Basic knowledge of Android development
- SQLite database basics

## Installation
1. Have Android Studio or IntelliJ installed on your computer
2. If using IntelliJ, make sure you already have the `Android plugin` installed, and if using Android Studio, make sure your software is up to date
3. Clone the repository: `git clone https://github.com/Nihaldhara/EsieaBoard.git`
4. Set your project Gradle version to be 8.1.0
5. Set your project SDK to be jbr-17, JetBrains Runtime version 17.0.9 : File -> Project Structure -> Project -> SDK -> Add SDK -> Download Android SDK

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/e5240fa5-5b30-4c02-8339-b6c4db363f14)
   
6. Build and run the application on an emulator or physical device

## Usage
This application's database is initialized with three default users, each with different administrator rights levels. Here is a guide on how each type of user works, and how to create your own clubs and events board ! 

**/!\ In the following examples, the application has already been used to create Clubs and Events. In the version you are downloading, these clubs and events are for you to create, using the `admin` user ! Therefore, the board will be empty upon opening the app for the first time. Enjoy !**

`User` admnistration level : 
This is the base user level of this application. As a simple `user`, you can browse the already created clubs, follow their profiles, and see what kind of events they are planning for the future. You can also decide to attend those events, and notify the club.
This user can also personnalize their profile. 
To access this user profile, here are the credentials : `email: jdutronc@et.esiea.fr | password: password`

Here is an overview of what the `user` experience is like :

Here is what the `Main Board` looks like for `Jacques Dutronc`, a great and experienced singer, following the `Music Club` of his school :

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/3c0911ba-31a0-41f1-b0f2-2e9e1191e7ab)

Jacques is really interested in joining the `Harp Class` offered by his favorite club, so he chooses to press on this event, and see what comes next.

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/72ddf085-8a9d-432b-848c-123dec239244)

Now, all that is left for Jacques to do, is to click on the `Attend` button, and he will be all set to join the event on the given date! How exciting. Now, back to the main page, Jacques is interested in seeing what this `BDE` club is... let's check it out.

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/4162c067-7e62-4d13-a194-25b0957e74a0)

Also interested in this kind of activity, Jacques only has one thing to do to be updated on this club's events in his main page, and that is to click on the `Subscribe` button.
Now, if Jacques wants to update his profile, and change his name to look more mysterious, or change his email address because he lost the original one... no worries! He just has to navigate to his user profile, by clicking on his profile picture in the top left corner. He can then press the `Modify` button to edit his profile, or `Log Out` button to leave his account.

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/bbbd3165-69fa-41fb-a4f9-dd6d64893de5)


### `Member` administration level : 
This is the second user level of this application. As a club `member`, you can now edit club profiles, and organize the events. All the basic user functionalities are of course still available.
To access this user profile, here are the credentials : `email: hjackman@et.esiea.fr | password: password`

Here is an overview of what the club `member` experience is like : 

For `Hugh Jackman`, this `Main Board` is not very different to Jacques'... but, when Hugh decides view `Cinema Club`, his favorite club profile, you'll notice that he can do something more!

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/846d4ccb-ce3d-46b3-bc31-c67bb1a62700)

Hugh can now organize a `New` event! As a big fan of the X-men franchise, he decides to offer his subscribers a marathon for these movies.

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/1129a980-a399-4514-b225-b85df63e5fd6)

He can also `Modify` the club's information, by using the top right corner button. The same goes for events, which he can `Edit`.

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/d85c24a9-dab0-42e0-a912-f7cce67cd233)


### `Admin` administration level :
This is the top user level of this application. As an `admin`, you can now do all of the above, but also organize the clubs, and manage administrator rights for every other user ! Congratulations, you have reached the top of the food chain !
To access this user profile, here are the credentials : `email: ojoly@et.esiea.fr | password: password`

Here is an overview of what the `admin` experience is like :

Now, let's explore my own profile, which is again slightly different! As a music and cinema lover, you'll see that I am subscribed to both the Music and Cinema Club. But you can also that on top of our club list, I have a `new` button present! 

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/940a0c36-e38c-48ab-b5f4-8734a4fc665b)

This here allows me to create new clubs, and add them to this list, by redirecting me on the `Club Editor`
I also have access to another button, on my `User Profile` :

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/c437fbe3-a1c5-474a-81ba-d905928656a2)

Thanks to this `Manage` button, I can now also manage other user rights, and promote or downgrade other user !

![image](https://github.com/Nihaldhara/EsieaBoard/assets/93861339/923c67dc-73af-4670-9a45-4395e868ac18)


It is now the end of this overview! I hope my explanations are enough for you to navigate through my application, and you have a great time using it!


## Database Schema

```sql
CREATE TABLE Users(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name VARCHAR(128),
    last_name VARCHAR(128),
    email_address VARCHAR(320) NOT NULL,
    password_hash VARCHAR(72) NOT NULL,
    description TEXT,
    rights INT NOT NULL
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
        REFERENCES Clubs (id) ON DELETE CASCADE
);

CREATE TABLE Subscriptions(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INT NOT NULL,
    club_id INT NOT NULL,
    nature INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (club_id)
        REFERENCES Clubs (id) ON DELETE CASCADE
);

CREATE TABLE Attendance(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INT NOT NULL,
    event_id INT NOT NULL,
    status INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id)
        REFERENCES Events (id) ON DELETE CASCADE
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
