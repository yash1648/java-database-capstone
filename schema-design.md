## MySQL Database Design

Structured and relational data such as patients, doctors, appointments, and administrative data are stored in MySQL. The database ensures consistency using primary keys, foreign keys, and constraints.

---

### Table: patients

* **id**: INT, Primary Key, AUTO_INCREMENT
* **first_name**: VARCHAR(50), NOT NULL
* **last_name**: VARCHAR(50), NOT NULL
* **email**: VARCHAR(100), UNIQUE, NOT NULL
* **phone**: VARCHAR(15), NOT NULL
* **date_of_birth**: DATE
* **gender**: VARCHAR(10)
* **created_at**: DATETIME, DEFAULT CURRENT_TIMESTAMP

**Notes**

* Email must be unique.
* Phone and email format validation can be handled in backend application code.

---

### Table: doctors

* **id**: INT, Primary Key, AUTO_INCREMENT
* **first_name**: VARCHAR(50), NOT NULL
* **last_name**: VARCHAR(50), NOT NULL
* **specialization**: VARCHAR(100), NOT NULL
* **email**: VARCHAR(100), UNIQUE, NOT NULL
* **phone**: VARCHAR(15), NOT NULL
* **clinic_location**: VARCHAR(100)
* **created_at**: DATETIME, DEFAULT CURRENT_TIMESTAMP

**Notes**

* Each doctor may have their own schedule for available appointment slots.

---

### Table: appointments

* **id**: INT, Primary Key, AUTO_INCREMENT
* **doctor_id**: INT, Foreign Key → doctors(id)
* **patient_id**: INT, Foreign Key → patients(id)
* **appointment_time**: DATETIME, NOT NULL
* **status**: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)
* **reason**: VARCHAR(255)
* **created_at**: DATETIME, DEFAULT CURRENT_TIMESTAMP

**Constraints and Design Considerations**

* Doctors should not have overlapping appointments at the same time.
* Appointment history should be retained for future medical reference.
* If a patient is deleted, appointments may be archived rather than permanently removed.

---

### Table: admin

* **id**: INT, Primary Key, AUTO_INCREMENT
* **username**: VARCHAR(50), UNIQUE, NOT NULL
* **password_hash**: VARCHAR(255), NOT NULL
* **email**: VARCHAR(100), UNIQUE
* **role**: VARCHAR(50)
* **created_at**: DATETIME, DEFAULT CURRENT_TIMESTAMP

**Notes**

* Passwords must be stored in hashed format for security.

---

### Table: clinic_locations

* **id**: INT, Primary Key, AUTO_INCREMENT
* **clinic_name**: VARCHAR(100), NOT NULL
* **address**: VARCHAR(255), NOT NULL
* **city**: VARCHAR(100)
* **phone**: VARCHAR(15)

**Purpose**

* Supports multiple clinic branches if the system expands.

---

## MongoDB Collection Design

Some information such as prescriptions, doctor notes, logs, and attachments are flexible and do not fit well into strict relational tables. MongoDB is used to store such dynamic data.

---

### Collection: prescriptions

```json
{
  "_id": "ObjectId('64abc123456')",
  "appointmentId": 51,
  "patientId": 12,
  "doctorId": 7,
  "medications": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "frequency": "Every 6 hours",
      "duration": "5 days"
    },
    {
      "name": "Vitamin C",
      "dosage": "1000mg",
      "frequency": "Once daily",
      "duration": "7 days"
    }
  ],
  "doctorNotes": "Patient has mild fever. Monitor temperature regularly.",
  "attachments": [
    {
      "fileName": "blood_test_report.pdf",
      "fileType": "pdf"
    }
  ],
  "tags": ["fever", "general-checkup"],
  "createdAt": "2026-03-18T10:30:00Z"
}
```

**Design Considerations**

* MongoDB documents reference **patientId, doctorId, and appointmentId** rather than embedding the full relational objects.
* Arrays allow multiple medications to be stored in a single prescription.
* Flexible schema allows adding new fields like tags, notes, attachments, or metadata without requiring schema changes.

---

**Further Considerations**

* Prescriptions are tied to a specific appointment.
* Patient appointment history should be preserved for medical records.
* The schema design allows future expansion such as messaging systems, doctor logs, or patient feedback collections.

