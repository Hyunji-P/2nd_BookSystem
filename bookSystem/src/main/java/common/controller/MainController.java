package common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	private final String COMMAND = "/main.co";
	
	public MainController() {}
	
	@GetMapping(value = COMMAND)
	public String doGet() {
		return "main";
	}
	
}
