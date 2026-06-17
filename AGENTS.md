# AGENTS.md — Brew & Bean Coffee Shop

## Build & run

```bash
# Build WAR (no tests exist in project)
mvn clean package -DskipTests

# Full stack with Podman Compose
podman compose up --build --remove-orphans
```

- Java 21, Jakarta Servlet 6.0, WAR packaging, Maven, HikariCP 5.1.0.
- **No test framework** — `mvn test` does nothing. No linter, formatter, or typecheck.
- GitLab CI runs on `self-hosted` runners (not shared).

## Architecture

- `@WebServlet("")` (empty string) maps HomeServlet to `/` **without** intercepting static files. Never use `@WebServlet("/")` — that would catch `/css/*` and `/js/*` too.
- `@WebServlet("/contact")` handles POST from contact form.
- `@WebServlet("/newsletter")` handles POST from newsletter signup in footer.
- DAOs read `db.properties` from classpath. The DB URL uses `db:5432` (Compose service name), not `localhost`.
- DatabaseConnection uses HikariCP connection pool. Driver class must be set explicitly: `config.setDriverClassName("org.postgresql.Driver")` — without it HikariCP fails with `No suitable driver`.
- JSPs use JSTL (`jakarta.servlet.jsp.jstl-api` 3.0 + `glassfish-jstl` 3.0.1).

## Database schema (`init.sql`)

| Table | Columns |
|-------|---------|
| `menu_items` | id, name, price (NUMERIC(10,2)), icon, category (VARCHAR(20)), created_at |
| `testimonials` | id, text, author, created_at |
| `contact_messages` | id, name, email, message, created_at |
| `newsletter_emails` | id, email (UNIQUE), created_at |

## Key files

| File | Role |
|------|------|
| `Containerfile` | Multi-stage: Maven build → Tomcat 10 runtime |
| `compose.yaml` | Podman Compose (app + db PostgreSQL services, internal network) |
| `db.properties` | JDBC connection config (Compose service name: `db:5432`) |
| `init.sql` | Schema + seed data (11 menu items, 3 testimonials) |
| `DatabaseConnection.java` | HikariCP connection pool (driver class must be explicit) |
| `NewsletterServlet.java` | `@WebServlet("/newsletter")` — saves email with duplicate check |

## Frontend features

- Single-page layout: Hero, About, Menu (3 categories), Testimonials, Visit Us (Google Maps), Contact form, Footer (newsletter + social links)
- Sticky nav bar with scroll detection and `.scrolled` class
- Hamburger menu on mobile (slide-out nav, no wrapping)
- Scroll-triggered fade-in animations (Intersection Observer)
- Form loading states (spinner + disabled button on submit)
- Favicon via SVG data URI
- Responsive grid: menu 3-col → 2-col → 1-col, Visit Us 2-col → stacked

## Servlet to JSP mapping

| Servlet | JSP | Data loaded |
|---------|-----|-------------|
| HomeServlet (`""`) | `index.jsp` | MenuItems (by category), Testimonials |
| ContactMessageServlet (`"/contact"`) | `index.jsp` (redirect with flash) | — |
| NewsletterServlet (`"/newsletter"`) | `index.jsp` (redirect with flash) | — |

## Quirks

- HikariCP on Tomcat 10 + PostgreSQL driver requires explicit `config.setDriverClassName("org.postgresql.Driver")` even though the driver is on the classpath. Without this, `DriverManager.getDriver()` returns `null` because PostgreSQL's `Driver` class hasn't been registered yet.
- `podman compose` (Docker Compose CLI plugin) works; `podman-compose` (Python tool) may not. Use `podman compose` consistently.
- If WSL2 restarts, the Podman machine may need restarting: `podman machine stop; podman machine start`.
- Containers cannot use `host.containers.internal:5432` — gvproxy doesn't forward arbitrary ports.
