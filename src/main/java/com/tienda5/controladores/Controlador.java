package com.tienda5.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class Controlador {

	@GetMapping("")
	public String index() {
		return "index";
	}
}
