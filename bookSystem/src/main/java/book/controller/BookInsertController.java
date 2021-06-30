package book.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

// 2021-06-11_박현지
// 도서 등록 컨트롤러

@Controller
public class BookInsertController {
	private final String COMMAND = "/bkInsert.bk";
	
	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;
	
	private BookBean bkBean;
	
	private ModelAndView mav;
	
	public BookInsertController() {
		this.bkBean = new BookBean();
		this.bkDao =  new BookDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public String doGet() {
		return "bkInsert";
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			BookBean bkbean,
			HttpServletRequest request) {
		
		// 파일 업로드 작업
		MultipartFile multi_file = bkbean.getMultiImage(); // 메인 이미지
		
		// File 경로
		File destination = null; // 메인 이미지 1
		
		// 파일이 저장되는 폴더
		String uploadPath = "D:\\workspace\\bookSystem\\src\\main\\webapp\\upload"; 
		
		try {
			destination = utility.Utility.getUploadFileInfo(multi_file, uploadPath);
			
			multi_file.transferTo(destination);
			
			// 원래 이미지명에 새로운 이름 셋팅 
			bkbean.setBkImage(destination.getName());
			
			
			int cnt = -1;
			cnt = this.bkDao.insertData(bkbean);
			
			if (cnt > 0) {
				System.out.println("도서 등록 성공");
				mav.setViewName("redirect:/main.co");
			}else {
				System.out.println("도서 등록 실패");
				mav.addObject("bean", bkbean);
				mav.setViewName("bkInsert");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav; 
	}
	
}
