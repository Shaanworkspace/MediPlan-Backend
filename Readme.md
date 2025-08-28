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

## 📂 Entities and Attributes

### 1. **User (user_entity)**
- **Attributes:** User_ID (PK), First_Name, Last_Name, DOB, Gender, Email, Phone, Password, Address, Company, Account_Status (enabled/locked), etc.  
- **Role:** Represents all users in the system (patients, doctors, caregivers, admins).

---
### 2. **User_Roles (user_roles)**
- **Attributes:** ID (PK), Role {ADMIN, CARE_GIVER, DOCTOR, PATIENT}, User_ID (FK).  
- **Role:** Defines system roles of each user.

---

### 3. **Admin (admins)**
- **Attributes:** Admin_ID (PK), Full_Name, Email, Phone, Department, Address_Details, Profile_Photo.  
- **Role:** Special type of user with administration privileges.

---

### 4. **Doctor (doctors)**
- **Attributes:** Doctor_ID (PK), First_Name, Last_Name, Email, Phone_Number, Specialty, Address, Availability.  
- **Role:** Medical professionals who can view patient history, prescriptions, and appointments.

---

### 5. **Appointment (appointments)**
- **Attributes:** Appointment_ID (PK), Appointment_Date_Time, Mode (Online/Offline), Reason, Doctor_Name, Doctor_Specialization, Payment_Mode, Insurance_Details, Consent, Notes, Created_At, Updated_At, User_ID (FK).  
- **Role:** Represents patient appointments with doctors.

---

### 6. **Caregiver (caregiver_entity)**
- **Attributes:** Caregiver_Entity_ID (PK), Caregiver_ID (FK → User), Patient_ID (FK → User), Allow_Auto_Purchase.  
- **Role:** Assigns a caregiver to monitor patients’ medication and support needs.

---

### 7. **Medicine_Schedule (medicine_schedule_entity)**
- **Attributes:** Schedule_ID (PK), Medicine_Name, Dosage_Amount, Dosage_Instruction, Frequency, Start_Date, End_Date, Reminder_Enabled, Created_At, Updated_At, Notes.  
- **Role:** Tracks the plan of medicines a patient must follow.

---

### 8. **Dose_Times (dose_time_entity)**
- **Attributes:** Dose_ID (PK), Dose_Time, Medicine_Schedule_ID (FK).  
- **Role:** Defines times of day each medicine should be taken.

---

### 9. **Medicine_Schedule_Days (medicine_schedule_entity_days_of_week)**
- **Attributes:** Schedule_ID (FK), Day_Of_Week [0–6].  
- **Role:** Stores schedule repetition based on days of week.

---

### 10. **Medicine_Schedule_Extra_Times (medicine_schedule_entity_dose_take_time)**
- **Attributes:** Schedule_ID (FK), Take_Time.  
- **Role:** Additional dose-taking times.

---

### 11. **Medicine_Stock (medicine_stock_entity)**
- **Attributes:** Stock_ID (PK), Medicine_Name, Current_Quantity, Low_Stock_Threshold, Auto_Buy, Last_Updated.  
- **Role:** Tracks stock quantity and triggers alerts when stock is low.

---

### 12. **Reminder (reminder_entity)**
- **Attributes:** Reminder_ID (PK), Reminder_Date_Time, Message, Is_Sent, Medicine_Schedule_ID (FK).  
- **Role:** Notification entity that reminds patients based on schedule.

---

### 13. **Contacts (contacts)**
- **Attributes:** Contact_ID (PK), Address, Phone, Email, LinkedIn, GitHub, HackerRank.  
- **Role:** General contact table for storing extended contact details.

---

## 🔗 Relationships and Constraints

- **Users – Roles:**  
  1:N (One user can have multiple roles like Doctor + Admin).  

- **Users – Appointments:**  
  1:N (Each patient can book many appointments, each appointment belongs to one patient).  

- **Doctors – Appointments:**  
  Doctors are referenced by name/specialty inside appointment. (Better modeled as a relationship).  

- **Users – Caregiver:**  
  - A patient can have multiple caregivers.  
  - A caregiver can manage multiple patients.  
  - Relationship is **M:N** resolved via `caregiver_entity`.  

- **Patients – Medicine_Schedule:**  
  1:N (One patient can have multiple medication schedules).  

- **Medicine_Schedule – Dose_Times:**  
  1:N (Each schedule has many daily dose times).  

- **Medicine_Schedule – Days_Of_Week:**  
  1:N (Each schedule can map to multiple days).  

- **Medicine_Schedule – Stock:**  
  1:1 or 1:N (Each medicine being scheduled must correspond to stock tracking).  

- **Medicine_Schedule – Reminder:**  
  1:N (Each schedule creates many reminders).  

---

## 📊 Participation & Cardinality

- **User → Appointment:** **Total** participation (appointment must belong to a user).  
- **Caregiver → Patient:** **Partial**, since not all patients have caregivers.  
- **Medicine_Schedule → Reminder:** **Total**, if reminders are enabled.  
- **Medicine_Stock → Schedule:** **Mandatory**, since stock must exist for an active schedule.  

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
