package book.controller;

import java.util.List;

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
import utility.FlowParameters;
import utility.Paging;

@Controller
public class BookListAllController {
	private final String COMMAND = "/bkList.bk";
	
	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;
	
	private BookBean bkBean;
	
	private ModelAndView mav;
	
	public BookListAllController() {
		this.bkBean = new BookBean();
		this.bkDao =  new BookDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "pageNumber" , required = false) String pageNumber,
			@RequestParam(value = "pageSize" , required = false) String pageSize,
			@RequestParam(value = "mode" , required = false) String mode,
			@RequestParam(value = "keyword" , required = false) String keyword,
			HttpServletRequest request
			) {
		
		// 페이징 처리 
		FlowParameters parameters = new FlowParameters(pageNumber, pageSize, mode, keyword);
		
		// 총 데이터 갯수 
		int totalCount = this.bkDao.selectTotalCount(
				parameters.getMode(),"%" +  parameters.getKeyword() + "%");
		
		String url = this.COMMAND;
		
		Paging pageInfo = new Paging(pageNumber, pageSize, totalCount, url, mode, keyword);
		
		List<BookBean> lists = this.bkDao.selectAllData(
				pageInfo.getOffset(),
				pageInfo.getLimit(),
				mode,
				"%" + keyword + "%"
				);
		
		if (lists != null) { // 목록에 잘 담겼으면 목록 페이지로 이동
			// 목록 갯수
			mav.addObject("totalCount", totalCount);
			
			// 목록
			mav.addObject("lists", lists);
			
			// 페이징 관련 
			mav.addObject("pagingHtml", pageInfo.getPagingHtml());
			
			// 필드 검색과 관련 항목
			mav.addObject("mode", parameters.getMode());
			mav.addObject("keyword", parameters.getKeyword());
			
			// 파라미터 리스트 문자열 : 상세보기, 수정 , 삭제 등에 사용
			mav.addObject("parameters", parameters.toString());
			
			mav.setViewName("bkListAll");
		}
		
		return mav;
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost() {
		
		return mav;
	}
	
}
