## demo1 - contact app

### intro
basic dropwizard service, using mongodb to store contact information, deployed via heroku

### install

to build fat jar run:

```
mvn clean install
```
## deployment

[heroku endpoint](http://zylro-contact-app.herokuapp.com)

example cURL
```
curl -X GET \
  http://zylro-contact-app.herokuapp.com/contact \
  -H 'Authorization: api_key bloop' \
  -H 'Content-Type: application/json' 
}'
```

### organization

this project is broken down by packages
- contact - app startup / configurations / utility functions
- auth - for a simple kind of authorization
- data - data access for mongodb interactions
- model - pojo for data stored
- resources - defining resources available
- service - for validations to be expanded upon in the distant future
