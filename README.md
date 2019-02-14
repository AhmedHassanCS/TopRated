# Top Rated Movies Android Application  
Android application to view top rated movies from The Movie DB API storage
Project is written in kotlin and structured in Clean Architecture with MVVM design pattern in the presentation layer  
  
**Application has two screen**  
  1. Top rated movies screen  
  2. Movie's details screen (Opens from the first screen)  

## Architecture Components:    
        1- Presentation Layer:  
           - UI components (Activities, Fragments and Adapters).  
           - ViewModels That are invoked by the fragments.  
           - Dagger Components and modules. 
		     
        2- Domain Layer:  
            - UseCases and everyone of them controls only one action  
            - Models that are the holders of the refined data ready to be used by presentation  
			
        3- Data Layer:  
            - Internet requests and calls  
            - Entities which are the receivers of the api json data  
            - Converters That Convert type of entities to type of models to be refined and ready to be used  
            - Repositories That controls the flow of data from online (and offline too in other cases) data  
            
## Action and Data Flow:  
    1 -> Activity Loads Fragment  
    2 -> Fragment invokes ViewModel and Observe the LiveData contained in it  
    3 -> ViewModel Creates Observer and pass it to UseCase Execution  
    4 -> UseCase Call Repository that returns observable of data, UseCase subscribes it with the observer sent by the ViewModel  
    5 -> Repository Calls the Api handling implementation to get the observable  
    6 -> Api Class Send request to TmDB API in observable implementation, and return the observable  
    7 <- Observable invoke the observer when data is received or error occures  
    8 <- Observer Changes the value of the LiveData in the ViewModel which will invoke fragment's observer  
    9 <- The Fragment eventually update the ui with the new coming data  

## Tools  
* Dagger2
* Retrofit2
* RxJava2
* Lifecycle Components  
* ButterKnife
* Picasso
* Gson
            
            
        
