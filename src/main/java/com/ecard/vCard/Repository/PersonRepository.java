package com.ecard.vCard.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecard.vCard.Entity.Person;

@Repository 
public interface PersonRepository extends JpaRepository<Person, Integer> {
    
    @Query(value = "SELECT * FROM person WHERE username = ?1",
        countQuery = "SELECT count(*) FROM person WHERE username = ?1",
        nativeQuery = true)
    Optional <Person> findbyUsername(String username);
}
