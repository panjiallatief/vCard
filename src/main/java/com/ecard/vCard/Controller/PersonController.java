package com.ecard.vCard.Controller;

import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecard.vCard.Entity.Person;
import com.ecard.vCard.Repository.PersonRepository; 

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/")
    public String yoyo() {
        return "index";
    }

    @GetMapping(value = "/Register")
    public String register() {
        return "register";
    }

    @GetMapping(value = "/GetPerson")
    public ResponseEntity<Map> GetPerson(@RequestParam (required = true) Integer id){
        Map data = new HashMap<>();
        if (!personRepository.existsById(id)) {
            System.out.println("GADA");
            data.put("message", "Data Tidak Ditemukan");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
          }
          Person person = personRepository.findById(id).get();
          System.out.println("ADA");
        data.put("data", person);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // @PostMapping(value = "/InputPerson")
    // public ResponseEntity<Map> InputPerson(@RequestParam String nama, @RequestParam String divisi, @RequestParam String email, @RequestParam String nowa, @RequestParam Blob foto) {

    // }
    
}
