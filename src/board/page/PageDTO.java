package board.page;

public class PageDTO {
	
	private String pageNum="1";
	private String searchComp="";
	private String searchProduct="";
	private int listCount=5;
	private int pagePerBlock=5;
	private int totalCount;
	
	public PageDTO() {
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getSearchComp() {
		return searchComp;
	}

	public void setSearchComp(String searchComp) {
		this.searchComp = searchComp;
	}

	public String getSearchProduct() {
		return searchProduct;
	}

	public void setSearchProduct(String searchProduct) {
		this.searchProduct = searchProduct;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	

	

}
