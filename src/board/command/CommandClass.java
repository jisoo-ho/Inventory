package board.command;

/**
 * 주문 하기 전용 클래스
 * @author USER
 *
 */
public class CommandClass {
	private String p_num;		//부품번호
	private int volume;			//납품 수량
	private String memo;		//주문 요청사항
	private String cus_idNum;	//고객사 고유번호
	private String sup_idNum;	//협력사 고유번호
	
	public String getP_num() {
		return p_num;
	}
	public void setP_num(String p_num) {
		this.p_num = p_num;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCus_idNum() {
		return cus_idNum;
	}
	public void setCus_idNum(String cus_idNum) {
		this.cus_idNum = cus_idNum;
	}
	public String getSup_idNum() {
		return sup_idNum;
	}
	public void setSup_idNum(String sup_idNum) {
		this.sup_idNum = sup_idNum;
	}
	public CommandClass() {
		// TODO Auto-generated constructor stub
	}

}
