package book.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import bean.BookMonth;
import bean.ResultBookMonth;
import dao.BookDao;
import dao.RentalDao;
import utility.FlowParameters;
import utility.Paging;

@Controller
public class BookMonthStatController {
	private final String COMMAND = "/bkMonthStat.bk";

	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;

	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;

	private BookBean bkBean;

	private ModelAndView mav;

	public BookMonthStatController() {
		this.bkBean = new BookBean();
		this.bkDao = new BookDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword, HttpServletRequest request) {

		// 페이징 처리
		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);

		List<BookMonth> totalList = this.reDao.bookMonthCount(param.getMode(), "%" + param.getKeyword() + "%");

		int totalCount = totalList.size(); // 게시글 건수

		String url = COMMAND;

		Paging pageInfo = new Paging(param.getPageNumber(), param.getPageSize(), totalCount, url, param.getMode(),
				"%" + param.getKeyword() + "%");

		// 화면에 보여줄 리스트 제목
		List<String> header = new ArrayList<String>();

		header.add("도서 번호");

		for (int i = 1; i <= 12; i++) {
			header.add(i + "월");
		}

		mav.addObject("header", header);

		// 화면에 보여줄 내용물
		Map<Integer, Integer> bookMap = new HashMap<Integer, Integer>();

		// 0으로 초기화
		for (int i = 1; i <= 12; i++) {
			bookMap.put(i, 0);
		}

		List<BookMonth> bookSqlList = this.reDao.selectBookMonth(pageInfo.getOffset(), pageInfo.getLimit(),
				pageInfo.getMode(), pageInfo.getKeyword());


		// 도서번호 + 1월 ~ 12월
		List<ResultBookMonth> resultList = new ArrayList<ResultBookMonth>();
		ResultBookMonth result = null;

		int imsi = 0; // 도서번호를 저장할 임시 변수

		for (int i = 0; i < bookSqlList.size(); i++) {
			BookMonth item = bookSqlList.get(i);

			if (i == 0) { // 처음 들어오면
				imsi = item.getBookNumber();

				result = new ResultBookMonth(); // 객체 생성
				result.setBookNumber(imsi); // 도서번호 set

				result.setBookMap(new HashMap<Integer, Integer>(bookMap)); // bookMap 객체 생성 및 0으로 초기화
				result.getBookMap().put(item.getMonth(), result.getBookMap().get(item.getMonth()) + item.getCount());
				result.setTotal(item.getCount()); // 토탈 건수 set

			} else { // 그 이후 들어온 거라면 도서번호를 비교하여 건수 누적
				if (imsi != item.getBookNumber()) { // 도서번호가 다르다면 새로운 도서번호 set
					imsi = item.getBookNumber();
					resultList.add(result); // 기존 작업물 넣기

					result = new ResultBookMonth(); // 객체 생성

					result.setBookNumber(imsi); // 도서번호 set
					result.setBookMap(new HashMap<Integer, Integer>(bookMap)); // bookMap 객체 생성 및 0으로 초기화
					result.getBookMap().put(item.getMonth(),
							result.getBookMap().get(item.getMonth()) + item.getCount());
					result.setTotal(item.getCount()); // 토탈 건수 set

				} else { // 도서번호가 같다면 기존 건수에 누적
					result.getBookMap().put(item.getMonth(),
							result.getBookMap().get(item.getMonth()) + item.getCount());

					result.setTotal(result.getTotal() + item.getCount());

				}
				
			}
			if (i == bookSqlList.size() - 1) {// 단 한개의 행만 존재
				resultList.add(result);
			}
		}

		System.out.println("결과물 확인 ==> " + resultList.toString());
		// 목록 갯수
		mav.addObject("totalCount", totalCount);

		// 목록

		// 페이징 관련
		mav.addObject("pagingHtml", pageInfo.getPagingHtml());

		// 필드 검색과 관련 항목
		mav.addObject("mode", param.getMode());
		mav.addObject("keyword", param.getKeyword());

		// 파라미터 리스트 문자열 : 상세보기, 수정 , 삭제 등에 사용
		mav.addObject("parameters", param.toString());

		mav.addObject("resultList", resultList);
		mav.setViewName("bkMonth");
		return mav;
	}

}
