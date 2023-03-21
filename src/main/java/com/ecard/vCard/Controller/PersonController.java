package com.ecard.vCard.Controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Decoder.Text;

import org.apache.poi.ss.formula.functions.Replace;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.ecard.vCard.Entity.Person;
import com.ecard.vCard.Repository.PersonRepository;
import com.ecard.vCard.Util.GenereteCode;

import io.netty.handler.codec.base64.Base64Decoder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

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
    public String Person(@PathVariable(required = true) String username, Model model) {

        com.ecard.vCard.Entity.Person person = personRepository.findbyUsername(username).get();
        model.addAttribute("nama", person.getNama());
        model.addAttribute("email", person.getEmail());
        model.addAttribute("wa", person.getNo_wa());
        model.addAttribute("divisi", person.getDivisi());
        model.addAttribute("username", person.getUsername());
        model.addAttribute("namafile", person.getNamafile());
        model.addAttribute("jabatan", person.getJabatan());
        model.addAttribute("Image", person.getImage());
        return "index";
    }

    @PostMapping(value = "/InputPerson")
    public ResponseEntity<Map> InputPerson(@RequestParam String nama, @RequestParam String divisi, @RequestParam String jabatan,
            @RequestParam String email,
            @RequestParam String nowa, @RequestParam String Image) throws IOException {
        Map data = new HashMap<>();
        String namafile = httpSession.getAttribute("username").toString();
        String gambar = Image.replace(" ", "+");
        Person person = new Person();
        person.setNama(nama);
        person.setDivisi(divisi);
        person.setEmail("mailto:" + email);
        person.setNo_wa("http://wa.me/+62" + nowa);
        person.setUsername(httpSession.getAttribute("username").toString());
        person.setNamafile(namafile + ".jpg");
        person.setJabatan(jabatan);
        person.setImage(gambar);
        personRepository.save(person);
        data.put("icon", "success");
        data.put("message", "data berhasil di insert");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping(value = "/PutPerson")
    public ResponseEntity<Map> PutPerson(@RequestParam (required = true) String username, @RequestParam String nama, @RequestParam String divisi, @RequestParam String jabatan, 
            @RequestParam String email,
            @RequestParam String nowa, @RequestParam String Image) throws IOException {
        Map data = new HashMap<>();
        String namafile = httpSession.getAttribute("username").toString();
        String gambar = Image.replace(" ", "+");
        Person person = personRepository.findbyUsername(username).get();
        person.setNama(nama);
        person.setDivisi(divisi);
        person.setJabatan(jabatan);
        person.setEmail("mailto:" + email);
        person.setNo_wa("http://wa.me/+62" + nowa);
        person.setUsername(httpSession.getAttribute("username").toString());
        person.setNamafile(namafile + ".jpg");
        person.setImage(gambar);
        personRepository.save(person);
        data.put("icon", "success");
        data.put("message", "data berhasil di insert");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/findusername")
    public ResponseEntity<Map> findusername(@RequestParam(required = true) String username) {
        Map data = new HashMap<>();
        Person person = personRepository.findbyUsername(username).get();
        data.put("data", person);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public void qrcode(@RequestParam String link, HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(GenereteCode.getQRCodeImage(link, 500, 500));
    }
}
