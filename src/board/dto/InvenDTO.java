package board.dto;

/**
 * 재고관리 DTO
 * @author main
 *
 */
public class InvenDTO {
	private String p_num;		//부품번호
	private String p_name;		//부품명
	private int p_inven;		//협력사 부품갯수
	private String sup_idNum;	//협력사 고유번호
	private String lotNum;		//LOT넘버(납품 고유번호)
	private String del_date;	//납품일
	private int del_vol;		//납품 수량
	private int order_vol;		//주문 수량
	private String order_date;	//주문 일자
	private String memo;		//주문 요청사항
	private String complete;	//납품 완료 여부
	private int orderNum;		//주문번호
	private String cus_idNum;	//고객사 고유번호
	private int cus_inven;		//고객사 부품 갯수추가
	private String p_mate;		//부품 재질
	private String p_mPrice;	//부품 생산 가격
	private String p_sPrice;	//부품 판매 가격
	private String manuNum;		//고객사 생산량
	private String goods_name;	//고객사 제품명
	private int goods_inven;	//고객사 재고

	
	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public int getGoods_inven() {
		return goods_inven;
	}

	public void setGoods_inven(int goods_inven) {
		this.goods_inven = goods_inven;
	}

	public String getManuNum() {
		return manuNum;
	}

	public void setManuNum(String manuNum) {
		this.manuNum = manuNum;
	}

	public InvenDTO() {
	}//기본생성자

	public String getP_num() {
		return p_num;
	}

	public void setP_num(String p_num) {
		this.p_num = p_num;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public int getP_inven() {
		return p_inven;
	}

	public void setP_inven(int p_inven) {
		this.p_inven = p_inven;
	}

	public String getSup_idNum() {
		return sup_idNum;
	}

	public void setSup_idNum(String sup_idNum) {
		this.sup_idNum = sup_idNum;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}

	public String getDel_date() {
		return del_date;
	}

	public void setDel_date(String del_date) {
		this.del_date = del_date;
	}

	public int getDel_vol() {
		return del_vol;
	}

	public void setDel_vol(int del_vol) {
		this.del_vol = del_vol;
	}

	public int getOrder_vol() {
		return order_vol;
	}

	public void setOrder_vol(int order_vol) {
		this.order_vol = order_vol;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getCus_idNum() {
		return cus_idNum;
	}

	public void setCus_idNum(String cus_idNum) {
		this.cus_idNum = cus_idNum;
	}

	public int getCus_inven() {
		return cus_inven;
	}

	public void setCus_inven(int cus_inven) {
		this.cus_inven = cus_inven;
	}

	public String getP_mate() {
		return p_mate;
	}

	public void setP_mate(String p_mate) {
		this.p_mate = p_mate;
	}

	public String getP_mPrice() {
		return p_mPrice;
	}

	public void setP_mPrice(String p_mPrice) {
		this.p_mPrice = p_mPrice;
	}

	public String getP_sPrice() {
		return p_sPrice;
	}

	public void setP_sPrice(String p_sPrice) {
		this.p_sPrice = p_sPrice;
	}

}
