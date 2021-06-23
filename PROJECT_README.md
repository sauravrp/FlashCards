# Overview
## Context

BigSpring is a global learning platform, and has learners consuming educational content in a [wide variety of languages](https://en.wikipedia.org/wiki/Languages_of_India#Official_languages). One of the types of educational content we deliver is a [flashcard](https://en.wikipedia.org/wiki/Flashcard). Currently, content is developed in a single language. As [literacy is an issue](https://en.wikipedia.org/wiki/Literacy_in_India) in several parts of the world, ensuring that content is fully [localized](https://en.wikipedia.org/wiki/Language_localisation) into regional languages would increase overall platform engagement in addition to improving individual learning outcomes.

## Requirements

Design and implement a Flash Card content management system. This can be an iOS, Android or web app. This will be a “backend” app, used by content administrators in order to begin developing a localized library of flash card content.

Our content library will have a concept of “supported” or “targeted” languages: Given the entire universe of languages, we will only aim to localize our content into a subset in order to support the most popular languages within the regions that BigSpring’s users are located in.

In Scope Features:
- Add a new “supported” language
- Create a flash card
- Localize flash card into all “supported” languages (acceptable if happens as part of creation)
- View localized flash cards

Out of Scope Features:
- Update and Deletion of flash cards (we’re very good at localization!)
- If a new “supported” language is added, any existing flash cards localized into the previous set of “supported” languages can remain as-is, i.e. there is no need to propagate the update.


## User Stories

- As a content administrator, I want to create a flash card and localize it into some of the BigSpring “supported” languages.
- As a content administrator, I want to add a new “supported” language in order to localize new content into new regions that the business is expanding into.

## Deliverables

Commit your implementation directly to this repository. Your implementation should contain documentation on how to execute the project.

# Focus Area
My primary focus was on the architecture and the data flow. 
For UI, my focus was on the phone

# Architecture
The architecture utilizes Model-View-Intent pattern. Dagger is used for dependency injection 
to separate out the components into the application component and feature component. 
This enables scaling of the feature components, allows testing as well as reusability of 
lower level components.
The project takes advantage of LiveData for binding presentation layer with the view model 
and RxJava for fetching data from the network.

# Data Persistence
Data Persistence is utilized using Room database.

# Copied in Code or Copied in Dependencies
The project utilizes various 3rd party libraries such as RxJava, Stetho, Timber etc.

# Areas for Improvement
- Error State and Loading State is still not fully implemented in most screens.
- Error recovery could be better.
- Code could use more comments.
- UI can be more polished.
- Skipped testing due to time limit. For previous testing samples of my work, see https://github.com/sauravrp/githubusers.
- More unit tests, instrumented tests, db test, UI tests and integration tests could be implemented.

# Tools Used
Android Studio 4.1.1, used Pixel 2 API 27 emulator for all of testing