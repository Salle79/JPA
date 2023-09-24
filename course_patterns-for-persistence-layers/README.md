# Design Patterns for Persistence Layers

Welcome to the example projects of the Design Patterns for Persistence Layers course. You can find the lectures in the [course section of the Persistence Hub](https://thorben-janssen.com/courses/design-patterns-for-persistence-layers).

This course is still a work in progress, and I will release a new lecture every week.

## Working with the example projects
The example projects require a database. GitHub Codespace or an IDE supporting Development Containers start up the database automatically. Or you can download and start the Docker configuration manually.

### GitHub Codespace or Development Containers integration
The easiest way to work with the example projects is to use a [free GitHub Codespace](https://github.com/features/codespaces) or the Development Containers of your IDE (supported by VSCode and IntelliJ). Both automatically start up the required database in a Docker container.

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/Persistence-Hub/course_patterns-for-persistence-layers)

### Manual Docker setup

I’m using a PostgreSQL database in all code samples and exercises of this course. I’ve prepared a docker-compose configuration that starts the database on localhost:5432 and an instance of pgAdmin on localhost:80:
[Docker Compose Configuration](https://thorben-janssen.com/mp-files/patterns-for-persistence-layers-docker.zip/).

To run the docker-compose command, please make sure that docker is installed on your machine before you download and extract the docker-compose configuration. You can then start the environment by executing the following commands in the folder that contains your docker-compose configuration:
- docker-compose build
- docker-compose up

After you start the docker-compose configuration, you can access your database at localhost:5432 using the user postgres and the password postgres. And you can access pgAdmin on localhost:80 using the username test@postgresql.org and the password postgres.

## About the course
A persistence layer consists of more than just a set of entity classes. You also need to write a lot of code that queries data and persists new or updates existing data. For most applications, that means implementing hundreds of classes with thousands of lines of code. If you want to keep all of that maintainable, you need to make a few decisions:
- Will your business code execute queries and persist new records? Or do you create a separate persistence layer?
- If you implement a persistence layer, what level of abstraction shall it provide? Does it only perform low-level operations, e.g., persist 1 entity? Or does it provide a logical abstraction and persist a graph of dependent entity objects?
- Do you want to follow Domain Driven Design principles?
- Do you expose your entities in your API?
- Do you want to improve the usability of your entity classes?
- Based on these decisions, you need to define a set of general rules. You need to design your persistence layer.

And that’s when design patterns come into play. They provide reusable solutions to common problems in software development. They represent common best practices and are often used as building blocks of an application’s overall design.

There are several design patterns that you could use when implementing your persistence layer. You might know some of them by acronyms like DAO and DTO.

There are also several design anti-patterns that you should better avoid. One of the most famous examples is the open session in view anti-pattern.

This course will give you an overview of the patterns and anti-patterns you should know when designing your persistence layer. You will learn about their advantages and disadvantages and the contexts in which you might want to use them. I will also show you several examples of how to implement them and reference ready-to-use implementations provided by frameworks like Spring Data JPA.

Enjoy the course,

Thorben
