package com.groupe.projet_android_AL.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/api/listings")
@CrossOrigin(origins = "*")
public class PropertyListingsController {

}
