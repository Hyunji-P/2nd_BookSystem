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
import utility.FlowParameters;

@Controller
public class BoardDetailViewController {
	private final String COMMAND = "/boDetailView.bo";

	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;

	private BoardBean boBean;

	private ModelAndView mav;

	public BoardDetailViewController() {
		this.boBean = new BoardBean();
		this.boDao = new BoardDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "boSeq", required = true) int boSeq,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword, HttpServletRequest request) {

		FlowParameters parameters = new FlowParameters(pageNumber, pageSize, mode, keyword);

		this.boBean = this.boDao.selectOneData(boSeq);
		
		if (boBean.getBoHits() == 0) { // 기존 조회수가 0이면 1 대입 
			boBean.setBoHits(1);
		}else { // 기존 조회수가0 이상이면 1 누적 
			boBean.setBoHits(boBean.getBoHits() + 1);
		}
		
		// 조회수 업데이트
		int cnt = -1;
		cnt = this.boDao.updateHit(boBean.getBoHits(), boSeq);
		
		if (cnt > 0) { // 업데이트 성공
			// 다시 셋팅된 정보를 가져옴 
			this.boBean = null;
			this.boBean = this.boDao.selectOneData(boSeq);
						
			mav.addObject("boBean", boBean);
			mav.addObject("parameters" , parameters.toString());
			mav.setViewName("boDetailView");
		}else { // 업데이트 실패
			System.out.println("조회수 업데이트 실패");
		}
		

		return mav;
	}

	@PostMapping(value = COMMAND)
	public ModelAndView doPost() {

		return mav;
	}

}
