# Journal App Backend

This is the backend for the **Journal App**, built using Spring Boot. It handles authentication (with JWT), journal management, weather data integration, and uses Redis for caching and MongoDB for persistent storage.

---

## Tech Stack

* Java 17
* Spring Boot (Web, Security, Data MongoDB, Data Redis, Mail)
* MongoDB (Atlas)
* Redis (Cloud or Docker)
* JWT for authentication
* Gmail SMTP for mail
* Weather API integration
* Lombok
* Docker support (for Redis)

---

## Project Structure

* `controller/` — API controllers
* `repository/` — Spring Data repositories
* `security/` — JWT configuration and filters
* `service/` — business logic and Redis caching
* `config/` — custom app configs
* `util/` — utility classes
* `JournalApplication.java` — main entry point

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/journal-app-backend.git
cd journal-app-backend
```

### 2. Create a `.env` file in the project root

Add the variables mentioned in [.env.example](.env.example)


### 3. Start Redis (for local development)

Use Docker:

```bash
docker run -d --name journal-redis -p 6379:6379 redis
```

If using Redis with a password (e.g., Redis Cloud), update the Redis URI in `application.yml`.

---

## Run the App

```bash
./mvnw spring-boot:run
```

Backend will be available at `http://localhost:8080`.

---

## Running Tests

Use:

```bash
./mvnw test
```

For integration tests, make sure required environment variables are either in `.env`, or passed using `@TestPropertySource`.

---

## Auth & Security

* Uses **JWT tokens stored in HttpOnly cookies** for authentication.
* Register and login endpoints return a response with the JWT in a secure, HttpOnly cookie.
* On each subsequent request, the client must send the cookie — no need to manually include an `Authorization` header.
* Logout clears the cookie on the client.
* CSRF protection is managed as per stateless JWT strategy.

---

## Weather & Redis

* Weather data is fetched from a 3rd-party API.
* The data is cached in Redis for better performance and rate-limit handling.
* TTL for cache is configurable.

---

## Email Support

* Uses Gmail SMTP to send emails.
* Make sure to allow "less secure apps" or use an app password if 2FA is enabled.

---

## API Endpoints (Basic)

* `POST /auth/register` – Register a new user
* `POST /auth/login` – Login and receive JWT token
* `GET /user` – Get authenticated user info
* `GET /journal` – List all journal entries
* `POST /journal` – Create a journal entry

---

## Notes

* Redis connection issues are logged in `RedisService`.
* MongoDB and Redis URIs are loaded from `.env` at runtime using the `Dotenv` library.

---

## Contribution

Contributions and PRs are welcome. If you'd like to add features, fix bugs, or suggest improvements, feel free to create an issue or submit a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.