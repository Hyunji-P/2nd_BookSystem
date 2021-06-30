package book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import dao.BookDao;

@Controller
public class TemplateController {
	private final String COMMAND = "";
	
	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;
	
	private BookBean bkBean;
	
	private ModelAndView mav;
	
	public TemplateController() {
		this.bkBean = new BookBean();
		this.bkDao =  new BookDao();
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
