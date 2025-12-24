# Hiring-Pipeline-Backend
Production-grade hiring workflow backend built with Java &amp; Spring Boot, featuring AI-assisted resume matching and explainable candidate scoring.

# Hiring Pipeline Backend (AI-Enhanced)

A **production-grade backend system** built using **Java and Spring Boot** to manage end-to-end hiring workflows, enhanced with an **AI-assisted resume matching layer** to demonstrate adaptability to emerging technologies.

This project simulates a **real-world internal Applicant Tracking System (ATS)** used by companies to manage jobs, candidates, interview stages, and shortlisting — with a strong focus on **backend engineering principles** rather than UI-heavy implementations.

---

## 🚀 Why This Project?

Many ATS or resume tools focus mainly on UI or black-box AI scoring.  
This project is intentionally **backend-first** and focuses on:

- Clean REST API design
- Secure authentication & authorization
- Business workflow modeling
- Database design & data consistency
- Explainable AI integration

The goal is to demonstrate **real-world backend engineering skills** expected from an **SDE-1 / Backend Engineer**.

---

## 🧩 Core Features

### 1️⃣ Hiring Workflow Management (Primary)
- Create and manage job postings
- Add and manage candidates
- Move candidates across hiring stages  
  `APPLIED → SCREENING → INTERVIEW → OFFER → HIRED`
- Track interview rounds and interviewer feedback
- Role-based access control (HR / Interviewer)

### 2️⃣ Resume Intelligence (AI-Assisted)
- Upload and parse resumes (PDF)
- Extract relevant skills from resume content
- Match resumes against job descriptions
- Generate **explainable matching results**:
  - Matched skills
  - Missing skills
  - Final match score

> AI is used as an **assistive layer**, not as a black-box dependency.

---

## 🏗️ High-Level Architecture
```mermaid
flowchart TD
Client[Client]
Client --> API[SpringBootAPI]

API --> Auth[AuthService]
API --> Hiring[HiringWorkflow]

Hiring --> Jobs[Jobs]
Hiring --> Candidates[Candidates]
Hiring --> Interviews[Interviews]

API --> AI[AIMatching]

AI --> Resume[ResumeParsing]
AI --> Matching[SkillMatching]
AI --> Scoring[Scoring]

Auth --> DB[(Database)]
Jobs --> DB
Candidates --> DB
Interviews --> DB
Resume --> DB
Scoring --> DB
```
## 🏗️ DB Schema
```mermaid
erDiagram

USERS {
    LONG id PK
    STRING name
    STRING email
    STRING password
    STRING role
    DATETIME created_at
}

JOBS {
    LONG id PK
    STRING title
    STRING description
    STRING department
    STRING status
    DATETIME created_at
}

CANDIDATES {
    LONG id PK
    STRING name
    STRING email
    STRING phone
    STRING current_stage
    DATETIME created_at
}

RESUMES {
    LONG id PK
    LONG candidate_id FK
    STRING file_path
    STRING extracted_text
    DATETIME uploaded_at
}

INTERVIEW_ROUNDS {
    LONG id PK
    LONG candidate_id FK
    STRING round_name
    STRING interviewer
    STRING status
    DATETIME scheduled_at
}

FEEDBACK {
    LONG id PK
    LONG interview_round_id FK
    STRING reviewer
    STRING comments
    INT rating
}

AI_MATCH_RESULTS {
    LONG id PK
    LONG candidate_id FK
    LONG job_id FK
    INT match_score
    STRING matched_skills
    STRING missing_skills
    DATETIME evaluated_at
}

USERS ||--o{ JOBS : creates
JOBS ||--o{ CANDIDATES : receives
CANDIDATES ||--o{ RESUMES : uploads
CANDIDATES ||--o{ INTERVIEW_ROUNDS : attends
INTERVIEW_ROUNDS ||--o{ FEEDBACK : has
CANDIDATES ||--o{ AI_MATCH_RESULTS : evaluated
JOBS ||--o{ AI_MATCH_RESULTS : evaluated
```
## 🏗️ Sequence Diagram
```mermaid
sequenceDiagram
actor Recruiter
participant Client
participant API as SpringBootAPI
participant ResumeSvc as ResumeService
participant AISvc as AIMatchingService
participant DB as Database

Recruiter ->> Client: Upload resume for candidate
Client ->> API: POST /resumes/upload
API ->> ResumeSvc: Validate & store resume
ResumeSvc ->> DB: Save resume metadata & text
ResumeSvc ->> AISvc: Trigger resume-job matching
AISvc ->> DB: Fetch job requirements
AISvc ->> AISvc: Extract skills & compute score
AISvc ->> DB: Save match results
AISvc -->> API: Return match score & insights
API -->> Client: Response with explainable score

