# API Testing KATA - Rest Assured Cucumber Framework

## About

This project is an API automation framework developed for the **API Testing KATA** using **Rest Assured**, **Cucumber**, **TestNG**, and **Jackson**.
The framework automates API validations for the Booking application hosted at: https://automationintesting.online

The framework currently covers:
✅ Authentication API
✅ Booking API positive scenarios
✅ Booking API negative validation scenarios
✅ Dynamic booking id handling
✅ Token generation and reuse
✅ JSON payload-based request creation
✅ POJO-based request serialization and response deserialization
✅ Cucumber BDD reporting

## 🧭 Flow Explanation

🥒 Feature files describe test scenarios in readable Gherkin format.
🧩 Step definitions contain Java implementation for each Gherkin step.
📦 POJO classes represent API request and response models.
📄 JSON files are used as request payload sources.
🔄 JacksonUtils converts JSON files into Java POJO objects.
🌐 RestResource provides reusable methods for API calls.
⚙️ SpecBuilder maintains common Rest Assured specifications.
🔐 TokenManager handles token generation and reuse.
🧠 TestContext stores runtime scenario data such as response, token, booking id, and payloads.
🪝 Hooks perform scenario-level logging and cleanup.


## 🧰 Technologies / Tools Used

| Icon | Technology / Tool | Purpose |
|---|---|---|
| ☕ | Java | Programming language |
| 📦 | Maven | Build and dependency management |
| 🔍 | Rest Assured | API automation and validation |
| 🥒 | Cucumber | BDD-style test design |
| 🧪 | TestNG | Test execution framework |
| 📄 | Jackson | JSON serialization and deserialization |
| 📊 | Allure Report | Advanced test reporting |
| 📘 | Cucumber HTML Report | Default BDD execution report |
| 🧑‍💻 | IntelliJ IDEA | Development IDE |
| 🌿 | Git | Version control |
| 🐙 | GitHub | Repository hosting |

## 📁 Folder Structure Overview

API_Testing_Kata
│
├── pom.xml
├── README.md
│
├── src
│   └── test
│       ├── java
│       │   ├── apis
│       │   │   ├── RestResource.java
│       │   │   ├── SpecBuilder.java
│       │   │   └── TokenManager.java
│       │   │
│       │   ├── hooks
│       │   │   └── Hooks.java
│       │   │
│       │   ├── pojo
│       │   │   ├── AuthRequest.java
│       │   │   ├── BookingDates.java
│       │   │   ├── BookingRequest.java
│       │   │   ├── BookingResponse.java
│       │   │   └── PartialUpdateBookingRequest.java
│       │   │
│       │   ├── runners
│       │   │   └── TestRunner.java
│       │   │
│       │   ├── stepdefinitions
│       │   │   ├── AuthSteps.java
│       │   │   ├── BookingSteps.java
│       │   │   └── HealthSteps.java
│       │   │
│       │   └── utils
│       │       ├── ConfigLoader.java
│       │       ├── JacksonUtils.java
│       │       └── TestContext.java
│       │
│       └── resources
│           ├── config
│           │   └── config.properties
│           │
│           ├── features
│           │   ├── auth.feature
│           │   ├── booking.feature
│           │   └── health.feature
│           │
│           └── testdata
│               ├── auth-login.json
│               ├── create-booking.json
│               ├── update-booking.json
│               └── partial-update-booking.json

## ✅ Framework Implements Below Best Practices
### 🧱 Framework Design

✅ Modular and scalable folder structure
✅ Separation of concerns across apis, pojo, utils, hooks, runners, and stepdefinitions
✅ Reusable API resource layer through RestResource
✅ Centralized request and response specification using SpecBuilder
✅ Configuration-driven framework using config.properties

### 🥒 Cucumber / BDD Practices

✅ Feature files written in Gherkin format
✅ Scenarios grouped by API capability
✅ Tags used for selective execution
✅ TestNG-based Cucumber runner
✅ Scenario hooks for logging and context cleanup

### 🔐 Authentication Handling

