package br.edu.utfpr.projetofinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("catalog")
public class CatalogController {

    @GetMapping
    public String catalog() {
        return "catalog/index";
    }


}
