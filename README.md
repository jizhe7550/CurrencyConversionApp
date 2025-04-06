# CurrencyConversionApp
This is a currency conversion app that allows a user to view a given amount in a given currency converted into other currencies.
- Offline first app, support HTTP caching and Database caching
- Compose + Clean Architecture + Multimodule
- To Run this app, you need to get an API key from https://www.currencyconverterapi.com/ and put it in the local.properties file like below.
```API_KEY = your key```

# Guide
This guide may help you understand my intentions and the rationale behind the implementation. Although this challenge is a demo, I applied Industry-level project strategies in its development, especially considering the perspective of xxxx in terms of architectural design and feature development. For example, module design, feature design. Please see the details below.

# Architecture 
## Clean architecture 
![image](https://github.com/user-attachments/assets/2192ea12-224d-4c7d-929d-677998a94fcf)

## MultiModule 
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
According to the testing pyramid strategy, we should spend more time covering smaller tests, such as unit tests, to detect issues early, fix them promptly, and reduce costs. In the demo, I primarily focused on testing the domain, ViewModel, and repository, as these components typically don’t change frequently. On the other hand, the UI often changes according to user preference trends and design principle. Therefore, for UI testing, we should integrate it with the pipeline to ensure comprehensive testing.

## Unit Testing
- In exchange:domain:test
- In exchange:data:test
- In exchange:presentation:test
- TBD - Gateway, DB, API.

## Instrumentation Testing
# Intergration Testing
- RoomLocalGatewayImplTest

## UI testing
- In exchange:presentation:androidTest CurrencyConversionScreenTest
- TBD Integration UI testing with VM
- TBD End-to-End Testing

# Techs
- Kotlin
- Jetpack Compose
- Coroutine
- Ktor
- Hilt
- Junit5
- Mockk

# Improvement
- Pagination
Due to the unclear and incomplete requirements of the demo, if the list data is sorted from most-used to least-used, pagination could be added. However, if the list data is sorted alphabetically, loading and updating all 167 currencies at once would provide a better user experience. Personally, I lean towards loading all the data at once because, from PayPay's perspective, I believe this feature should be a standalone module. It is not something users would use heavily, and therefore, it is more suitable as a Dynamic Feature Module.
- Error handling
In the real world, we should handle Errors properly to give the best UX to users. However, the demo is using try catch strategy.
- UI detail
Typical input bugs, in the real world, we'd better customise a keyboard to limit input invalid cases. In the demo, I only handle common cases.
- Dynamic Feature module 

