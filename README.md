# Brew & Bean Coffee Shop

A one-page coffee shop website built with HTML, CSS, and JavaScript.

---

## Beginner's Guide: CI/CD with GitLab

This guide walks you through testing, building, and deploying this coffee shop site using **GitLab** and **GitLab CI**.

---

### 1. Prerequisites

- A [GitLab.com](https://gitlab.com) account (free tier works)
- [Git](https://git-scm.com/downloads) installed on your machine
- A code editor (VS Code recommended)
- A web browser

---

### 2. Push the Code to GitLab

#### 2.1 Create a new project on GitLab

1. Log in to [gitlab.com](https://gitlab.com)
2. Click **"New project"** → **"Create blank project"**
3. Fill in:
   - **Project name:** `coffee-shop`
   - **Visibility Level:** `Public` (or Private if you prefer)
4. Click **"Create project"**

#### 2.2 Push your code

Open a terminal in the project folder and run:

```bash
git init
git add .
git commit -m "Initial commit: coffee shop website"
git remote add origin https://gitlab.com/YOUR_USERNAME/coffee-shop.git
git branch -M main
git push -u origin main
```

> Replace `YOUR_USERNAME` with your actual GitLab username.

---

### 3. Set Up GitLab CI/CD

GitLab CI uses a file called `.gitlab-ci.yml` at the root of your repository. Every time you push, GitLab runs the pipeline defined in that file.

Create `.gitlab-ci.yml` in the project root:

```yaml
# .gitlab-ci.yml

stages:
  - test
  - build
  - deploy

variables:
  CI_DEBUG_TRACE: "false"

# ── Stage 1: Test ──────────────────────────────────────────

test-html:
  stage: test
  image: alpine:latest
  script:
    - apk add --no-cache htmlhint
    - htmlhint index.html
  allow_failure: true
  only:
    - main
    - merge_requests

test-links:
  stage: test
  image: alpine:latest
  script:
    - apk add --no-cache curl
    - echo "Checking that index.html exists..."
    - test -f index.html && echo "PASS: index.html found" || (echo "FAIL: index.html missing" && exit 1)
    - echo "All static files present."
  only:
    - main
    - merge_requests

# ── Stage 2: Build ──────────────────────────────────────────

build-static:
  stage: build
  image: alpine:latest
  script:
    - mkdir -p public
    - cp index.html public/
    - echo "Build complete. Files in public/:"
    - ls -la public/
  artifacts:
    paths:
      - public/
    expire_in: 1 hour
  only:
    - main

# ── Stage 3: Deploy (GitLab Pages) ─────────────────────────

pages:
  stage: deploy
  image: alpine:latest
  script:
    - echo "Deploying to GitLab Pages..."
    - mkdir -p public
    - cp index.html public/
  artifacts:
    paths:
      - public/
    expire_in: never
  only:
    - main
  environment:
    name: production
    url: https://YOUR_USERNAME.gitlab.io/coffee-shop
```

---

### 4. Push and Watch the Pipeline

```bash
git add .gitlab-ci.yml
git commit -m "Add GitLab CI/CD pipeline"
git push
```

1. Go to your GitLab project
2. Click **"CI/CD"** → **"Pipelines"**
3. You should see a running pipeline with three stages:
   - ✅ **test** (check HTML and files)
   - 🔨 **build** (prepare static files)
   - 🚀 **deploy** (publish to GitLab Pages)

Click on each stage to view the live logs.

---

### 5. Enable GitLab Pages

After the pipeline succeeds:

1. Go to **Settings** → **Pages**
2. You'll see a URL like: `https://YOUR_USERNAME.gitlab.io/coffee-shop`
3. Click **"Visit"** to see your live coffee shop site

The default Pages URL is:
```
https://YOUR_USERNAME.gitlab.io/coffee-shop
```

---

### 6. Make a Change and Re-deploy

Let's trigger a new pipeline by making a simple change:

1. Edit the price of Espresso in `index.html`
2. Commit and push:

```bash
git add index.html
git commit -m "Update espresso price"
git push
```

The pipeline will automatically run and re-deploy to Pages.

---

### 7. Pipeline Explained

| Stage | Job | What it does |
|-------|-----|-------------|
| `test` | `test-html` | Runs `htmlhint` to validate HTML syntax |
| `test` | `test-links` | Checks that all static files exist |
| `build` | `build-static` | Copies files into a `public/` folder and saves them as artifacts |
| `deploy` | `pages` | Publishes the `public/` folder to GitLab Pages |

- **Artifacts** are files passed between stages (e.g., build output sent to deploy)
- **`only: main`** means the job runs only on the `main` branch
- **`allow_failure: true`** means the pipeline continues even if that job fails (good for optional linting)

---

### 8. Next Steps

| Topic | What to learn |
|-------|--------------|
| Add CSS linting | Add `stylelint` to the test stage |
| Add JS linting | Add `eslint` to the test stage |
| Add unit tests | Use a framework like Jest (even for simple logic) |
| Deploy to Netlify | Use Netlify CLI or deploy job with deploy tokens |
| Deploy to AWS S3 | Add `aws-cli` in the deploy stage |
| Add a custom domain | Configure it in GitLab Pages settings |
| Add a review app | Deploy each MR to a unique URL |
| Add Slack notifications | Use the `slack-notify` or webhook integration |

---

### 9. Troubleshooting

| Problem | Solution |
|---------|----------|
| Pipeline stuck | Check your GitLab runner — shared runners are free but may queue |
| Pages URL returns 404 | Wait 5 minutes after deploy; check **Settings → Pages** |
| `htmlhint` not found | `apk add` may fail if Alpine repo is down — retry |
| Push rejected | Run `git pull --rebase` first |
| Want to run a job manually | Use `when: manual` in the job definition |

---

### 10. Useful Commands

```bash
# Run pipeline locally (requires gitlab-runner)
gitlab-runner exec docker test-html

# View logs for the last pipeline
curl --header "PRIVATE-TOKEN: YOUR_TOKEN" https://gitlab.com/api/v4/projects/YOUR_PROJECT_ID/jobs

# Download artifacts
curl --header "PRIVATE-TOKEN: YOUR_TOKEN" https://gitlab.com/api/v4/projects/YOUR_PROJECT_ID/jobs/ARTIFACT_ID/artifacts
```

---

Happy brewing and happy deploying! ☕
