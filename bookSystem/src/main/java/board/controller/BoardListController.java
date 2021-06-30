package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import utility.Paging;

@Controller
public class BoardListController {
	private final String COMMAND = "/boList.bo";
	
	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;
	
	private BoardBean boBean;
	
	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	
	private ModelAndView mav;
	
	public BoardListController() {
		this.boBean = new BoardBean();
		this.boDao =  new BoardDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword,
			HttpServletRequest request) {
		
		// 페이징 처리 
		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);
		
		int totalCount = this.boDao.selectTotalCount(
				param.getMode(),
				"%" + param.getKeyword() + "%");
		
		String url = COMMAND;
		
		Paging pageInfo = new Paging(
				 param.getPageNumber(), 
				 param.getPageSize(), 
				 totalCount, 
				 url, 
				 param.getMode(), 
				 param.getKeyword());
		
		List<BoardBean> lists = this.boDao.selectAllData(
				pageInfo.getOffset(),
				pageInfo.getLimit(),
				pageInfo.getMode(),
				"%" + pageInfo.getKeyword() + "%");
		
		for (BoardBean item : lists) {
			MemberBean meBean = this.meDao.selectById(item.getBoEmail());
			
			item.setWriter(meBean.getMeName());
		}
		
		if (lists != null) { // 목록이 잘 담겼으면 목록 페이지로 이동
			// 목록 갯수
			mav.addObject("totalCount", totalCount);

			// 목록
			mav.addObject("lists", lists);

			// 페이징 관력 항목들
			mav.addObject("pagingHtml", pageInfo.getPagingHtml());

			// 필드 검색과 관련 항목들
			mav.addObject("mode", param.getMode());
			mav.addObject("keyword", param.getKeyword());

			// 파라미터 리스트 문자열 : 상세보기 , 수정 , 삭제 등에 사용됨
			mav.addObject("parameters", param.toString());
			
			mav.setViewName("boList");
		}
		
		return mav;
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost() {
		
		return mav;
	}
	
}