✅ Auth token generated through login API
✅ Token reused for authenticated APIs
✅ Token renewal handled through TokenManager
✅ Auth credentials maintained through configuration and JSON payload

### 📦 Payload and Data Management

✅ JSON-based request payloads
✅ Jackson-based JSON deserialization
✅ POJO-based request serialization
✅ Strongly typed request and response models
✅ Dynamic booking dates to avoid duplicate booking conflicts

### 🔁 Runtime Data Handling

✅ Dynamic booking id extraction from create booking response
✅ Booking id reused across GET, PUT, PATCH, and DELETE scenarios
✅ TestContext used for sharing scenario-level data
✅ ThreadLocal-based context design for safer execution

### 🧪 Validation Strategy

✅ Status code validation
✅ Response body validation
✅ Booking details validation
✅ Token availability validation
✅ Positive and negative API validations
✅ API behavior-aligned assertions based on actual server response

### 📊 Reporting

✅ Cucumber HTML report
✅ Cucumber JSON report
✅ Allure results support
✅ Console logs for request and response debugging

## ▶️ Running Tests

### ✅ Run All Tests
mvn clean test

### 🔐 Run Auth Tests
mvn clean test -Dcucumber.filter.tags="@auth"

### 🏨 Run Booking Tests
mvn clean test -Dcucumber.filter.tags="@booking"

### 🟢 Run Create Booking Tests
mvn clean test -Dcucumber.filter.tags="@create"

### 🔴 Run Negative Validation Tests
mvn clean test -Dcucumber.filter.tags="@negative"

### 🧪 Run from IntelliJ IDEA
1. Open the project in IntelliJ IDEA.
2. Navigate to: src/test/java/runners/TestRunner.java
3. Right-click TestRunner.java
4. Select: Run 'TestRunner'

## 📊 Test Reports

### 📘 Cucumber HTML Report
After test execution, the Cucumber HTML report is generated at:

## 📄 Cucumber JSON Report
target/cucumber-reports/cucumber-report.json

## 📈 Test Execution Summary

| Metric | Count |
|---|---:|
| ✅ Total test cases | 50 |
| 🟢 Passed test cases | 47 |
| 🔴 Failed test cases | 3 |
| 📊 Pass percentage | 94% |

> ⚠️ **Note:** API responses from the server are sometimes inconsistent. When the API response is stable, the above results are observed.

## ⚠️ Known Observations

| Area | Count | Remarks |
|---|---:|---|
| 🔁 PATCH API | 1 | API appears unstable or not fully implemented from server side |
| 🐞 Possible API defect | 2 | Requires further API-side validation |

## 🏷️ Useful Tags

| Tag | Purpose |
|---|---|
| `@auth` | Run authentication scenarios |
| `@booking` | Run booking API scenarios |
| `@create` | Run create booking scenarios |
| `@get` | Run get booking scenarios |
| `@update` | Run update booking scenarios |
| `@patch` | Run partial update scenarios |
| `@delete` | Run delete booking scenarios |
| `@negative` | Run negative validation scenarios |
| `@validation` | Run field validation scenarios |

---

## 🧾 API Coverage

| API Area | Method | Endpoint | Status |
|---|---|---|---|
| 🔐 Auth | POST | `/auth/login` | ✅ Automated |
| 🏨 Booking | POST | `/booking` | ✅ Automated |
| 🏨 Booking | GET | `/booking/{id}` | ✅ Automated |
| 🏨 Booking | PUT | `/booking/{id}` | ✅ Automated |
| 🏨 Booking | PATCH | `/booking/{id}` | ✅ Automated |
| 🏨 Booking | DELETE | `/booking/{id}` | ✅ Automated |
| 💚 Health | GET | `/booking/actuator/health` | ✅ Automated |

---

## 📌 Notes

- The framework uses JSON payload files from `src/test/resources/testdata`.
- Booking ids are not hardcoded for positive booking flows.
- Booking id is dynamically extracted from the create booking response.
- Unique booking dates are generated during execution to avoid duplicate booking conflicts.
- Some API responses are inconsistent from the server side, so validations are aligned with observed API behavior.

---

## 👤 Author

**Ravichandran Thiyagarajan**








