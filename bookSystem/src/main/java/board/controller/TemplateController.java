package board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.BoardBean;
import dao.BoardDao;

@Controller
public class TemplateController {
	private final String COMMAND = "";
	
	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;
	
	private BoardBean boBean;
	
	private ModelAndView mav;
	
	public TemplateController() {
		this.boBean = new BoardBean();
		this.boDao =  new BoardDao();
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
