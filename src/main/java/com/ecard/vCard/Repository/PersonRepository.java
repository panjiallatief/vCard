package com.ecard.vCard.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecard.vCard.Entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    
}
