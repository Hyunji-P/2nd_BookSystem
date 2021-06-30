package book.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import dao.BookDao;

@Controller
public class BookDetalViewController {
	private final String COMMAND = "/bkDetailView.bk";
	
	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;
	
	private BookBean bkBean;
	
	private ModelAndView mav;
	
	public BookDetalViewController() {
		this.bkBean = new BookBean();
		this.bkDao =  new BookDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public String doGet(
			@RequestParam(value = "bkSeq", required = true) int bkSeq,
			HttpServletRequest request
			) {
		
		this.bkBean = this.bkDao.selectOneData(bkSeq);
		
		if (bkBean != null) {
			// 해당 데이터가 존재할 경우
			request.setAttribute("bean", bkBean);
		
		}
		return "bkDetailView";
	}
	
	
}
