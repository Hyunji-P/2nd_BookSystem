package bean;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookBean {
	private int bkSeq;
	private String bkEmail;
	private String bkTitle;
	private String bkAuthor;
	private String bkPublisher;
	private String bkPublDate;
	private String bkContent;
	
	// 이미지
	private String bkImage;
	private MultipartFile multiImage;
	
	public MultipartFile getMultiImage() {
		return multiImage;
	}

	public void setMultiImage(MultipartFile multiImage) {
		this.multiImage = multiImage;
		
		// 업로드가 잘 되었다면 null이 아님
		if (multiImage != null) {
			this.bkImage = this.multiImage.getOriginalFilename();
		}
	}
	
	
}
