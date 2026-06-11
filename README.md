# </> PrepAI — AI-Powered Interview Preparation Platform

[![Live Demo](https://img.shields.io/badge/Live%20Demo-Vercel-black?style=for-the-badge&logo=vercel)](https://interview-prep-ui.vercel.app)
[![Backend](https://img.shields.io/badge/Backend-Render-46E3B7?style=for-the-badge&logo=render)](https://interview-prep-backend-x7ui.onrender.com)
[![GitHub](https://img.shields.io/badge/Frontend-GitHub-181717?style=for-the-badge&logo=github)](https://github.com/HarshithaBurla/interview-prep-ui)

A full-stack AI-powered platform that helps developers prepare for technical interviews. Users can browse a curated question bank, practice with AI-generated questions, get instant AI feedback on their answers, take mock interviews, and track their progress over time.

---

## Live Links

| | URL |
|---|---|
| Frontend | https://interview-prep-ui.vercel.app |
| Backend API | https://interview-prep-backend-x7ui.onrender.com |
| Frontend Repo | https://github.com/HarshithaBurla/interview-prep-ui |
| Backend Repo | https://github.com/HarshithaBurla/interview-prep |

> Note: The backend is hosted on Render's free tier and may take 30–60 seconds to wake up on the first request.

---

## Features

- **JWT Authentication** — Secure register/login with BCrypt password hashing and stateless JWT tokens
- **Question Bank** — 25+ curated questions across Java, Spring Boot, SQL, and DSA with topic and difficulty filters
- **AI Question Generation** — Generate custom interview questions on any topic using Groq AI (Llama 3.3)
- **AI Answer Grading** — Submit your answer and receive a score out of 10, detailed feedback, and an improved model answer
- **Mock Interview Mode** — AI conducts a full 5-question interview and generates a performance report
- **Progress Dashboard** — Track total attempts, average score per topic, weak areas, and recent history
- **AI Coach** — Personalized advice based on your weak topics
- **Admin Role** — Role-based access control for creating, editing, and deleting questions

---

## Tech Stack

### Backend
| Technology | Purpose |
|---|---|
| Java 21 | Core language |
| Spring Boot 3.5 | Application framework |
| Spring Security | Authentication & authorization |
| JWT (jjwt) | Stateless token-based auth |
| Spring Data JPA | ORM and database access |
| PostgreSQL | Production database |
| Hibernate | ORM implementation |
| Maven | Build tool |
| Docker | Containerization |

### Frontend
| Technology | Purpose |
|---|---|
| React 18 | UI framework |
| Vite | Build tool |
| JavaScript (ES6+) | Core language |
| Fetch API | HTTP requests |
| CSS Variables | Theming and design tokens |

### AI & Cloud
| Technology | Purpose |
|---|---|
| Groq API (Llama 3.3-70b) | AI question generation and grading |
| Render | Backend hosting (Docker) |
| Vercel | Frontend hosting |
| GitHub | Version control |

---

## Architecture

```
┌─────────────────────┐         ┌──────────────────────────┐
│   React Frontend    │  HTTPS  │   Spring Boot Backend    │
│   (Vercel)          │────────▶│   (Render / Docker)      │
│                     │         │                          │
│  - Auth pages       │         │  - REST APIs             │
│  - Question Bank    │         │  - JWT Security          │
│  - Practice Mode    │         │  - JPA / Hibernate       │
│  - Mock Interview   │         │  - Groq AI Integration   │
│  - Dashboard        │         │                          │
└─────────────────────┘         └──────────┬───────────────┘
                                           │
                                           ▼
                                ┌──────────────────────┐
                                │   PostgreSQL DB      │
                                │   (Render)           │
                                │                      │
                                │  - users             │
                                │  - questions         │
                                │  - user_attempts     │
                                └──────────────────────┘
```

---

## API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and get JWT token |

### Questions
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/questions` | Get all questions |
| GET | `/api/questions?topic=java&difficulty=easy` | Filter questions |
| GET | `/api/questions/{id}` | Get question by ID |
| POST | `/api/questions` | Create question (Admin only) |
| PUT | `/api/questions/{id}` | Update question (Admin only) |
| DELETE | `/api/questions/{id}` | Delete question (Admin only) |

### AI
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/ai/generate` | Generate an AI question |
| POST | `/api/ai/grade` | Grade a user's answer |
| POST | `/api/ai/mock-interview` | Run a 5-question mock interview |

### Progress
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/progress/attempt` | Submit and save an attempt |
| GET | `/api/progress` | Get full progress dashboard |
| GET | `/api/progress/topic/{topic}` | Get attempts by topic |

---

## Running Locally

### Prerequisites
- Java 21+
- Maven
- PostgreSQL
- Node.js 18+
- Groq API key (free at https://console.groq.com)

### Backend

```bash
git clone https://github.com/HarshithaBurla/interview-prep.git
cd interview-prep
```

Create `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/interview_prep
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
jwt.secret=YourSecretKeyHere
groq.api.key=${GROQ_API_KEY}
server.port=8080
```

```bash
./mvnw spring-boot:run
```

### Frontend

```bash
git clone https://github.com/HarshithaBurla/interview-prep-ui.git
cd interview-prep-ui
npm install
npm run dev
```

Open http://localhost:5173

### Docker (optional)

```bash
cd interview-prep
docker-compose up --build
```

---

## Project Structure

```
interview-prep/
├── src/main/java/com/interviewprep/
│   ├── auth/               # Register, login, JWT DTOs
│   ├── config/             # Security, JWT filter, CORS
│   ├── question/           # Question entity, service, controller
│   ├── ai/                 # Groq AI integration
│   ├── progress/           # Attempt tracking, dashboard
│   └── user/               # User entity, repository
├── src/main/resources/
│   ├── application.properties
│   └── application-prod.properties
└── Dockerfile
```

---

## Screenshots

> Login Page · Dashboard · Practice Mode · Mock Interview

---

## What I Learned

- Implementing stateless JWT authentication with Spring Security filter chains
- Integrating third-party AI APIs (Groq) into a Spring Boot backend
- Designing a REST API with role-based access control using `@PreAuthorize`
- Fixing real-world issues like PostgreSQL JPQL type casting, Hibernate lazy loading, and CORS configuration
- Dockerizing a Spring Boot application for cloud deployment
- Deploying a full-stack app with separate frontend (Vercel) and backend (Render) hosting

---

## Author

**Harshitha Burla**
- GitHub: [@HarshithaBurla](https://github.com/HarshithaBurla)
- LinkedIn: [harshitha-burla](https://www.linkedin.com/in/harshitha-burla-526808331/)
- Email: harshithaburla@gmail.com
