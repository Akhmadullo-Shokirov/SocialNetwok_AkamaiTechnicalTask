# Social Network

## Initial Setup
This project uses H2 integrated in-memory database to persist entities. The following variables is application.properties file
should be saved as environment variables.

![image](https://user-images.githubusercontent.com/66511606/216837534-17ef1b95-25ec-44c9-943a-6985542dcad4.png)

This is due to avoid security risks.
Please specify database username and password in environment variables as follows:

DB_USERNAME = username
DB_PASSWORD = passoword

You may set another username/password, but please be sure to make variable names UPPERCASE

## Overview
This project is a technical task. The main objective of the project is to expose the usage of CRUD API in Java. <br/>
The project operates create/read/delete/update operations on social network Post object as below in pseudo-code: 

Post {

    Date postDate

    String author

    String content

    Number viewCount

}


Besides that, it has also a functionality to retrieve top ten viewed posts from the database.
