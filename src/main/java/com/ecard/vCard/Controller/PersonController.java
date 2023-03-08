package com.ecard.vCard.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecard.vCard.Entity.Person;
import com.ecard.vCard.Repository.PersonRepository;

public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/Person")
    public ResponseEntity<Map> Person(@RequestParam (required = true) Integer id){
        Map data = new HashMap<>();
        if (!personRepository.existsById(id)) {
            data.put("message", "Data Tidak Ditemukan");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
          }
          Person person = personRepository.findById(id).get();
        data.put("data", person);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
}
