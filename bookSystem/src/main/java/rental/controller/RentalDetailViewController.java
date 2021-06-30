package rental.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import bean.RentalBean;
import dao.BookDao;
import dao.RentalDao;

@Controller
public class RentalDetailViewController {
	private final String COMMAND = "/reDetailView.re";
	
	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;
	
	private RentalBean reBean;
	
	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;
	
	private BookBean bkBean;
	
	private ModelAndView mav;
	
	public RentalDetailViewController() {
		this.reBean = new RentalBean();
		this.reDao =  new RentalDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public String doGet(
			@RequestParam(value = "reSeq", required = true) int reSeq,
			HttpServletRequest request) {
		
		this.reBean = this.reDao.selectOneData(reSeq); // 대여 정보
		this.bkBean = this.bkDao.selectOneData(reBean.getReBookNumber()); // 책 정보
		
		request.setAttribute("reBean", reBean);
		request.setAttribute("bkBean", bkBean);
		
		return "reDetailView";
	}

	
}
