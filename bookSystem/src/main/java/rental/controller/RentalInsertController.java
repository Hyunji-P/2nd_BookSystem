package rental.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import bean.RentalBean;
import dao.RentalDao;

@Controller
public class RentalInsertController {
	private final String COMMAND = "/reInsert.re";

	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;

	private RentalBean reBean;

	private ModelAndView mav;

	
	
	String errMsg = ""; // 에러메세지

	public RentalInsertController() {
		this.reBean = new RentalBean();
		this.reDao = new RentalDao();
		this.mav = new ModelAndView();

	}

	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			@RequestParam(value = "reBookNumber", required = true) int reBookNumber,
			@RequestParam(value = "reStartDate", required = true) String reStartDate, 
			@RequestParam(value = "reEndDate", required = true) String reEndDate, 
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberBean meBean = (MemberBean) session.getAttribute("loginfo"); // 회원 정보를 가져옴

		// 대여 조건
		if (meBean == null) { // 비회원일 경우
			session.setAttribute("err_message", "비회원일 경우 로그인을 먼저 진행해주세요.");
			mav.setViewName("redirect:/meLogin.me");
		} else { // 비회원이 아닐 경우, 회원이거나 관리자
			if (meBean.getMeDivision().equals("관리자")) { // 관리자 일 경우 대여 불가능
				
				session.setAttribute("err_message", "관리자는 도서 대여가 불가합니다.");
				mav.setViewName("redirect:/bkList.bk");
			} else { // 회원 일 경우 대여 가능
				// 유효성 체크
				if (this.isCheck(reStartDate,reEndDate) == true) { // 대여 가능
					reStartDate = reStartDate.replace("-", ""); // 대여 날짜에서 하이픈 제거
					reEndDate = reEndDate.replace("-", ""); // 대여 날짜에서 하이픈 제거
					
					this.reBean.setReEndDate(reEndDate);
					this.reBean.setReBookNumber(reBookNumber);
					this.reBean.setReEmail(meBean.getMeEmail());
					this.reBean.setReStartDate(reStartDate);

					// 대여 테이블을 조회하여 신규 대여 일자의 중복을 체크
					if (this.duplDate(reBean.getReStartDate(), reBean.getReEndDate(), reBean.getReBookNumber()) == false) { // 대여불가
						errMsg = "중복된 대여일자가 존재합니다. 다른 날짜를 선택해주세요.";
						session.setAttribute("err_message", errMsg);
						mav.setViewName("redirect:/bkDetailView.bk?bkSeq=" + reBookNumber);
					}else { // 대여가능
						int cnt = -1;
						cnt = this.reDao.insertData(reBean);
						mav.setViewName("redirect:/reList.re?reEmail=" + this.reBean.getReEmail());
					}
				} else {
					session.setAttribute("err_message", errMsg);
					mav.setViewName("redirect:/bkDetailView.bk?bkSeq=" + reBookNumber);
				}
			}

		}

		return mav;
	}


	// 2021-06-15_박현지
	// 회원이 선택한 대여 일자의 유효성 체크
	private boolean isCheck(String reStartDate, String reEndDate) {
		boolean dateCheck = false;
		String pattern = "YYYYMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String today = sdf.format(new Date()); // 오늘 날짜
		
		reStartDate = reStartDate.replace("-", ""); // 대여 날짜에서 하이픈 제거
		reEndDate = reEndDate.replace("-", ""); // 대여 날짜에서 하이픈 제거

		System.out.println("오늘일자 : " + today);
		System.out.println("시작일자 : " + reStartDate);
		System.out.println("종료일자 : " + reEndDate);
		
		if (Integer.parseInt(today) <= Integer.parseInt(reStartDate)) { // 오늘 날짜보다 시작일자가 커야함
			if (Integer.parseInt(reEndDate) - Integer.parseInt(reStartDate) > 0) { // 대여 종료일 > 대여 시작일
				dateCheck = true;
			} else {
				errMsg = "*대여 종료일을 다시 선택해주세요. *대여 시작일보다 커야합니다.";
				dateCheck = false;
			}
		}else {
			errMsg = "*대여 시작일을 다시 선택해주세요. 오늘 날짜보다 크거나 같아야합니다.";
			dateCheck = false;
		}
		

		return dateCheck;
	}
	
	// 2021-06-15_박현지
	// 대여 테이블을 바라보고 대여일자의 중복을 판단
	// ex) [1][2][3][4][5] (날짜 1~5일 대여 예약이 되어있다고 가정)
	//     start       end
	//     p:end    p:start   
	// 중복 체크 로직 
	// start < p:end and end > p:start -- true면 중복, false면 중복아님 
	// 1 < 6 and 5 > 2 -- true  --- 파라미터 시작일 : 2, 마지막일 : 6으로 가정
	// 1 < 8 and 5 > 6 -- false --- 파라미터 시작일 : 6, 마지막일 : 8으로 가정
	private boolean duplDate(String startDate, String endDate , int bookNumber) {
		boolean duplDateCheck = false;
		
		int cnt = -1;
		cnt = this.reDao.selectDuplDate(startDate, endDate , bookNumber);
		
		if (cnt > 0) { // cnt가 1이면 중복 구간
			System.out.println("중복 구간");
			duplDateCheck = false;
		}else {
			System.out.println("중복 구간 아님");
			duplDateCheck = true;
		}
		
		return duplDateCheck;
	}

}
