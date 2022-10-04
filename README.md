## REST API WITH QUARKUS JAVA KEYCLOAK AUTHENTICATION-AUTHORIZATION SERVER WITH POSTRGRESQL

ALL files for steps bellow you will find here: https://github.com/eif-courses/production-api/tree/master/src/main/authentication-server

# STEP 1 

Install docker into your machine: https://www.docker.com/products/docker-desktop/

# STEP 2 

Install Keycloak server with postgresql added for authentication and authorization using Bearer tokens

run command: ``` docker-compose -f authentication-server.yml up ```

if everything is ok you will see in docker desktop or terminal window two containers running:

![image](https://user-images.githubusercontent.com/8007447/193873345-51e0b13b-8dc7-4be0-8864-fd7ffdecb730.png)

# STEP 3 

Go to browser and open ``` http://localhost:8180/auth ```

Enter username: *admin* and password: *admin*

Left side of page you will see Master so you need create new realm from prepared JSON file 

![image](https://user-images.githubusercontent.com/8007447/193874098-578d36db-ab01-4171-bf12-b52350f5c2f8.png)

Hover on image and click Add new realm button then click Select file pick one: ``` quarkus-realm.json ``` and import it. 
If everything is fine you will see successfully.... message.

# STEP 4 

Run you simple rest API 
```shell script
./mvnw compile quarkus:dev
``` 
with few endpoints and different roles

``` http://localhost:8080/api/secured ```

Then you should see error 401 non authorized so its fine. 

# STEP 5 

Check your endpoint with access token instead of Frontend creation first use tool like CURL or other similar for http requests 

Generate access token using http plugin in Intellij IDEA Ultimate:

``` POST http://localhost:8180/auth/realms/quarkus/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded
client_id=backend-service&grant_type=password&client_secret=secret&scope=openid&username=alice&password=alice
```
OR CURL

``` curl -L -X POST 'http://localhost:8180/auth/realms/quarkus/protocol/openid-connect/token' -H 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'client_id=backend-service' --data-urlencode 'grant_type=password' --data-urlencode 'client_secret=secret' --data-urlencode 'scope=openid' --data-urlencode 'username=alice' --data-urlencode 'password=alice' ```

Try to access secured endpoint (add newly created token from POST request): 

```
GET http://localhost:8080/api/secure
accept: */*
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjZklBRE5feHhDSm1Wa1d5Ti1QTlhFRXZNVVdzMnI2OEN4dG1oRUROelhVIn0.eyJleHAiOjE2NjQ4OTk2MzYsImlhdCI6MTY2NDg5OTMzNiwianRpIjoiNDY3ZTg5MTEtYjdkOS00YWZjLWExZDQtNzRiMmE4YzI3ZmMyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgwL2F1dGgvcmVhbG1zL3F1YXJrdXMiLCJzdWIiOiJlYjQxMjNhMy1iNzIyLTQ3OTgtOWFmNS04OTU3ZjgyMzY1N2EiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJiYWNrZW5kLXNlcnZpY2UiLCJzZXNzaW9uX3N0YXRlIjoiZDk2OTQ3NTktYTI0OC00NGU4LTgyODQtMDIzZThkM2MzZWU4IiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ1c2VyIl19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiZDk2OTQ3NTktYTI0OC00NGU4LTgyODQtMDIzZThkM2MzZWU4IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhbGljZSJ9.Fb9Zyuel6CZjvpgAFq1m_dWYlkS5JyehQFbk2JClCvYxRxVHi5XQWhv1xbctwHrSBE47ZRrblPvAHUQnj5aTZEpAiLhAUoIYkvJ3DuMlZyRv92ddi3vkopwOycbTeEREl1vP9VC6c_DxHQzOMT_3CeDckNOs48ZSg77H57fydL2OSCc1eB4hYs-56kVVqMIw89tKPV89twb50LZzsxBqahwB30LBXRQkRiBej2WXt1gb6DEp7jcSmAlK1_YABDz2P_I0rSIlanGSztR-4nF1GlCkmXu8zQ1Rg4E7_GyQJFPXbfGfwY8EpLoR2OtPdwqRXzDJip6gCO2U2JNxyO6DaA
```

OR CURL 

``` curl -X 'GET' 'http://localhost:8888/students' -H 'accept: */*' -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJPakNSTE9RYkZBS0hzLWV3R1djZl9QdHNOZThtdzNZY05iZzc0eU1SV3VvIn0.eyJleHAiOjE2NjQ3MTQ2MDIsImlhdCI6MTY2NDcxMzcwMiwiYXV0aF90aW1lIjoxNjY0NzEzNzAyLCJqdGkiOiI1Yzc1ZGVmYi0zMDM1LTQzYzItYWZmZS1mZjFiNjYyNzMwMjAiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjUwODUzL3JlYWxtcy9xdWFya3VzIiwic3ViIjoiMTRmYmM2ZjItNjQ2MS00OWNlLWIxNjYtMGZiNGU5MWQyNGQ0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3R1ZGVudHMtYXBpIiwibm9uY2UiOiJkM2QzMWUxMS01YWZhLTQ0OTQtYjI1Mi00YWUwMThmNjAxYWIiLCJzZXNzaW9uX3N0YXRlIjoiZGU0ZjFiZGEtNmYyNy00NDI3LTg5MjQtZjU4NGMyYTNhN2MxIiwic2NvcGUiOiJtaWNyb3Byb2ZpbGUtand0Iiwic2lkIjoiZGU0ZjFiZGEtNmYyNy00NDI3LTg5MjQtZjU4NGMyYTNhN2MxIiwidXBuIjoiYWxpY2UiLCJncm91cHMiOlsiYWRtaW4iLCJ1c2VyIl19.MKNEvR_dhr-u-dkDy3qpNNoufqj6PCQJ4Ua598XJzCV00JPFsBv2jrMYlUQ7QJG7rhSne1oBN2s3sGTQq6X6-vCw_nISZNeeZKs52a-rfNfMdbOXxZY6qq5IThW0KaEw0EaSeteXgk0-XGn3QU9HpJsdp2juMS7FN_GWBjEP0PVsT5DJ66uncKCSzfPaVB-rhvcS9hN86nAma6YQV12XtjVYtw4ARBQ45x9m6T9-WXdeik42bkzmg-nSURshtY-1nLtslwV2SiPmhzglyyvTcRTglLoFQwD8z3Qd8KQtOcQA-mBpkK3uRQ1Wc97nZTuBJ3wIMrpy9Dm07ipt7sLXUw' ```
`` 

If you everything is fine you will see ``` Hello Secured ```. 

# production-api Project DEFAULT INFO FOR QUARKUS MAVEN

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/production-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Keycloak Authorization ([guide](https://quarkus.io/guides/security-keycloak-authorization)): Policy enforcer using Keycloak-managed permissions to control access to protected resources
- OpenID Connect ([guide](https://quarkus.io/guides/security-openid-connect)): Verify Bearer access tokens and authenticate users with Authorization Code Flow
