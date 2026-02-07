package com.example.springdatajpademo.repo;

import com.example.springdatajpademo.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {

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

