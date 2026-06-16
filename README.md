# Brew & Bean Coffee Shop

A one-page coffee shop website built with **Jakarta EE (J2EE)**, **PostgreSQL**, and **Podman**.

## Architecture

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Browser    │────▶│  Tomcat 10   │────▶│  PostgreSQL  │
│  (HTML/CSS)  │◀────│  (Servlets)  │◀────│  (Database)  │
└──────────────┘     └──────────────┘     └──────────────┘
                           │
                    ┌──────┴──────┐
                    │   MVC Pattern     │
                    │ Model │ View │ Ctrl│
                    └──────────────────┘
```

| Layer | Technology | Files |
|-------|-----------|-------|
| **View** | JSP + CSS + JS | `WEB-INF/index.jsp`, `css/style.css`, `js/script.js` |
| **Controller** | Jakarta Servlets | `HomeServlet.java`, `ContactServlet.java` |
| **Model** | POJOs | `MenuItem.java`, `Testimonial.java`, `ContactMessage.java` |
| **DAO** | JDBC | `MenuItemDAO.java`, `TestimonialDAO.java`, `ContactMessageDAO.java` |
| **Database** | PostgreSQL | Schema in `init.sql` |
| **Container** | Podman | `Containerfile` + `compose.yaml` |

---

## Prerequisites

- [Podman](https://podman.io/docs/installation) installed
- [Podman Compose](https://github.com/containers/podman-compose) installed (`pip install podman-compose`)

---

## Run Locally with Podman

### 1. Build and Start

```bash
podman-compose up --build
```

This starts two containers:
- **`db`** — PostgreSQL 16 with schema + seed data
- **`app`** — Tomcat 10 with the compiled WAR

### 2. Open the Site

```
http://localhost:8080
```

The `HomeServlet` loads menu items and testimonials from PostgreSQL and renders them in `index.jsp`.

### 3. Stop

```bash
podman-compose down
```

To also delete the database volume:

```bash
podman-compose down -v
```

---

## Project Structure

```
coffee-shop/
├── pom.xml                          # Maven build (Jakarta EE 6, JDBC)
├── Containerfile                    # Multi-stage Podman build
├── compose.yaml                     # Podman Compose (app + db)
├── init.sql                         # DB schema + seed data
├── .gitlab-ci.yml                   # GitLab CI pipeline
├── src/
│   └── main/
│       ├── java/
│       │   └── com/coffeeshop/
│       │       ├── model/           # MenuItem, Testimonial, ContactMessage
│       │       ├── dao/             # DatabaseConnection, *DAO
│       │       └── controller/      # HomeServlet, ContactServlet
│       ├── resources/
│       │   └── db.properties        # DB connection config
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── web.xml
│           │   └── index.jsp        # Main page (JSTL)
│           ├── css/
│           │   └── style.css
│           └── js/
│               └── script.js
└── README.md
```

---

## How It Works (MVC Flow)

### Home Page
1. Browser requests `http://localhost:8080/`
2. `HomeServlet` (Controller) queries `MenuItemDAO` + `TestimonialDAO` (Model)
3. DAOs fetch data from PostgreSQL
4. Servlet forwards to `WEB-INF/index.jsp` (View)
5. JSP renders the page with menu items and testimonials via JSTL

### Contact Form
1. User fills form and clicks Submit
2. Form POSTs to `/contact`
3. `ContactServlet` validates input
4. `ContactMessageDAO` saves to `contact_messages` table
5. Redirects back to home with success/error flash message

### Database Tables

```sql
menu_items:       id, name, price, icon
testimonials:     id, text, author
contact_messages: id, name, email, message, created_at
```

---

## GitLab CI/CD Pipeline

Pipeline stages:
1. **test-compile** — Verifies the project compiles with Maven
2. **build-war** — Packages the WAR file
3. **pages** — Archives the WAR as an artifact

See `.gitlab-ci.yml` for details.

---

## Container Details

### Containerfile (Multi-stage)
- **Stage 1 (build)**: `maven:3-eclipse-temurin-21` — compiles and packages WAR
- **Stage 2 (runtime)**: `tomcat:10-jdk21` — deploys WAR as ROOT

### compose.yaml Services

| Service | Image | Port | Purpose |
|---------|-------|------|---------|
| `db` | postgres:16 | 5432 | Database |
| `app` | custom (built) | 8080 | Tomcat + WAR |

The app waits for DB via `healthcheck` before starting.

---

## Key Files Reference

| File | Purpose |
|------|---------|
| `pom.xml` | Maven config with Jakarta Servlet 6 + PostgreSQL JDBC 42 |
| `src/main/resources/db.properties` | JDBC connection string (`db:5432` hostname from compose) |
| `compose.yaml` | Orchestrates PostgreSQL + Tomcat containers |
| `Containerfile` | Multi-stage build: Maven → Tomcat |
| `init.sql` | Runs on first DB startup, creates tables + seed data |
| `.gitlab-ci.yml` | CI: compile, package, archive |

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| Port 8080 in use | Change `"8080:8080"` in `compose.yaml` to `"8081:8080"` |
| DB connection refused | Wait for healthcheck; run `podman-compose logs db` |
| Changes not reflected | Run `podman-compose up --build` to rebuild the WAR |
| Windows line endings | Use `git config core.autocrlf input` before committing |
| Podman not found | Install from https://podman.io/docs/installation |
