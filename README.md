# Movies API

This is a Spring Boot RESTful API for managing movies, actors, and genres. The project allows you to create, read, update, and delete movies, actors, and genres, as well as search movies by title or filter by genre, year, or actor.

## Table of Contents
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Sample Data](#sample-data)
- [Testing](#testing)
- [Notes](#notes)

## Technologies
- Java 21
- Spring Boot 3.5
- Spring Data JPA
- H2 / SQLite Database
- Maven
- Postman (for testing)

## Project Structure
# Movies API

This is a Spring Boot RESTful API for managing movies, actors, and genres. The project allows you to create, read, update, and delete movies, actors, and genres, as well as search movies by title or filter by genre, year, or actor.

## Table of Contents
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Sample Data](#sample-data)
- [Testing](#testing)
- [Notes](#notes)

## Technologies
- Java 21
- Spring Boot 3.5
- Spring Data JPA
- H2 / SQLite Database
- Maven
- Postman (for testing)

## Project Structure
src/
├─ main/
│ ├─ java/com/filmsociety/movies/
│ │ ├─ controller/ ← REST controllers (Movie, Actor, Genre)
│ │ ├─ entity/ ← JPA entity classes
│ │ ├─ repository/ ← Spring Data JPA repositories
│ │ └─ service/ ← Business logic services
│ └─ resources/
│ └─ application.properties ← DB and Spring Boot config
├─ test/ ← Unit tests
pom.xml ← Maven build file
README.md ← Project documentation


## Getting Started

1. **Clone the repository:**
```bash
git clone <your-repo-url>
cd movie-api

2. **Build and run the project:**
```bash
mvn clean install
mvn spring-boot:run

3. **Access API on:**
```bash
http://localhost:8080/api/

API Endpoints
Movies

GET /api/movies — List all movies, optionally filter by genre, actor, or year.

GET /api/movies/{id} — Get a movie by ID.

POST /api/movies — Add a new movie.

PATCH /api/movies/{id} — Update an existing movie.

DELETE /api/movies/{id} — Delete a movie.

GET /api/movies/search?title=some_title — Search movies by title.

GET /api/movies/{id}/actors — List actors in a movie.

Actors

GET /api/actors — List all actors, optionally search by name (case-insensitive).

GET /api/actors/{id} — Get an actor by ID.

POST /api/actors — Add a new actor.

PATCH /api/actors/{id} — Update actor info.

DELETE /api/actors/{id} — Delete an actor.

Genres

GET /api/genres — List all genres.

GET /api/genres/{id} — Get a genre by ID.

POST /api/genres — Add a new genre.

PATCH /api/genres/{id} — Update genre info.

DELETE /api/genres/{id} — Delete a genre.

Sample Data

Genres: Action, Drama, Comedy, Thriller, Horror, Sci-Fi

Movies: 20 sample movies added

Actors: 15 sample actors added (with birth dates)

Relations: Movies linked to actors and genres

Testing

Use Postman to test API endpoints. Examples:

GET all movies:

GET http://localhost:8080/api/movies


Search movie by title:

GET c


Update actor birth date:

PATCH http://localhost:8080/api/actors/1
Headers: Content-Type: application/json
Body:
{
  "birthDate": "1974-11-11"
}


Add new genre:

POST http://localhost:8080/api/genres
Headers: Content-Type: application/json
Body:
{
  "name": "Fantasy"
}

Notes

Dependency Injection is used for services in controllers.

Spring Boot auto-configures database connection using application.properties.

Case-insensitive search implemented for actors by name.

Ensure Content-Type: application/json is set in Postman for POST/PATCH requests.
