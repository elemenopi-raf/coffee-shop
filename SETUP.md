# Brew & Bean Coffee Shop — Step-by-Step Setup

A one-page coffee shop website built with **Jakarta EE (J2EE)**, **PostgreSQL**, and **Podman**.

---

## Prerequisites

| Tool | Windows Install |
|------|----------------|
| **JDK 21+** | `winget install EclipseAdoptium.Temurin.21.JDK` or [manual](https://adoptium.net/) |
| **Apache Maven** | `winget install Apache.Maven` or [manual](https://maven.apache.org/download.cgi) |
| **Podman** | `winget install RedHat.Podman` |
| **PostgreSQL 18** | [Download](https://www.postgresql.org/download/windows/) |
| **pgAdmin** | Included with PostgreSQL installer |

Verify installation:
```powershell
java -version
mvn -version
podman --version
psql --version
```

---

## Step 1: Clone the Repository

```bash
git clone https://gitlab.com/elemenopi-raf/coffee-shop.git
cd coffee-shop
```

### Project Structure

```
coffee-shop/
├── pom.xml                          # Maven: Jakarta Servlet 6 + PostgreSQL JDBC + JSTL
├── Containerfile                    # Multi-stage Podman build (Maven -> Tomcat)
├── compose.yaml                     # (Optional) App + DB container orchestration
├── init.sql                         # Database schema + seed data
├── .gitlab-ci.yml                   # GitLab CI pipeline
├── src/
│   └── main/
│       ├── java/com/coffeeshop/
│       │   ├── model/               # MenuItem, Testimonial, ContactMessage
│       │   ├── dao/                 # DatabaseConnection, MenuItemDAO, TestimonialDAO, ContactMessageDAO
│       │   └── controller/          # HomeServlet, ContactServlet
│       ├── resources/
│       │   └── db.properties        # JDBC connection config
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── index.jsp        # Main page (JSTL)
│           │   └── web.xml          # Web deployment descriptor
│           ├── css/
│           │   └── style.css        # Page styles
│           └── js/
│               └── script.js        # Contact form validation
├── SETUP.md                         # This file
└── README.md                        # Overview
```

---

## Step 2: Set Up the Database

### 2a. Run init.sql in pgAdmin

1. Open **pgAdmin**
2. Connect to your local PostgreSQL (host: `localhost`, port: `5432`, user: `postgres`, password: `root`)
3. Right-click the `postgres` database -> **Query Tool**
4. Open and run `init.sql` (or paste its contents)

This creates three tables:

| Table | Columns |
|-------|---------|
| `menu_items` | `id`, `name`, `price`, `icon` |
| `testimonials` | `id`, `text`, `author` |
| `contact_messages` | `id`, `name`, `email`, `message`, `created_at` |

And seeds:
- **6 menu items**: Espresso ($5.50), Latte ($8.50), Cappuccino ($9.00), Mocha ($12.00), Cold Brew ($6.50), Flat White ($8.25)
- **3 testimonials** from Sarah L., James M., and Emily R.

### 2b. Verify

```sql
SELECT * FROM menu_items;    -- expect 6 rows
SELECT * FROM testimonials;  -- expect 3 rows
```

---

## Step 3: Configure Database Connection

File: **`src/main/resources/db.properties`**

```properties
db.url=jdbc:postgresql://172.31.224.1:5432/postgres
db.user=postgres
db.password=root
```

> **Why not `localhost`?** Inside a Podman container on Windows, `localhost` refers to the container itself, not your Windows machine. The WSL2 gateway IP `172.31.224.1` is what reaches your Windows host from inside the container.
>
> The special DNS name `host.containers.internal` (mapped to `169.254.1.2` via gvproxy) does **not** forward arbitrary host ports like 5432, so we use the WSL2 gateway IP instead.

---

## Step 4: Build the Podman Image

```powershell
podman build -t coffee-shop -f Containerfile .
```

The `Containerfile` uses a **multi-stage build**:

1. **Stage 1 (build)** — `maven:3-eclipse-temurin-21` compiles the project and packages `coffee-shop.war`
2. **Stage 2 (runtime)** — `tomcat:10-jdk21` copies the WAR as `ROOT.war` and exposes port 8080

---

## Step 5: Allow Container-to-Host Database Access

The container runs inside a WSL2 VM on the `172.31.224.0/20` subnet. By default, PostgreSQL rejects connections from non-localhost IPs.

### 5a. Edit pg_hba.conf

File: `C:\Program Files\PostgreSQL\18\data\pg_hba.conf`

Add this line **after** the IPv4 localhost line:

```
# IPv4 local connections:
host    all             all             127.0.0.1/32            scram-sha-256
host    all             all             172.31.224.0/20         scram-sha-256    <-- add this
```

### 5b. Add Windows Firewall Rule

Run PowerShell **as Administrator**:

```powershell
New-NetFirewallRule -DisplayName "PostgreSQL Podman" `
  -Direction Inbound -Protocol TCP -LocalPort 5432 `
  -RemoteAddress 172.31.224.0/20 -Action Allow
```

### 5c. Reload PostgreSQL Configuration

```powershell
$env:PGPASSWORD="root"
& "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -c "SELECT pg_reload_conf();"
```

---

## Step 6: Run the Container

```powershell
podman run -d --name app -p 8080:8080 coffee-shop
```

Wait ~5 seconds for Tomcat to start, then test:

```powershell
curl.exe -s -o NUL -w "%{http_code}" http://localhost:8080/
```

Expected output: `200`

---

## Step 7: Verify the Site

Open **http://localhost:8080/** in a browser.

You should see:
- **Page title**: Brew & Bean Coffee Shop
- **Navigation**: Home, Menu, Testimonials, Contact
- **Menu section**: 6 drink items with prices and icons
- **Testimonials section**: 3 customer quotes with author names
- **Contact form**: Name, email, message fields with client-side validation

### Verify static assets:

```powershell
curl.exe -s -o NUL -w "%{http_code}" http://localhost:8080/css/style.css   # Expect 200
curl.exe -s -o NUL -w "%{http_code}" http://localhost:8080/js/script.js     # Expect 200
```

---

## Step 8: Managing the Container

| Action | Command |
|--------|---------|
| Stop | `podman stop app` |
| Remove | `podman rm app` |
| Rebuild and run | `podman build -t coffee-shop -f Containerfile . ; podman run -d --name app -p 8080:8080 coffee-shop` |
| View logs | `podman logs app` |
| Follow logs | `podman logs -f app` |
| Enter container | `podman exec -it app bash` |
| List containers | `podman ps -a` |

---

## Step 9: Git Workflow (Feature Branch)

```bash
# Create and switch to a feature branch
git checkout -b feature/my-changes

# Stage changes
git add .

# Commit
git commit -m "Describe what you changed"

# Push to GitLab
git push origin feature/my-changes
```

Then open a **Merge Request** on GitLab from `feature/my-changes` into `main`.

To reset your local `main` back to origin:
```bash
git checkout main
git reset --hard origin/main
```

---

## Architecture Overview (MVC Flow)

```
Browser ----> HomeServlet ----> MenuItemDAO -----> PostgreSQL
                |                    |                    |
                |              List<MenuItem>             |
                |                    |                    |
                |           TestimonialDAO ---------------|
                |                    |                    |
                |         List<Testimonial>               |
                |                    |                    |
                +---> index.jsp <----+--------------------+
                          |
                    JSP renders:
                    - Menu items via ${menuItems}
                    - Testimonials via ${testimonials}
                    - Contact form (POST to /contact)

Browser ----> POST /contact ----> ContactServlet ----> ContactMessageDAO ----> PostgreSQL
                                      |
                                 redirect to /
```

### Key Code References

| File | Line(s) | Purpose |
|------|---------|---------|
| `HomeServlet.java` | `@WebServlet("")` | Maps to root without catching static files |
| `HomeServlet.java:16` | `MenuItemDAO.getAll()` | Fetches menu from DB into request attribute |
| `DatabaseConnection.java:28` | `DriverManager.getConnection(...)` | Reads `db.properties` for connection |
| `index.jsp` | `<%@ taglib ... %>` | Imports JSTL tag library |
| `index.jsp` | `<c:forEach items="\${menuItems}">` | Loops over menu items to render |
| `db.properties` | `jdbc:postgresql://172.31.224.1:5432/postgres` | Connection string to local PostgreSQL |
| `Containerfile` | `FROM maven:3-eclipse-temurin-21 AS build` | Stage 1: compile and package |
| `Containerfile` | `FROM tomcat:10-jdk21` | Stage 2: Tomcat runtime |
| `pom.xml` | `jakarta.servlet.jsp.jstl-api` + `glassfish-jstl` | JSTL taglib dependencies |

---

## Troubleshooting

| Symptom | Likely Cause | Fix |
|---------|--------------|-----|
| 500 error, logs: `Connection refused` | pg_hba.conf blocks container IP | Add `172.31.224.0/20` to pg_hba.conf |
| 500 error, logs: `Connection refused` (after pg_hba fix) | Windows Firewall blocks 5432 | Add firewall rule for port 5432 from WSL2 subnet |
| 500 error after WSL2 restart | WSL2 gateway IP changed | Run `wsl ip route show default`, update `db.properties` with new gateway |
| 404 for `/css/style.css` or `/js/script.js` | Servlet mapped to `"/"` intercepts all paths | Use `@WebServlet("")` (empty string), not `@WebServlet("/")` |
| JSP shows JSTL tags as raw text (e.g. `${menuItems}`) | JSTL jars missing at runtime | Add `jakarta.servlet.jsp.jstl-api` and `glassfish-jstl` to `pom.xml` |
| `Page not found` on Netlify | Netlify is static-only, can't run Java | Use Podman/Tomcat hosting instead |
| Port 8080 already in use | Another process on that port | Change to `-p 8081:8080` |
| `host.containers.internal:5432` not working | gvproxy doesn't forward arbitrary ports | Use WSL2 gateway `172.31.224.1` instead (run `wsl ip route show default` to confirm) |
| `podman build` slow on first run | Maven downloading dependencies | Subsequent builds will be faster (cached layers) |

### Finding the WSL2 Gateway IP (if it changes)

```powershell
wsl ip route show default
```

Example output: `default via 172.31.224.1 dev eth0`

Take the gateway IP (`172.31.224.1`) and update `db.properties`:
```properties
db.url=jdbc:postgresql://172.31.224.1:5432/postgres
```

Then rebuild and rerun:
```powershell
podman stop app
podman rm app
podman build -t coffee-shop -f Containerfile .
podman run -d --name app -p 8080:8080 coffee-shop
```
