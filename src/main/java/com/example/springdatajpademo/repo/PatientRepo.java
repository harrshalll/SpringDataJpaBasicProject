package com.example.springdatajpademo.repo;

import com.example.springdatajpademo.dto.BloodGrpResponseEntity;
import com.example.springdatajpademo.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
    Patient findByName(String name);

    List<Patient> findByBloodGrp(String bloodGrp);

    List<Patient> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    //JPQL
    @Query("select p from Patient p where p.birthDate  > :birthDate")
    List<Patient> findBornAfterDate(@Param("birthDate")LocalDate birthDate);
    //Projection In JPQL
    @Query("select new com.example.springdatajpademo.dto.BloodGrpResponseEntity(p.bloodGrp, " +"Count(p)) from Patient p group by p.bloodGrp")
    List<BloodGrpResponseEntity> countEachBloodGrpType();
    //Native Query Language
    @Query(value = "select * from patient_table", nativeQuery = true)
    Page<Patient> findByAll(Pageable pageable);
}
/*
Jpa Repository Extends ListCrud Repository, QueryByExampleExecutor Repository, PagingAndSortingRepository

 Hibernate Entity Lifecycle (compact diagram)

        new Entity()
            |
            v
        +-----------+
        | Transient |   New Entity is in transient State
        +-----------+
            |
            | save()/persist()/persistAndFlush()/saveOrUpdate()/update()
            v
        +-----------+            <--- get()/find()/load()
        | Persistent|  <-----------------------------   now entity is in persistent context
        +-----------+               (loaded/managed by EntityManage/Session)
         |   |   |
         |   |   | delete()
         |   |   v
         |   | +--------+
         |   | | Removed|  --(garbage collected)-->
         |   | +--------+
         |   |
         |   | detach()/close()/clear()
         |   v
         | +---------+
         | | Detached|  --(garbage collected)-->
         | +---------+
         |
         | save()/merge()/lock()/persist()  (re-attach / merge state)
         +---------------------------------> (back to Persistent)

We implement save(),delete(),find() method through jpa repository but behind the scene entity manager calls the persist(), remove(),get() methods.
Once an entity is in the Persistence Context, it is managed — and Hibernate may run DB queries when required.

 Legend:
  - Transient: newly created object, not associated with persistence context
  - Persistent: currently managed by EntityManager / Session
  - Detached: was persistent, now out of persistence context (no automatic dirty-check)
  - Removed: scheduled for deletion (still in persistence context until flush/commit)

 Notes:
  - Use EntityManager.find()/em.getReference() or Session APIs to load into Persistent state.
  - merge() copies state from a Detached instance into a Persistent instance.
  - delete()/remove() marks entity as Removed; actual DB DELETE happens on flush/commit.
*/

