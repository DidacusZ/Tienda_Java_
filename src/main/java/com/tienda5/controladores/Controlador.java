package com.tienda5.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class Controlador {

	@GetMapping("/demo")
	public String showDemo() {
		return "hola";
	}
}
