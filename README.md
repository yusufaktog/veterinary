# Veterinary App
---

### Spring Boot - Thymeleaf Application

---
This Project provides to create pet, pet owner and address records and management of the records.

## APIs

The application has 5 apis:

* PetAPI
* PetOwnerAPI
* AddressAPI
* RegisterAPI
* LoginAPI

## Summary

This is a veterinary app where users(pet owners) can keep track of their pets, users can create pet records,
address records and edit them. Users those only have 'ADMIN' authority can perform some
specific operations such as deleting records, assigning and removing authorities from other users.

### PET

---

```html
POST /${version}/pet/{ownerId} = creates a new pet with given parameters
GET /${version}/pet = retrieves all pets
GET /${version}/pet/search?query = applies a filter which retrieves records those only contain the given keyword
PUT /${version}/pet/{petId} = updates the pet with given id
DELETE /${version}/pet/delete-pet/{petId} = deletes the record  associated with given pet id 
```

### Address

---

```html
POST /${version}/address = creates a new pet with given parameters
POST /${version}/address/add-address/{addressId} = Adds the existing address with given id to the 
                                                   currently logged-in user's address list
POST /${version}/address/remove-address/{addressId} = Removes the existing address with given id 
                                                      from the currently logged-in user's address list
GET /${version}/address = retrieves all addresses
GET /${version}/address/search?query = applies a filter which retrieves records those only contain the given keyword
PUT /${version}/address/{addressId} = updates the address with given id
DELETE /${version}/address/delete-address/{addressId} = deletes the record associated with given address id,
                                                        but only if there is no other user use this address otherwise throws an exception. 
```

### Pet Owner

---

```html

GET /${version}/owner = retrieves all owners
POST /${version}/owner/search?query = applies a filter which retrieves records those only contain the given keyword
POST /${version}/owner/assign-authority/{authorityId} = Adds the authority with given name to the currently logged-in user
POST /${version}/owner/remove-authority/{authorityId} = Removes the authority with given name from the currently logged-in user
PUT /${version}/owner/{ownerId} = updates the owner with given id
DELETE /${version}/owner/delete-owner/{ownerId} = drops the record associated with given owner id
```

### Register

---

```html
POST /${version}/register = Registers a new user record to the app.
```

### Login

---

```html

POST /${version}/login = Logs in to the app.
POST /${version}/logout = Logs out from the app.
```

### Test

---
This app provides unit tests under the directory test/com.aktog.yusuf.veterinary/service

### Tech Stack

---

* Java 11
* Spring Boot 2.7.2
* Spring Data JPA
* Maven 3.5.1
* Kotlin 1.7.0
* JUnit 5
* PostgreSQL 13.0
* Thymeleaf
* Springsecurity5

### Prerequisites

---

* Maven
* PostgreSQL

### Run & Build

---
#### Launch the app in your local

---
SPORT:8080

```
$ git clone https://github.com/yusufaktog/Veterinary.git
$ cd  veterinary
$ mvn clean install
$ mvn spring-boot:run
```

---
#### Or Visit the app deployed to public Heroku server

---


The app is also available on the heroku, can be visited from
(First run may take a while because Heroku puts app to sleep after 30 minutes of inactivity.)

[veterinary-thymeleaf](https://veterinary-thymeleaf.herokuapp.com/```application.properties.version```/login)
