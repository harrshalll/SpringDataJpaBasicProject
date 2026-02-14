package com.example.springdatajpademo.patientservices;

import com.example.springdatajpademo.entities.Patient;
import com.example.springdatajpademo.repo.PatientRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Transactional
    public Patient getPatientById(Long id){
        Patient p1 = patientRepo.findById(id).orElseThrow();

        Patient p2 = patientRepo.findById(id).orElseThrow();
        System.out.println(p1 == p2);

        return p1;
    }
    public Patient getPatientByName(String Name){
        Patient patient = patientRepo.findByName(Name);
        return patient;
    }
}

/*
When p1 calls findById(), Hibernate executes a SELECT query, loads the entity, and stores it in the first-level cache (Persistence Context).
When p2 calls findById() with the same id inside the same transaction, Hibernate first checks the Persistence Context, finds the entity, and returns the same managed instance — so p1 == p2 is true.

1️⃣ What is COMMIT?
Commit means: “Make all changes permanent in the database.”

Example (real life)
You transfer ₹1000 from Account A to B.
Steps:
Deduct ₹1000 from A
Add ₹1000 to B
If both succeed → COMMIT
If any step fails → ROLLBACK
BEGIN;
UPDATE account SET balance = balance - 1000 WHERE id = 1;
UPDATE account SET balance = balance + 1000 WHERE id = 2;
COMMIT;
After COMMIT:
Data is saved permanently
Even DB crash won’t undo it

What is ROLLBACK?
Rollback means: “Undo everything done in this transaction.”
BEGIN;
UPDATE account SET balance = balance - 1000 WHERE id = 1;
-- error happens here
ROLLBACK;

After ROLLBACK:
Account balance remains unchanged
No half-completed data

Why do we NEED commit & rollback?
Without them 😬
Step 1 succeeds
Step 2 fails
Database is broken ❌

With transactions:
All succeed → COMMIT ✅
Any fail → ROLLBACK 🔁
This guarantees data integrity.

What @Transactional does HERE
1️⃣ Transaction starts before method execution
When this method is called:
Spring opens a transaction
A Persistence Context (1st-level cache) is created

First findById(id) (p1)
Patient p1 = patientRepo.findById(id).orElseThrow();
What happens:
Spring Data JPA calls:
EntityManager.find(Patient.class, id)

Hibernate checks:
Persistence Context → empty ❌
Hibernate executes SQL:
SELECT * FROM patient WHERE id = ?
Result is converted into a Patient object
p1 becomes a managed entity.
Entity is stored in Persistence Context
✔ One DB query executed

3️⃣ Second findById(id) (p2)
Patient p2 = patientRepo.findById(id).orElseThrow();
Now the magic happens 🪄
Hibernate checks:
Persistence Context → entity with id already present ✅
So:
❌ NO SQL query runs
Hibernate returns the same object reference
p1 == p2   // true ✅
Both variables point to the same managed entity.

4️⃣ Method returns → transaction ends
Since no exception occurred:
Spring calls COMMIT
Hibernate flushes (no changes → no SQL)
Persistence Context is closed
✔ Method finishes cleanly
 */