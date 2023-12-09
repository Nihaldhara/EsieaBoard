# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
