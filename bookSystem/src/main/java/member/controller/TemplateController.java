package member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import dao.MemberDao;

@Controller
public class TemplateController {
	private final String COMMAND = "";
	
	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public TemplateController() {
		this.meBean = new MemberBean();
		this.mav =  new ModelAndView();
		this.meDao = new MemberDao();
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
