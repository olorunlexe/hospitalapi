# hospital-api
This is the code base for Spring Backend Assessment


## Dependencies
- JDK v17 - Download from [Adoptium](https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot)
- MySQL
- SpringBoot v2.6.3
- Maven v3.6+

## Local Database Setup
Download MySQL and setup on machine
Then execute the following create the database,schema and user

```MySQL
CREATE USER "hospital" WITH PASSWORD 'hospital';
CREATE DATABASE "hospital";
GRANT ALL PRIVILEGES ON DATABASE "hospital" TO "hospital";
```

## Response Format

### Successful response format
```json
{
  "status": true,
  "code" : 200,
  "message" : "Operation successful",
  "data": {
    "id": 1,
    "firstName": "Susan",
    "lastName": "Doe"
  }
}
```

### Error response format
```json
{
  "status": false,
  "code" : 400,
  "message" : "Missing required parameter",
  "errors": [
    "Email field is required",
    "First name field is required"
  ]
}
```