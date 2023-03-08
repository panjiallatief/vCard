package com.ecard.vCard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecard.vCard.Entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    
}
