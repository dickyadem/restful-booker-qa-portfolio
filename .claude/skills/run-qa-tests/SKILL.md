---
name: run-qa-tests
description: Run, debug, and fix the restful-booker QA portfolio test suites — Selenium (Maven/Java), Cypress, Playwright, and Postman/Newman. Use whenever the user asks to run tests, a suite fails, a build won't compile, or tests are flaky in this repo. Encodes the known gotchas (ELECTRON_RUN_AS_NODE breaking Cypress, booking date conflicts on the live site, JUnit 5 imports, overlay click interception).
---

# Run QA Tests (restful-booker portfolio)

This repo holds four independent QA stacks, all targeting the live demo site
`https://automationintesting.online`. Each lives in its own folder and is run separately.

| Stack      | Folder                      | Runner            |
|------------|-----------------------------|-------------------|
| Selenium   | `restful-booker-selenium`   | Maven + JUnit 5   |
| Cypress    | `restful-booker-cypress`    | Cypress (Electron)|
| Playwright | `restful-booker-playwright` | Playwright        |
| Postman    | `postman`                   | Newman CLI        |

Run commands from the repo root. Shell is **PowerShell on Windows**.

## CRITICAL global gotcha — Cypress / Electron

The system has `ELECTRON_RUN_AS_NODE=1` set permanently. This makes any Electron
app (Cypress) run as plain Node and fail to start with:
`Cypress.exe: bad option: --smoke-test`.

**Always unset it in the same command before running Cypress (or Electron-backed Playwright debug):**
```powershell
Remove-Item Env:ELECTRON_RUN_AS_NODE -ErrorAction SilentlyContinue
```
The env var resets each session, so this must be repeated every time.

## Shared gotcha — live-site booking date conflicts

The booking confirmation ("Return home") only renders if the submitted check-in/
check-out range is NOT already booked for that room. Tests that book the *default*
date (today→tomorrow, room 1) pass once, then conflict on the next run and time out
waiting for the confirmation. **Booking-flow tests must request a unique future date
range** via the `?checkin=YYYY-MM-DD&checkout=YYYY-MM-DD` query params (random 30–365
days ahead), not click "Book now" (which forces today). This is already applied in the
Selenium, Cypress, and Playwright booking specs — preserve it when editing.

---

## Selenium (Maven, Java 17, JUnit 5)

Run:
```powershell
mvn test -B -f "restful-booker-selenium\pom.xml"
```
- Use `-B` (batch mode) — otherwise dependency-download progress floods the output.
- Single test class: append `-Dtest=BookingTest`.

Known issues already fixed (watch for regressions):
- **POM must have exactly one `<?xml ?>` declaration** as line 1.
- **JUnit 5 only** — imports are `org.junit.jupiter.api.*`, not `org.junit`.
- **Overlay click interception** — `BookingPage` clicks via `JavascriptExecutor`
  (`scrollIntoView` + JS `click`) because a sticky banner covers links; don't revert
  to a plain `.click()`.
- CDP version warnings (`Unable to find CDP implementation matching ...`) are harmless.

## Cypress

Install (first time only — binary is ~300 MB, cached globally):
```powershell
npm install --prefix "restful-booker-cypress"
```
Run (note the env unset and direct local binary — `npx` from repo root tries to
reinstall Cypress globally and can corrupt the cache):
```powershell
Remove-Item Env:ELECTRON_RUN_AS_NODE -ErrorAction SilentlyContinue
& "restful-booker-cypress\node_modules\.bin\cypress.cmd" run --project "restful-booker-cypress" --spec "restful-booker-cypress/cypress/e2e/booking.cy.js"
```
- If you see `bad option: --smoke-test` → `ELECTRON_RUN_AS_NODE` is set; unset it.
- If you see `bad option` *after* unsetting, or a verify failure → cache is corrupt:
  `& "restful-booker-cypress\node_modules\.bin\cypress.cmd" cache clear` then `... install`.
- Config (`cypress.config.js`) excludes the scaffolded example specs
  (`1-getting-started`, `2-advanced-examples`) via `excludeSpecPattern`; don't run them.
- `cypress/support/e2e.js` swallows the app's own `uncaught:exception` errors
  (e.g. `Cannot read properties of undefined (reading 'length')`) — those come from
  the third-party Next.js app, not the tests.

## Playwright (ESM, Chromium)

Install (first time):
```powershell
npm install --prefix "restful-booker-playwright"
npx --prefix "restful-booker-playwright" playwright install chromium
```
Run:
```powershell
npx playwright test --config "restful-booker-playwright/playwright.config.js"
```
- `baseURL` is the live site; headless Chromium; `retries: 1`; HTML report.
- View report: `npx playwright show-report` from the project folder.

## Postman (Newman)

Newman is not installed globally. Run via npx:
```powershell
npx newman run "postman/Restful Booker - API Testing.postman_collection.json" -e "postman/Restful Booker - API Testing.postman_environment.json"
```

---

## Workflow

1. Identify which stack(s) the user means; default to all four if unspecified.
2. Run the suite with the command above. For Cypress, ALWAYS unset
   `ELECTRON_RUN_AS_NODE` first.
3. On failure, distinguish:
   - **Environment/tooling** (won't start, won't compile, won't install) → fix per the
     gotchas above.
   - **App-side flakiness** (booking confirmation missing, date conflict, app uncaught
     exception) → it's the live demo site's state, not the test logic; apply the
     unique-future-date pattern or ignore app exceptions.
4. Re-run to confirm green before reporting. For flaky booking flows, run twice.
