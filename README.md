


<div align="center">
    <img alt="Icon" src="https://github.com/erenkaraboga/getir_android_final_project/assets/74095539/be288011-8112-4b78-aacb-64c106311313" width="200" />
</div>

<h2 align="center">
    Getir Lite
</h2>
<p align="center">
    Getir Lite is a simplified version of the Getir app.
</p>

<div align="center">
    <img alt="Icon" src="https://github.com/erenkaraboga/StockListingApp/assets/74095539/70bdb8a6-d5cd-440d-a74e-deff2730e786" />
</div>

## Table of Contents

1. [Project Characteristics](#project-characteristics)
2. [Project Decisions](#project-decisions)
3. [App Architecture](#app-architecture)
4. [List of Positives](#list-of-positives)
5. [Demo Video](#demo-video)

## Project Characteristics 
* [Kotlin](https://kotlinlang.org/)
* [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
* [Multi Module Setup](https://developer.android.com/topic/modularization)
* [MVVM](https://developer.android.com/topic/architecture)
* [Single Activity Architecture](https://www.youtube.com/watch?v=2k8x8V77CrU)
* [Navigation Component](https://developer.android.com/guide/navigation/migrate)
* [Dependency Injection With Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Version Catalogs](https://developer.android.com/build/migrate-to-catalogs)
* [Unit Testing](https://developer.android.com/training/testing/local-tests)

## Project Decisions
  <ul>
    <li><strong>Kotlin Usage:</strong> The project utilizes Kotlin as its primary language, leveraging its expressive power and productivity benefits.</li>
    <li><strong>Clean Architecture Approach:</strong> Following clean architecture principles, the project ensures modularity, independence, and testability of code components.</li>
    <li><strong>Multi-Module Setup:</strong> Employing an advanced modular structure, the project logically separates its components for easier maintenance and scalability.</li>
    <li><strong>MVVM Design Pattern:</strong> Implementing the Model-View-ViewModel (MVVM) design pattern, the project achieves a clear separation between user interface and business logic, enhancing code readability and maintainability.</li>
    <li><strong>Single Activity Architecture:</strong> Embracing a single activity architecture, the project enhances performance and reduces memory consumption, thus improving user experience.</li>
    <li><strong>Navigation Component Usage:</strong> Utilizing the Navigation Component, the project manages application navigation, simplifying navigation structure and maintenance while promoting modular navigation setup.</li>
    <li><strong>Dependency Injection With Hilt:</strong> Leveraging Hilt for dependency injection, the project reduces dependencies between components and enhances testability.</li>
    <li><strong>Version Catalogs:</strong> Version catalogs centralize the management of project dependencies and versions, facilitating dependency updates and version compatibility across modules, thus enhancing modularity.</li>
    <li><strong>Unit Testing:</strong> Encouraging the use of unit tests, the project enhances software accuracy and reliability, ultimately improving code quality.</li>
  </ul>

## App Architecture
<div align="center">
    <img alt="Icon" src="https://github.com/erenkaraboga/getir_android_final_project/assets/74095539/d6dcbdbb-7f24-4578-9375-cbe19227cc03" />
</div>

## Module Explaination

App

* The app module contains the Hilt app module, which includes the necessary Hilt API, and it also contains the main activity class. The main activity class listens to changes in the toolbar type, the total amount in the shopping cart, and whether the order has been placed. The toolbar is defined once within this class. The main navigation graph is located here, and the navigation graphs of feature modules are included in this activity.

Core

* The core module of the application is designed to be usable by other modules. Constants and enums for the entire application are within the core module. Additionally, the core module includes custom UI components used by other modules of the application. Moreover, the class necessary for dialogs is located here, and the Resource class for fetching data from the internet is defined within this module as well.

* This module also handles the application's internet-related connections. Folder structure follows Clean Architecture principles. Under the Data layer, within the remote folder, there are api service and dto folders. The Data layer also includes the implementation of the Product Repository class. The DI (Dependency Injection) folder is where Hilt configurations are made. Dependencies for Mapper, Retrofit, UseCase, and network modules are bound here. Http Logging Interceptor usage is also found under the Network Module.

* In the Domain layer, there are folders that connect the application's core and feature modules. Extensions specific to the application, such as the Mapper Dto to Model mapper class, are defined here. Model classes used by feature modules are also located here, along with the interface part of the Repository. Since feature modules are closer to the application, Use Case implementation is done here.

* As the application follows the single activity multi-fragment approach, and there's significant connection and synchronization between screens, a single ViewModel is used, which is also located here.


## List of Positives

- ✅ Completed

| Feature                                                   | APP |
|-----------------------------------------------------------|:---:|
| MVVM                                                      |  ✅  |
| Custom Views                                              |  ✅  |
| Recommend Products                                        |  ✅  |  
| Animations                                                |  ✅  | 
| Unit Tests                                                |  ✅  | 
| Modular Approach                                          |  ✅  | 
| Hilt for DI                                               |  ✅  | 

## Demo Video

[Demo Video](https://youtu.be/YW1R92lEpDE)




