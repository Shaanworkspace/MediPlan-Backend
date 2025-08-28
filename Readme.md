# 💊 MediPlan – Digital Medication Reminder & Health Planner

A comprehensive healthcare management system that helps patients track medications, receive timely reminders, manage appointments, and share medical reports with doctors.

---

# 📌 Problem Statement for MediPlan

Many patients with chronic illnesses (e.g., diabetes, hypertension, heart disorders) require long-term medication that must be taken at specific times (daily, weekly, or multiple times a day). However, patients often:

- Forget doses due to busy schedules or memory lapses  
- Lose track of how many tablets are left and when to reorder  
- Have difficulty keeping a consistent medical history for doctors to review  

This results in **poor treatment outcomes, missed doses, health complications, and difficulty for doctors in monitoring compliance.**

👉 **Problem Statement:**  
There is a need for a digital solution that allows patients to plan, schedule, and track medications, receive reminders, monitor medicine stock, and share this data with their doctors for better treatment follow-up.

---

# 📝 Case Study: MediPlan – A Digital Medication Reminder & Health Planner System

## Entities and Details

- **Patient** *(Patient_ID, Name, Age, Gender, Email/Phone, Address, Medical_History)*  
- **Doctor** *(Doctor_ID, Name, Specialization, Contact_Info, Hospital/Clinic)*  
- **Medicine** *(Med_ID, Name, Manufacturer, Strength, Stock_Quantity, Expiry_Date)*  
- **Medical Plan** *(Plan_ID, Patient_ID, Med_ID, Dosage, Frequency, Start_Date, End_Date, Instructions)*  
- **Reminder** *(Reminder_ID, Plan_ID, Time, Frequency_Type [Daily/Weekly/Monthly], Status [Taken/Skipped])*  
- **Appointment** *(Appt_ID, Patient_ID, Doctor_ID, Date, Time, Purpose, Notes)*  
- **Medical Report** *(Report_ID, Patient_ID, Doctor_ID, Report_Date, File/Notes, Next_Checkup)*  

---

## 🔗 Relationships and Constraints

- Each patient can have many medicines in their medical plan; each medicine may belong to many patients. (**M:N via Medical Plan**)  
- Each medical plan generates scheduled reminders. (**1:N**)  
- Each patient can book many appointments with doctors; each appointment is linked to exactly one doctor. (**M:N but resolved via Appointment entity**)  
- Each doctor can access multiple patients’ history/medical reports; each report is prepared for exactly one patient. (**1:N**)  
- Medicine stock must be monitored — when stock is below threshold, the patient is notified.  

---

## ♾️ Participation & Cardinality

- **Patient–Medical Plan**: Mandatory, since every patient may have at least one plan. A plan cannot exist without a patient.  
- **Medical Plan–Reminder**: Total participation, since each plan yields reminders.  
- **Doctor–Appointment–Patient**: Both Patients and Doctors participate; cardinality is many-to-many, resolved via *Appointment*.  
- **Patient–Medical Report**: Optional, since patients may not always have uploaded/generated reports.  

---

## 📖 Assumptions

- Each patient will register with a unique **email/phone number**  
- A medical plan is specific to **one patient** but can contain multiple medicines  
- A reminder is always linked to exactly **one medical plan**  
- Doctors can see only the details/reports of patients who have **booked appointments** with them  
- Medicine stock tracking assumes the patient enters the **initial stock** when adding medicine  

---

## 🗂️ ER Diagram (High‑Level Components)

**Entities:**
- Patient  
- Doctor  
- Medicine  
- Medical_Plan  
- Reminder  
- Appointment  
- Medical_Report  

**Relationships:**
- Patient ↔ Medical_Plan ↔ Medicine  
- Medical_Plan ↔ Reminder  
- Patient ↔ Appointment ↔ Doctor  
- Patient ↔ Medical_Report ↔ Doctor  

---

⚡ *Note:* Since diagrams cannot be drawn directly here, you can use tools like **draw.io**, **Lucidchart**, **Creately**, or **ERDPlus** to map this structure visually.

---

## ✨ Features

- **User Management**: Multi-role system (Admin, Doctor, Patient, Caregiver)
- **Medicine Scheduling**: Create and manage medication plans with dosage details
- **Smart Reminders**: Automated notifications for medication times
- **Stock Tracking**: Monitor medicine inventory with low-stock alerts
- **Appointment System**: Book and manage doctor appointments
- **Medical Reports**: Upload and share reports between patients and doctors
- **Secure Authentication**: JWT-based API security

---

## 🛠️ Tech Stack

**Backend**
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA / Hibernate
- MySQL Database
- Maven

**Frontend**
- React 18
- Tailwind CSS
- Axios
- React Router

---

## 📂 Project Structure

```bash
MediPlan/
├── MediPlan-Frontend/               # React frontend
│   ├── public/                      # Static assets
│   ├── src/
│   │   ├── components/              # Reusable UI components
│   │   ├── pages/                   # Page-level components
│   │   ├── services/                # API calls using Axios
│   │   ├── hooks/                   # Custom React hooks (if any)
│   │   ├── utils/                   # Utility functions/helpers
│   │   └── App.jsx                  # Root React component
│   ├── package.json                 # Frontend dependencies & scripts
│   ├── vite.config.js / webpack     # Build configuration
│   └── README.md
│
├── MediPlan-Backend/                # Spring Boot backend
│   ├── src/main/java/com/mediplan/
│   │   ├── configuration/           # Spring Security & JWT config
│   │   ├── controller/              # API endpoints
│   │   ├── dto/                     # Request/Response DTOs
│   │   ├── entity/                  # JPA entities (database models)
│   │   ├── enums/                   # Enum classes
│   │   ├── filter/                  # JWT filter
│   │   ├── repository/              # Spring Data JPA repositories
│   │   ├── service/                 # Business logic services
│   │   └── MediPlanBackendApplication.java   # Main application entrypoint
│   │
│   ├── src/main/resources/
│   │   ├── application.properties   # Common config
│   │   ├── application-dev.properties
│   │   ├── application-prod.properties
│   │   ├── static/                  # Static resources (if any)
│   │   └── templates/               # HTML templates (if any)
│   │
│   ├── pom.xml                      # Maven build file
│   ├── Dockerfile                   # Dockerization support
│   ├── .env                         # Environment variables (local dev)
│   └── README.md
│
├── docs/                            # Documentation (ERD, diagrams, notes)
│   ├── ERD.png
│   └── architecture-diagram.png
│
└── README.md                        # Master project README (this file)
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8+
- Maven 3.9+

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/MediPlan-Backend.git
   cd MediPlan-Backend
