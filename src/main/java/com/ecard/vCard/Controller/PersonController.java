package com.ecard.vCard.Controller;

import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(value = "/Person")
    public String Person(@RequestParam (required = true) Integer id, Model model){
        Map data = new HashMap<>();
        if (!personRepository.existsById(id)) {
            return "error";
        }
        Person person = personRepository.findById(id).get();
        model.addAttribute("nama", person.getNama());
        model.addAttribute("email", person.getEmail());
        model.addAttribute("wa", person.getNo_wa());
        model.addAttribute("divisi", person.getDivisi());
        return "index";
    }

    @PostMapping(value = "/InputPerson")
    public ResponseEntity<Map> InputPerson(@RequestParam String nama, @RequestParam String divisi, @RequestParam String email, 
                                        @RequestParam String nowa, @RequestParam (required = false) Blob foto) {
        Map data = new HashMap<>();
        Person person = new Person();
        person.setNama(nama);
        person.setDivisi(divisi);
        person.setEmail(email);
        person.setNo_wa(nowa);
        // person.setFoto(foto);
        personRepository.save(person);

    data.put("icon", "success");
    data.put("message", "Sukses Insert Data");
    return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
}
