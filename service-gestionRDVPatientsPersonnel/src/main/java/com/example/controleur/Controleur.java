package com.example.controleur;

import com.example.facade.FacadeApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rdvpatients",produces = {MediaType.APPLICATION_JSON_VALUE})
public class Controleur {
    @Autowired
    FacadeApplication facadeApplication;



}
