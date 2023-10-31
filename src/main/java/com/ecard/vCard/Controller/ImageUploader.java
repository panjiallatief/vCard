package com.ecard.vCard.Controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Controller
public class ImageUploader {

    @Autowired
    private Environment env;
    
    @GetMapping(value = "/imageuploader")
    public String test(Model model) throws SQLException {
        return "upload";
    }

    @RequestMapping(value = "/inputimage", consumes = {
    MediaType.MULTIPART_FORM_DATA_VALUE }, produces = APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map> inputimage(@RequestParam String nama, @RequestPart(value = "files", required = false) MultipartFile files){
        Map data = new HashMap<>();
        String originalExtension;
        String arrSplit[] = files.getOriginalFilename().split("\\.");
        originalExtension = arrSplit[arrSplit.length - 1];
        try {
            files.transferTo(new File(env.getProperty("URL.FILE_UPLOAD") + "/" + nama));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        data.put("icon", "success");
        data.put("message", "data berhasil di insert");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/{filename}")
    public StreamingResponseBody handleRequest (@PathVariable String filename, HttpServletResponse response) {
    response.setContentType("image/jpeg");
        return new StreamingResponseBody() {
            public void writeTo (OutputStream out) throws IOException {
                File Image = new File(env.getProperty("URL.FILE_PREVIEW") + "/" + filename);
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
