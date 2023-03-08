package com.ecard.vCard.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecard.vCard.Repository.PersonRepository;

public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/Person")
    // public ResponseEntity<Map> Person(){
        // Map data = new HashMap<>();
    public String HELLO(){
        return "index";
    }
    
}
