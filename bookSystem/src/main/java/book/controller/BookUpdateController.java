package book.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import dao.BookDao;

@Controller
public class BookUpdateController {
	private final String COMMAND = "/bkUpdate.bk";

	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;

	private BookBean bkBean;

	private ModelAndView mav;

	public BookUpdateController() {
		this.bkBean = new BookBean();
		this.bkDao = new BookDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public String doGet(@RequestParam(value = "bkSeq", required = true) int bkSeq, HttpServletRequest request) {

		// 도서 번호에 해당하는 bean 객체를 구함
		this.bkBean = this.bkDao.selectOneData(bkSeq);

		request.setAttribute("bean", bkBean);

		return "bkUpdate";
	}

	@PostMapping(value = COMMAND)
	public ModelAndView doPost(BookBean bkbean, @RequestParam(value = "oldImg", required = false) String oldImg,
			HttpServletRequest request) {

		System.out.println("기존 업로드된 이미지 : " + oldImg);

		// 파일 업로드 작업
		MultipartFile multi_file = bkbean.getMultiImage(); // 메인 이미지

		// File 경로
		File destination = null; // 메인 이미지 1

		String uploadPath = "D:\\workspace\\bookSystem\\src\\main\\webapp\\upload";

		try {
			// 새로운 이미지가 들어오면 기존 이미지는 삭제해주어야함
			String delImg = uploadPath + File.separator + oldImg; // 삭제할 이미지 경로
			new File(delImg).delete();

			destination = utility.Utility.getUploadFileInfo(multi_file, uploadPath);
			multi_file.transferTo(destination);

			// 새로운 이미지명을 셋팅
			bkbean.setBkImage(destination.getName());

			int cnt = -1;
			cnt = this.bkDao.updateData(bkbean);

			if (cnt > 0) {
				System.out.println("도서 수정 성공");
				mav.setViewName("redirect:/main.co");
			} else {
				System.out.println("도서 수정 실패");
				mav.addObject("bean", bkbean);
				mav.setViewName("bkUpdate");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

}
