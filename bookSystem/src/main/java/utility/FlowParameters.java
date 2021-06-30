package utility;

public class FlowParameters {
	// 페이징 처리 관련 변수들
	private String pageNumber = "1" ; // 현재 페이지 번호
	private String pageSize = "10"; // 한 페이지에 보여줄 게시글 건수
	
	// 필드 검색과 관련된 변수들
	private String mode = "" ; // 검색 조건
	private String keyword = "" ; // 검색 키워드
	
	public FlowParameters() { }
	
	public FlowParameters(String pageNumber, String pageSize, String mode, String keyword) {
		this.pageNumber = pageNumber ;
		this.pageSize = pageSize ;
		if ( mode == null || mode.equals("null") || mode.equals("") ) {
			mode = "all" ;
		}
		this.mode = mode ;
		if ( keyword == null || keyword.equals("null") || keyword.equals("")  || keyword.equals("all") ) {
			keyword = "" ;
		}
		this.keyword = keyword ;
	}
	
	 
	@Override
	public String toString() {
		return "pageNumber=" + pageNumber + "&pageSize="
				+ pageSize + "&mode=" + mode + "&keyword=" + keyword + "";
	}
	
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	} 
	
}