package com.ecard.vCard.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecard.vCard.Entity.Person;
import com.ecard.vCard.Repository.PersonRepository;

public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/Person")
    public String Person(@RequestParam (required = true) Integer id, Model model){
        Map data = new HashMap<>();
        if (!personRepository.existsById(id)) {
            System.out.println("ID Tidak Ditemukan");
        }
        Person person = personRepository.findById(id).get();
        model.addAttribute("person", person);
        
        return "index";
    }
    
}
