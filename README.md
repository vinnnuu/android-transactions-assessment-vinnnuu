# Android Technical Home Assignment

## Description
At Backbase we try to offer our customers and end-users the best possible Digital Banking experience that they can get.

In one of the scenarios, we would like to find specific patterns in grouping user's transactions. A transaction group consists in one or multiple transactions that have the same `creditDebitIndicator` and are created in the same day.
For example, if two transactions are completed within the same day and they are both DBIT (outgoing), then they can be put in a group.
We also want to section transaction groups based on their transactions `state`, so all groups that have PENDING transactions should be in one section and all groups that have COMPLETED transactions should be in another section.

The focus of this assignment is to evaluate your coding skills and thinking process. We don't want you to spend more than 4 hours on this assignment.
Even if you're not able to finish within the given time interval, please try to provide your best solution within the recommended time and focus on the quality (rather than quantity) of whatever you've completed.

## Project setup
In the project we have provided you, we have included two helper frameworks that you can use to speed up your development. After all, we want you to focus on what's important!

1. `BackbaseNetworking` is a framework that you can use to perform the API calls based on the technical requirements. You can find components under `com.backbase.techassignment.networking` package
   You don't have to build the project structure in such a way that you know the NetworkBandwidth in real time. You can use explicit indication of the connection speed `TransactionsUseCase(NetworkBandwidth.Medium)`
2. `BackbaseMDS` is a framework that contains different UI tokens to help you speed up your UI development while maintaining consistency. You can find components under `com.backbase.technicalassignment.design` package
UI tokens in Android pertain to essential design elements such as colors, dimensions, and typography, fostering visual consistency across app interfaces. Leveraging resource files, UI tokens are seamlessly 
integrated into layouts, styles, and code, ensuring efficient and consistent design implementation. Advantages include cohesive aesthetics, 
streamlined development, adaptable modifications, and scalability. Incorporating UI tokens enhances UI design efficiency and maintains brand identity throughout app evolution.

After you finalise the task, please make sure all your code is properly pushed to the `main` branch of your project in Github Classroom.
We would also like to see the commit history.

Refrain from using 3rd party libraries.

## Requirements
> List all transactions groups

Based on the provided Transaction objects, compute all transaction groups and display them as a list in the following two sections:

* PENDING - show transaction groups where each group consists of one or more transactions that have the same `creditDebitIndicator` and are created in the same day `creationTime`. All the transactions have PENDING `state`.
* COMPLETED - show transaction groups where each group consists of one or more transactions that have the same `creditDebitIndicator` and are created in the same day `creationTime`. All the transactions have COMPLETED `state`.

The order of the sections should be as above. Pending is first and then Completed.
For both sections, most recent transactions should show first.
For more information, see the examples at the end of this document.

## UI Requirements
1. Follow the designs provided in the **UI Guidelines** folder. You can use the Figma file (if you already have an account) or the PDF file. The necessary icons are already included in the project Assets folder.
2. If a section is empty, it shouldn't be shown.
3. For each transaction group, the following info should be showed: `date`, `all available transactions description`, `total transactions number`, `combined calculated amount` and `credit debit indicator`.
4. Implement UI state management with loading, empty and error states.
5. Provide support for the UI in light and dark mode.

## Transaction Data

```
    {
        "id": "ec9ec6fd-a99c-4cee-a73e-51f84b11c373c342",
        "description": "Internet Banking",
        "typeGroup": "Payment",
        "type": "SEPA Payment",
        "category": "Income",
        "transactionAmountCurrency": {
            "amount": "1919.95",
            "currencyCode": "EUR"
        },
        "creditDebitIndicator": "CRDT",
        "counterPartyName": "Backbase B.V",
        "counterPartyAccountNumber": "NL40ABNA0541164350",
        "counterPartyCity": "Amsterdam",
        "counterPartyAddress": "Jacob Bontiusplaats 9, 1018 LL",
        "creationTime": "2019-05-15T14:28:01",
        "state": "PENDING"
    }
```
Optional Properties:
1. counterPartyName
2. counterPartyAccountNumber
3. counterPartyCity
4. counterPartyAddress

## Testing
1. Test using the provided user ID's:
- **10000** user without any transactions
- **10010** user with only single pending transactions
- **10011** user with only grouped pending transactions
- **10012** user with only single completed transactions
- **10013** user with only grouped completed transactions
- **10014** user with single pending and completed transactions
- **10015** user with grouped pending and completed transactions

