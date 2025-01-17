# CurrencyConversionApp

This is a currency conversion app that allows a user to view a given amount in a given currency converted into other currencies.

# Guide
This guide may help you understand my intentions and the rationale behind the implementation. Although this challenge is a demo, I applied Industry-level project strategies in its development, especially considering the perspective of PayPay in terms of architectural design and feature development. For example, module design, feature design, and attention to detail. Please see the details below.

# Architecture 
## Clean architecture 
![image](https://github.com/user-attachments/assets/2192ea12-224d-4c7d-929d-677998a94fcf)

## MutilModule 
There are some popular modularization strategies, which I know are Layer-Based/Featured-Based/Component-Based. In this demo, I chose the mix strategy since I feel this is more flexible. Here just highlight some crucial points as below.
- The core module is a shared module, which can be reused by other modules.
- The exchange module is a typical feature module.
- Each module (Core/Feature) follows clean architecture which includes at least 3 layers - presentation/domain/data
- Generally, each parent module can access core:domain module and core:layerModule, like exchange:data can access core:domain and core:data.
- core:testing are shared to others.
- Depending on the feature size, the data layer can be expanded to more modules eg, gateway, network, and location.
- Since some code in the core module can be reused, like testing, and database, so in the demo, I put them in 2 isolated modules.
- The dependency management of multiple modules is made possible by plugin development in the build-logic module, which has significantly reduced the amount of boilerplate Gradle code.

# About testing

## Unit testing - I covered most of the feature layers, but I still left Gateway because I believe it is enough to show my testing skills.
- In exchange:domain:test, I show how to use mock to test usecases. 
- In exchange:data:test, I show how to use fake to test repository
- In exchange:presentation:test, I show how to unit test state and viewModel.

## UI testing - TBD

# Techs
- Kotlin
- Jetpack Compose
- Coroutine
- Ktor
- Koin
- Testing

# TODO
- Pagination
Due to the unclear and incomplete requirements of the demo, if the list data is sorted from most-used to least-used, pagination could be added. However, if the list data is sorted alphabetically, loading and updating all 167 currencies at once would provide a better user experience. Personally, I lean towards loading all the data at once because, from PayPay's perspective, I believe this feature should be a standalone module. It is not something users would use heavily, and therefore, it is more suitable as a Dynamic Feature Module.

- Error handling

