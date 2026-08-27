# CodeCombat — Real-Time 1v1 Competitive Programming Platform

CodeCombat is a production-oriented, full-stack 1v1 real-time competitive programming platform inspired by modern competitive versus experiences. Two players face off on identical coding problems under a server-authoritative timer, submit code evaluated in an isolated execution sandbox, and are scored based on **Correctness → Execution Efficiency (70%) + Memory (30%) → Submission Speed**.

🌐 **Live Vercel Deployment**: [https://codecombat-frontend-five.vercel.app](https://codecombat-frontend-five.vercel.app)

---

## ⚡ Tech Stack

- **Frontend**: React 18, React Router v6, Tailwind CSS, Monaco Code Editor, STOMP/SockJS WebSockets, Axios, Lucide Icons, Canvas Confetti.
- **Backend**: Java 17, Spring Boot 3.2, Spring Security 6, Spring Data JPA, Spring WebSocket, Spring Data Redis, JJWT, Springdoc OpenAPI / Swagger.
- **Database**: MySQL 8.0 (with H2 in-memory fallback for zero-dependency development resilience).
- **Cache & Real-Time**: Redis 7 (Matchmaking sorted sets, match states, global leaderboards, Pub/Sub).
- **Judging Engine**: Subprocess execution sandbox supporting **Java, Python, C++, and JavaScript** with strict CPU, memory, and timeout limits.
- **Problem Bank**: 1,000 distinct curated DSA questions with starter codes in 4 languages, constraints, complexity metadata, and test cases.

---

## 🎮 Core Competitive Experience

```text
Find Opponent → Match Found → 3... 2... 1... GO! → Same Problem → 15:00 Server Timer →
Code in Monaco Editor → Run Tests → Submit Solution → Secure Sandbox Judge →
Compare Performance & Efficiency → Winner Fanfare → Elo Rating Update → Post-Match Analysis
```

---

## 🚀 Quick Start

### Option 1: Docker Compose (All Services in 1 Command)

```bash
cd codecombat
docker-compose up --build
```
- Frontend UI: `http://localhost:3000`
- Backend API & Swagger: `http://localhost:8080/swagger-ui.html`

---

### Option 2: Local Development

#### 1. Start Backend (Spring Boot)
```bash
cd backend
mvn spring-boot:run
```
The backend will automatically start on `http://localhost:8080` and seed:
- 1,000 Curated DSA Problems (`CD-0001` through `CD-1000`)
- Demo accounts:
  - `rishu` / `password123` (Rating: 1542, Rank: #248)
  - `rahul` / `password123` (Rating: 1518, Rank: #265)
  - `admin` / `admin123` (Rating: 2100, Role: ROLE_ADMIN)

#### 2. Start Frontend (React + Vite)
```bash
cd frontend
npm install
npm run dev
```
Frontend runs at `http://localhost:5173`.

---

## 📚 1,000-Problem Question Bank Distribution

| Topic Category | Total Problems | Easy | Medium | Hard |
| :--- | :---: | :---: | :---: | :---: |
| **Arrays & Hashing** | 120 | 30 | 66 | 24 |
| **Two Pointers & Sliding Window** | 80 | 20 | 44 | 16 |
| **Binary Search** | 70 | 18 | 38 | 14 |
| **Strings** | 70 | 20 | 38 | 12 |
| **Linked List** | 60 | 18 | 32 | 10 |
| **Stack & Monotonic Stack** | 60 | 16 | 32 | 12 |
| **Queue & Deque** | 30 | 8 | 16 | 6 |
| **Trees & BST** | 100 | 25 | 55 | 20 |
| **Heap / Priority Queue** | 50 | 12 | 28 | 10 |
| **Greedy** | 60 | 15 | 33 | 12 |
| **Backtracking** | 40 | 8 | 22 | 10 |
| **Graphs & DSU** | 120 | 26 | 68 | 26 |
| **Dynamic Programming** | 130 | 24 | 72 | 34 |
| **Bit Manipulation** | 40 | 12 | 20 | 8 |
| **Trie** | 20 | 4 | 12 | 4 |
| **Advanced DS & Algorithms** | 50 | 4 | 26 | 20 |
| **TOTAL** | **1,000** | **250** | **550** | **200** |

---

## ⚖️ Scoring & Efficiency Engine

CodeCombat implements normalized scoring so that writing an optimal algorithm ($O(n)$ vs $O(n^2)$) yields victory:

- **Correctness**: **600 pts** (dominant factor; failed solution cannot beat an accepted one)
- **Execution Efficiency**: **200 pts** (70% weight of normalized execution time)
- **Memory Efficiency**: **100 pts** (30% weight of normalized memory consumption)
- **Submission Speed**: **100 pts** (time remaining ratio bonus)
- **Total**: **1,000 pts**

Includes anti-jitter damping so minor hardware noise ($102\text{ms}$ vs $105\text{ms}$) produces no unfair scoring disparity.

---

## 📈 Elo Rating System

Standard FIDE-derived formula:
$$E_A = \frac{1}{1 + 10^{(R_B - R_A) / 400}}$$
$$R'_A = R_A + K \cdot (S_A - E_A)$$

With dynamic K-factors ($K=40$ for provisional contenders, $K=32$ for $<1600$, $K=24$ for $1600-2000$, and $K=16$ for $2000+$).

---

## 🛰️ Real-Time WebSocket Events

- `MATCH_FOUND`: Dispatched when Redis finds a matched pair in the rating window.
- `COUNTDOWN`: 3-second synchronized pre-match countdown.
- `MATCH_START`: Synchronizes start timestamp and starts 15-minute authoritative timer.
- `PLAYER_CODING`: Live telemetry indicator of opponent activity.
- `PLAYER_RUNNING`: Alerts opponent that code is running in sandbox.
- `PLAYER_ACCEPTED` / `PLAYER_WRONG`: Live test passed counters ($x/y$) without exposing source code.
- `PLAYER_DISCONNECTED`: Begins 30-second grace countdown before forfeiture.
- `PLAYER_RECONNECTED`: Restores live duel session.
- `MATCH_FINISHED`: Broadcasts full match verdict, Elo changes, and post-match deep analysis.

---

## 🛡️ Security & Integrity

- Never runs user code directly inside the Spring Boot thread pool.
- Process isolation with CPU limits, memory bounds, timeouts, and temporary sandboxed directories.
- Server is the single source of truth for timer, scoring, and winner calculations.
- Passwords hashed with BCrypt.
- Role-based authorization (`ROLE_USER`, `ROLE_ADMIN`).
