package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BoardBean;
import bean.MemberBean;
import dao.BoardDao;
import dao.MemberDao;
import utility.FlowParameters;

@Controller
public class FromOriginToReplyOneController {
	private final String COMMAND = "/boFromOriToRepl.bo";
	
	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;
	
	private BoardBean boBean;
	

	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public FromOriginToReplyOneController() {
		this.boBean = new BoardBean();
		this.boDao =  new BoardDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "boOriginSeq", required = true) int boOriginSeq,
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword,
			HttpServletRequest request) {
		
		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);
		
		this.boBean = this.boDao.selectOneData(boOriginSeq); // 게시글 정보 
		
		System.out.println(boBean.getBoOriginSeq());
		
		mav.addObject("parameters", param.toString()); // 페이징 파라미터 정보 바인딩
		mav.addObject("boBean", boBean); // 게시글 정보 바인딩
		mav.setViewName("boFromOriToRepl");
		return mav;
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			BoardBean boBean,
			HttpServletRequest request) {
		
		// 회원 정보 
		HttpSession session = request.getSession();
		
		this.meBean = (MemberBean) session.getAttribute("loginfo");
		
		boBean.setBoEmail(meBean.getMeEmail()); // 이메일 셋팅
		boBean.setWriter(meBean.getMeName()); // 작성자 셋팅 
		
		
		// 먼저 기존 원글에 대한 답글이 있는지 확인 
		int cnt = -1;
		cnt = this.boDao.selectReplyCount(boBean.getBoOriginSeq());
		
		
		
		if (cnt > 0) { // 이미 원글에 답변이 달린 경우 
			cnt = -1;
			cnt = this.boDao.insertFromOriginToReply(boBean); // 글 등록 
			
			BoardBean currBoard = this.boDao.selectCurrData(boBean.getBoOriginSeq()); // 현재 등록된 게시글 데이터 가져오기
			//System.out.println("방금 전 insert된 데이터 : " + currBoard.toString());
			
			// 게시글 순서의 두번째 최댓값 구하기 = 현재 등록된 데이터의 이전 데이터를 의미 
			BoardBean prevBoard = this.boDao.selectPrevData(boBean.getBoOriginSeq());  
			//System.out.println("이전 insert된 데이터 : " + prevBoard.toString());
			
			int newBoGroupOrd = prevBoard.getBoGroupOrd() + 1; // 이전 게시물의 그룹 순서 + 1
			currBoard.setBoGroupOrd(newBoGroupOrd);
			
			cnt = -1;
			cnt = this.boDao.updateFromOriginToReply(currBoard); // 원글의 그룹 번호를 수정 
		}else {
			cnt = -1;
			cnt = this.boDao.insertFromOriginToReply(boBean);
		}
		
		if (cnt > 0) { // 정상 등록 되었으면 
			mav.setViewName("redirect:/boList.bo");
		}
		return mav;
	}
	
}
