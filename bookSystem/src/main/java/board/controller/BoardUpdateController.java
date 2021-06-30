package board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BoardBean;
import dao.BoardDao;

@Controller
public class BoardUpdateController {
	private final String COMMAND = "/boUpdate.bo";
	
	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;
	
	private BoardBean boBean;
	
	private ModelAndView mav;
	
	public BoardUpdateController() {
		this.boBean = new BoardBean();
		this.boDao =  new BoardDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "boSeq", required = true) int boSeq,
			HttpServletRequest request) {
		
		this.boBean = this.boDao.selectOneData(boSeq);
		
		mav.addObject("boBean", boBean);
		mav.setViewName("boUpdate");
		
		return mav;
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			BoardBean boBean,
			HttpServletRequest request
			) {
		
		int cnt = -1; 
		
		cnt = this.boDao.updateData(boBean);
		
		if (cnt > 0) {
			mav.setViewName("redirect:/boDetailView.bo?boSeq=" + boBean.getBoSeq());
		}else {
			mav.setViewName("boUpdate");
		}
		
		return mav;
	}
	
}