If a userID that is not in the above list is used, the API will return an error.

### Automation

Through `BackbaseNetworking` framework you can test a generic part of your output results. If you want to do so, you can follow the below steps:
1. Conform to the `TransactionsOutput` interface where you have your business logic.
2. Use `TransactionSolutionTester.evaluateResult()` to evaluate your solution.

## Guidelines
- Please use Kotlin as your development language.
- Design your code to be testable even though we don't specifically require Unit Tests. Adding unit tests on the business logic is a big bonus on the requirements.
- Make sure the code can be easily compiled and it doesn't require third party setups.
- Use the latest stable Android Studio versions to develop.
- Support minimum API level 23.

## Assessment:
Once submitted, your solution will be checked on the requirements mentioned above, as well as:
* Technical Skills
* Documentation
* Coding/Problem solving skills
* Code Efficiency, Maintainability, Scalability
* Architecture and Design Patterns
* Testability
* Platform Knowledge

## Examples
Please take a look at different examples on how the app should behave

### Example 1; single pending transactions
```
Input:

Transaction(id: "1", date: "2019-05-17", creditDebitIndicator: "CRDT", state: "PENDING")
Transaction(id: "2", date: "2019-05-16", creditDebitIndicator: "DBIT", state: "PENDING")
Transaction(id: "3", date: "2019-05-15", creditDebitIndicator: "CRDT", state: "PENDING")

Output:

1 Section:
  PENDING - 3 Groups
    1. ID 1 (`2019-05-17` and `CRDT`)
    2. ID 2 (`2019-05-16` and `DBIT`)
    3. ID 3 (`2019-05-15` and `CRDT`)
``` 

### Example 2; grouped pending transactions
```
Input:

Transaction(id: "1", date: "2019-05-15", creditDebitIndicator: "CRDT", state: "PENDING")
Transaction(id: "2", date: "2019-05-15", creditDebitIndicator: "CRDT", state: "PENDING")
Transaction(id: "3", date: "2019-05-15", creditDebitIndicator: "DBIT", state: "PENDING")

Output:

1 Section:
  PENDING - 2 Groups
    1. ID 1 & ID 2 (`2019-05-15` and `CRDT`)
    2. ID 3 (`2019-05-15` and `DBIT`)
``` 

### Example 3; single completed transactions
```
Input:

Transaction(id: "1", date: "2019-05-17", creditDebitIndicator: "CRDT", state: "COMPLETED")
Transaction(id: "2", date: "2019-05-16", creditDebitIndicator: "CRDT", state: "COMPLETED")
Transaction(id: "3", date: "2019-05-15", creditDebitIndicator: "DBIT", state: "COMPLETED")

Output:

1 Section:
  COMPLETED - 3 Groups
    1. ID 1 (`2019-05-17` and `CRDT`)
    2. ID 2 (`2019-05-16` and `CRDT`)
    3. ID 3 (`2019-05-15` and `DBIT`)
``` 

### Example 4; grouped pending and completed transactions
```
Input:

Transaction(id: "1", date: "2019-05-17", creditDebitIndicator: "CRDT", state: "PENDING")
Transaction(id: "2", date: "2019-05-17", creditDebitIndicator: "CRDT", state: "PENDING")
Transaction(id: "3", date: "2019-05-18", creditDebitIndicator: "DBIT", state: "COMPLETED")
Transaction(id: "4", date: "2019-05-15", creditDebitIndicator: "CRDT", state: "COMPLETED")
Transaction(id: "5", date: "2019-05-15", creditDebitIndicator: "DBIT", state: "COMPLETED")
Transaction(id: "6", date: "2019-05-15", creditDebitIndicator: "DBIT", state: "COMPLETED")
Transaction(id: "7", date: "2019-05-17", creditDebitIndicator: "CRDT", state: "COMPLETED")

Output:

2 Sections:
  PENDING - 1 Group
    1. ID 1 & ID 2 (`2019-05-17` and `CRDT`)
  COMPLETED - 3 Groups
    1. ID 7 (`2019-05-17` and `CRDT`)
    1. ID 3 (`2019-05-18` and `DBIT`)
    2. ID 4 (`2019-05-15` and `CRDT`)
    3. ID 5 & ID 6 (`2019-05-15` and `DBIT`)
``` 
