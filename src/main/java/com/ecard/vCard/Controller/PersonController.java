package com.ecard.vCard.Controller;

import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecard.vCard.Entity.Person;
import com.ecard.vCard.Repository.PersonRepository;
import com.ecard.vCard.Util.ImageUtil;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @RequestMapping(value = "/InputPerson", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE }, produces = APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map> InputPerson(@RequestParam String nama, @RequestParam String divisi, @RequestParam String email, 
                                        @RequestParam String nowa, @RequestParam ("files") MultipartFile file) throws IOException {
        Map data = new HashMap<>();
        Person person = new Person();
        person.setNama(nama);
        person.setDivisi(divisi);
        person.setEmail("mailto:" + email);
        person.setNo_wa("http://wa.me/+62" + nowa);
        person.setFoto(ImageUtil.compressImage(file.getBytes()));
        personRepository.save(person);

    data.put("icon", "success");
    data.put("message", "Sukses Insert Data");
    return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
}
