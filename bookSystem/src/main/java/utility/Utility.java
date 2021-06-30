package utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class Utility {

	// 2021-06-14_박현지
	// 사진의 이미지 이름을 변경하여 저장한다. 
	// ex) apple.jpg → apple202106141011.jpg 
	// apple + [년] + [월] + [일] + [시] + [분] + . + 확장자 
	public static File getUploadFileInfo(MultipartFile multi_file, String uploadPath) {
		
		String pattern = "yyyyMMddhhmm"; // 년월일시분
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String now = sdf.format(new Date()); //오늘 날짜 
		
		String someFile = multi_file.getOriginalFilename(); // 원래 파일의 이름 
		System.out.println("원래 파일명 : " + someFile);
		
		// 파일 이름 변경
		int dot = someFile.indexOf("."); // . 위치 
		
		String fileName = someFile.substring(0,dot); // 파일 원래 이름
		String fileExt = someFile.substring(dot); // dot + 확장자 
		String newFileName = fileName + now + fileExt; // 변경된 파일 이름 
		
		// 실제 업로드 되는 위치 
		
		String realUpload = uploadPath + File.separator + newFileName;
		
		
		return new File(realUpload);
	}

}
