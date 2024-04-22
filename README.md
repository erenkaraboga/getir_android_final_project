


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

Product List  

* On the product list module, there are two RecyclerViews, one horizontal and one vertical. This module uses a shared ViewModel to send requests and displays product items in the respective lists through the ProductAdapter. It also enables navigation to the product detail module when any item is clicked.
Additionally, shimmer effect is utilized for loading products.

Product Detail

* In the product detail module, the details of the selected product from the product list page are displayed. Additionally, this module includes functionalities for adding the respective product to the cart or removing it from the cart."

Basket

 * In the basket module, the user can view the products they have selected from both the product detail and product list modules. If desired, the user can make changes to these products.
 * At the bottom of the screen, there is a list of recommended products as a bonus feature, and the user can also add or remove products from here in the same way. If the user's basket is not empty, they can choose to empty it.
 * When the user confirms their basket, a Toast message appears indicating the total amount, and the user is returned to the main module.

## Testing

### GetProductsUseCase Test

The `GetProductsUseCase` is responsible for fetching products. Three different scenarios are tested:

1. **Success Case**: Verifies the expected behavior when `getProducts()` function is called successfully. It ensures that the function returns the expected list of products.

2. **HTTP Exception Error Case**: Tests the expected behavior when `getProducts()` function encounters an HTTP exception. It verifies that the function throws an HTTP exception as expected.

3. **IO Exception Error Case**: Validates the expected behavior when `getProducts()` function encounters an I/O exception. It ensures that the function throws an I/O exception as expected.

#### Test Cases

- **check getProducts() success case**: Tests the successful execution of the `getProducts()` function.
- **check getProducts() http exception error case**: Tests the behavior when `getProducts()` encounters an HTTP exception.
- **check getProducts() io exception error case**: Tests the behavior when `getProducts()` encounters an I/O exception.

### SharedViewModel Test

The `SharedViewModel` is a ViewModel responsible for managing UI-related data in the home screen of an Android application. The test cases cover various scenarios to ensure the correct behavior of the ViewModel's functions.

#### Test Cases

1. **getProductsUseCase emits success**: Verifies the correct handling of a successful response from the `getProducts()` function of the `GetProductsUseCase`. It simulates the emission of a successful resource and checks if the ViewModel handles it properly.

2. **getProductsUseCase emits error**: Tests the ViewModel's behavior when the `getProducts()` function of the `GetProductsUseCase` returns an error. It simulates the emission of an error resource and verifies if the ViewModel handles it correctly.

3. **getProducts emits loading**: Checks the ViewModel's response to the loading state when the `getProducts()` function is called. It simulates the emission of a loading resource and ensures that the ViewModel handles it appropriately.

4. **verify setLoading function called with isLoading=true**: Validates the ViewModel's behavior when the `setLoading(true)` function is called. It verifies if the ViewModel correctly sets the loading state to true.

5. **verify setLoading function called with isLoading=false**: Validates the ViewModel's behavior when the `setLoading(false)` function is called. It verifies if the ViewModel correctly sets the loading state to false.

## Branches Explanation 


## Developer Notes
 * It has been instructed that in the application, if there are no products in the cart, the user should not be able to navigate to the cart. If the user does not have any items in the cart, they cannot navigate to the cart from the product list or detail page. However, if the cart is populated, and the user navigates to it, the cart is emptied one by one, allowing the user to optionally select from suggested products without the screen being closed.
 * In such an application, since both the elements of the product list and suggested product list can change continuously, and there are RecyclerViews in both the product list and basket screens, these elements need to be updated without fetching data from the internet again. Therefore, these lists are maintained locally in the viewmodel class. Due to the limitations of the task provided by the Getir, no local database setup has been implemented. Adding a local database could also be a reasonable option.
 * Mockito has been used for the application's testing, and manual mock data has been utilized. You can find the MockHelper class under the utils section within the Core module.

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




