package rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import bean.RentalBean;
import dao.BookDao;
import dao.RentalDao;

@Controller
public class TemplateController {
	private final String COMMAND = "";
	
	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;
	
	private RentalBean reBean;
	
	private ModelAndView mav;
	
	public TemplateController() {
		this.reBean = new RentalBean();
		this.reDao =  new RentalDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public String doGet() {
		return "";
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost() {
		
		return mav;
	}
	
}
