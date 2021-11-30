# Codium
BankCardApp

##Requirements
* Java > 1.9+
* Mysql server running on port 3306
* Redis server with empty databases running on port 6379



##Semantics
* Owner of a card is called a CardOwner
* Card is BankCard- either debit or credit bank card
* Service responsible for handling rest operations such as assigning cards to the CardOwner is called Bank(Service, Mapper, DTO...)

**CardOwner** owns multiple BankCards. They can posses as many cards as they want.

**BankCard** is represented either by CreditCard or DebitCard, the only difference is that CreditCard has limit attribute.

CardOwner is in **one to many** association with BankCards.
BankCards are in **many to one** association with CardOwner.

Validation of BankCard number is done using Luhman's algorithm. All fields in BankCard an subclasses are being validated.

##Internal Logic
Dependencies are managed by **maven.**

Data management & profiles Data are manged based on the profile used.

**App uses 3 profiles:**
1. production  
   MYSQL database production version, hibernate.ddl.auto=update, verifications of BankCard numbers and production logging, h2 console disabled
2. localsql  
MYSQL database, disabled BankCard number verification, debug logging, database is create-drop
3. local
   REDIS database(needs to be empty), same as localsql

###Rest
**Endpoints:**
1. list                       
   List all users and their BankCards
2. find&surname=?    
Find one user and list their BankCards
3. save   
Save one user and their list of BankCards

Data are transported via DTO object. Conversions between objects and dto's is done by mapstruct.

All responses(200/400/500) are custom.

###Data init
If profile is set to local or localsql and spring.dummy.generate=true
one dummy database entry is generated on application startup.

###Tests
A few tests(spring mocks, and junit) are implemented.

In the repository you can also find exported post man collections for testing purposes

###Logging
Production logs are saved to the %usershome%/logs folder. 

##Ideas for further improvements
1. Add all of the crud methods implementation
2. Field validity should by string, because of standardization problems 
3. BankCard number validation on class level. Card brands require different starting sequnce which is not controlled.
4. More tests
5. Frontend
