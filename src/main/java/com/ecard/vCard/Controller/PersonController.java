package com.ecard.vCard.Controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.ecard.vCard.Entity.Image;
import com.ecard.vCard.Entity.Person;
import com.ecard.vCard.Repository.ImageRepository;
import com.ecard.vCard.Repository.PersonRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private Environment env;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/")
    public String Persons(Authentication authentication, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        httpSession.setAttribute("username", username);
        System.out.println(httpSession.getAttribute("username"));
        System.out.println(username);
        return "register";
    }

    @GetMapping(value = "/Person/{username}")
    public String Person(@PathVariable (required = true) String username, Model model){

        Person person = personRepository.findbyUsername(username).get();
        model.addAttribute("nama", person.getNama());
        model.addAttribute("email", person.getEmail());
        model.addAttribute("wa", person.getNo_wa());
        model.addAttribute("divisi", person.getDivisi());
        model.addAttribute("username", person.getUsername());
        model.addAttribute("namafile", person.getNamafile());
        return "index";
    }

    @RequestMapping(value = "/InputPerson", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE }, produces = APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map> InputPerson(@RequestParam String nama, @RequestParam String divisi, @RequestParam String email, 
                                        @RequestParam String nowa, @RequestParam ("files") MultipartFile file) throws IOException {
        Map data = new HashMap<>();

        String originalExtension = "";
        String arrSplit[] = file.getOriginalFilename().split("\\.");
        originalExtension = arrSplit[arrSplit.length - 1];
        String namafile = httpSession.getAttribute("username").toString();

        Person person = new Person();
        person.setNama(nama);
        person.setDivisi(divisi);
        person.setEmail("mailto:" + email);
        person.setNo_wa("http://wa.me/+62" + nowa);
        person.setUsername(httpSession.getAttribute("username").toString());
        person.setNamafile(namafile + "." + originalExtension);
        personRepository.save(person);

        try {
            file.transferTo(new File(env.getProperty("URL.FILE_IN") + "/" + namafile + "." + originalExtension));
            Image img = new Image();
            img.setId_person(person.getId_person());
            img.setFileName(namafile);
            imageRepository.save(img);
          } catch (IOException e) {
              System.out.println(e.getMessage());
          }

    data.put("icon", "success");
    data.put("message", "Sukses Insert Data");
    return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/findusername")
    public ResponseEntity<Map> findusername(@RequestParam(required = true) String username) {
        Map data = new HashMap<>();
        Person person = personRepository.findbyUsername(username).get();
        data.put("data", person);
        return new ResponseEntity<>(data, HttpStatus.OK);
      }

    ////////////////////////////////////////////     Menampilkan atau Stream Image     ////////////////////////////////////////////
    @GetMapping(value = "/streamImage")
    public StreamingResponseBody handleRequest (@RequestParam String username, HttpServletResponse response) {
    response.setContentType("image/jpeg");
        return new StreamingResponseBody() {
            public void writeTo (OutputStream out) throws IOException {
                File Image = new File(env.getProperty("URL.FILE_PRIEVIEW") + "/" + username);
                try {
                    byte[] fileContent = Files.readAllBytes(Image.toPath());
                    out.write(fileContent);
                } catch (IOException image) {
                    System.out.println(image);
                }
            }
        };
    }
}
