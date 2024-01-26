# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0-dev+012] - 2024-01-26

### Changed
 - Database table names are now in plural form

### Fixed
 - Foreign keys were reestablished in the database
 - User can no longer enter invalid data in EditText fields
 - AdministratorsManageFragment now displays the correct data
 - UserAdapter buttons are now fully functional

## [1.0.0-dev+011] - 2024-01-21

### Changed
 - Project now follows MVVM architecture : 
     - Models handle data
     - Views display data
     - ViewModels handle logic and communication between models and views
 - Project now uses LiveData and DataBinding to display data in views
 - Project now uses Room to handle database access
 - MixedAdapter was broken down into three separate adapters, one for events, one for clubs and one for users

### Added
 - ViewModels, Repositories and DAOs for every model
 - AppDatabase class to handle database creation and access

### Removed
 - DataBaseHelper class
 - MixedAdapter class

## [1.0.0-dev+010] - 2023-12-30

### Changed
 - Project structure is now only one activity with multiple fragments

### Added
 - Fragments for every different page

### Removed
 - All activities except for the main one

## [1.0.0-dev+009] - 2023-12-10

### Added
 - `Delete` button on the `EventPageActivity`
 - `Back` button on the `ManageAdministratorsActivity`

### Fixed
 - It is no longer possible to create a second user with the same email address

## [1.0.0-dev+008] - 2023-12-10

### Added
 - RecyclerView displaying users in the `ManageAdministratorsActivity`
 - Inserting three users with three different levels of rights on creation of the database

### Removed
 - Administrator table and model

### Changed
 - Introducing database version 12
 - User table and model now have a `rights` column and variable

### Fixed
 - Changes in the database made through clicking on RecyclerView elements now show up in the main page without needing to relaunch the app
 - `Log out` button is now fully functional
 - `ManageAdministratorsActivity` is now fully functional, and allows administrators to manage user rights

## [1.0.0-dev+007] - 2023-12-09

### Changed
 - Now on our database version 4
 - Some creation and editing changes now show up immediately on screen without needing to restart the app (still need to fix a few in the future)
 - Events now show up on the main page only when subscribed to the club organizing them

### Added
 - `Attendance` table, containing data on user attendance to events
 - `Subscribe` and `Unsubscribe` buttons were added on club profiles
 - `Attend` button and counter were added on event pages, as well as a `Cancel` button
 - `Delete profile` button was added on club profiles

## [1.0.0-dev+006] - 2023-12-06

### Changed
 - `LogInActivity` and `SignInActivity` are now fully operationnal and can send/retrieve data from the database
 - `LogInActivity` is now the launching activity
 - The database is now on version 3, where a descritpion was added to the user table and an email address was added to the club table
 - `UserProfile`, `EditUserActivity` and `UserModel` were updated to match the new features (log in/sign in, database v3)

## [1.0.0-dev+005] - 2023-12-05

### Added
 - `EditEventActivity` file was added, editing the data stored in the database for a selected event

### Changed
 - `EditUserActivity` and `EditClubActivity` are now both operational, editing the data stored in the database for a selected element
 - .idea file was removed from the repository

### Fixed
 - The `capacity` attribute of the `EventModel` model class is now correctly used as an int in other activities

## [1.0.0-dev+004] - 2023-12-05

### Changed
 - `CustomAdapter` java file was renamed `MixedAdapter`, and is now fully working, displaying a list of clubs and a list of events on two different RecycleView elements in the main page
 - It is now possible to access the details of an event by clicking on it in the main page

## [1.0.0-dev+003] - 2023-12-04

### Added
 - New necessary activity files for editing and adding clubs/events/users, and their corresponding layout files
 - A Custom adapter java file to display lists of models in the RecyclerViews

### Changed
 - Most activities are now completely interactive in-app

## [1.0.0-dev+002] - 2023-11-30

### Added

 - DataBaseHelper file, giving access to a writable database and creating tables on creation
 - Every activity planned for now, along with their layout files. 
Layout files are more or less completed, they will need adjustments but they are mostly functional already.

## [1.0.0-dev+001] - 2023-11-26

### Added

 - This CHANGELOG file 
 - A LICENCE file to disclose the project's software licence
 - Object Models for every future table in the database
