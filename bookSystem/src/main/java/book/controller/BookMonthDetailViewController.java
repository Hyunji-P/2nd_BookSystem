package book.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import bean.StatisticsDetail;
import dao.BookDao;
import dao.RentalDao;
import utility.FlowParameters;
import utility.Paging;

@Controller
public class BookMonthDetailViewController {
	private final String COMMAND = "/bkMonthDetail.bk";

	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;

	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;

	private BookBean bkBean;

	private ModelAndView mav;

	public BookMonthDetailViewController() {
		this.bkBean = new BookBean();
		this.bkDao = new BookDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "month", required = true) int month,
			@RequestParam(value = "bookNumber", required = true) int bookNumber, 
			HttpServletRequest request) {

		// 페이징 처리
		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);

		int totalCount = this.bkDao.bookDetailCount(param.getMode(), "%" + param.getKeyword() + "%", bookNumber, month);

		String url = this.COMMAND;

		Paging pageInfo = new Paging(param.getPageNumber(), param.getPageSize(), totalCount, url, param.getMode(),
				param.getKeyword());

		List<StatisticsDetail> detailList = this.bkDao.selectDetailList(month, bookNumber, pageInfo.getOffset(),
				pageInfo.getLimit(), pageInfo.getMode(), "%" + pageInfo.getKeyword() + "%");

		if (detailList != null) {
			// 목록 갯수
			mav.addObject("totalCount", totalCount);

			// 목록
			mav.addObject("detailList", detailList);

			// 페이징 관력 항목들
			mav.addObject("pagingHtml", pageInfo.getPagingHtml());

			// 필드 검색과 관련 항목들
			mav.addObject("mode", param.getMode());
			mav.addObject("keyword", param.getKeyword());

			// 파라미터 리스트 문자열 : 상세보기 , 수정 , 삭제 등에 사용됨
			mav.addObject("parameters", param.toString());
			
			// 필수 파라미터
			mav.addObject("month", month);
			mav.addObject("bookNumber", bookNumber);

			mav.setViewName("bookStatDetail");
		}

		return mav;
	}

}
