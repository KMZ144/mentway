package com.global.mentorship.security.controller;

import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/images")
public class FileController {

    //root path for image files
	@Value("${systemPath}")
    private String FILE_PATH_ROOT;

    @GetMapping("/{filename}")
    public ResponseEntity <byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils
          .readFileToByteArray(new File(FILE_PATH_ROOT+File.separator+filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
    }

}
