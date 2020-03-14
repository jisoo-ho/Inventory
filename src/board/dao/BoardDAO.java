package board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import board.command.CommandClass;
import board.dto.InvenDTO;
import board.page.PageDTO;

/**
 * 데이터 베이스 처리 클래스
 * @author ojjang
 * @since 20200224
 */
public class BoardDAO {
	
	//context.xml 에서 받아온 내용
	DataSource ds; 

	// 생성자 : DataSource 얻기 
	public BoardDAO() {
		try {
			
			Context ctx = new InitialContext();
			
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/orcl");
			System.out.println("BoardDAO ds생성 완료 : " + ds);

		} catch (Exception e) {
			System.out.println("BoardDAO() ds 생성 ERR : "+e.getMessage());
		}
	} //생성자
	
	
	/**
	 * 게시판 총 갯수 조회
	 * @param page
	 * @return
	 */
	public int selectCount(PageDTO page,String tableName){
		int totalCount=0;	//전체 목록 또는 검색어를 통한 조회 시 총 레코드 갯수 저장
		String searchComp=page.getSearchComp();
		String searchProduct=page.getSearchProduct();
		String whereSQL="";	//select 쿼리의 조건 부분만 저장
		
		try{
			//검색어 쿼리문 둘중 하나만이라도 입력값있으면...
			if(!"".equals(searchComp)||!"".equals(searchProduct)){	//검색어가 있을 경우에만
				whereSQL=" WHERE P_NUM LIKE '%'||?||'%' AND SUP_IDNUM LIKE '%'||?||'%' ";
			}//if(!"".equals(searchProduct)) END
			
			//데이터베이스 객체 생성
			Connection con=ds.getConnection();
			
			//목록의 총 수를 얻는 쿼리 실행
			String query="SELECT COUNT(*) AS TOTAL FROM "+tableName+" "+whereSQL;
			PreparedStatement pstmt = con.prepareStatement(query);
			if(!"".equals(whereSQL)){	//검색어가 있을 경우에만 ? 2개
				pstmt.setString(1, searchComp);
				pstmt.setString(2, searchProduct);
			}//if(!"".equals(whereSQL))END
			
			ResultSet rs=pstmt.executeQuery();	//쿼리 실행
			System.out.println(query);
			
			if(rs.next()){
				totalCount=rs.getInt("TOTAL");		//결과 필드명을 이용
			}
			rs.close();
			pstmt.close();
			con.close();
			
		} catch(SQLException e){
			System.out.println("selectCount() PSTMT ERR : "+e.getMessage());
		} 
		
		return totalCount; //결과 값 반환 
	}//public int selectCount() END
	
	
	/**
	 * 주문내용 등록 메서드(협력사 주문테이블, 고객사 주문테이블)
	 */
	public void insertOrder(CommandClass data){
		try {
			String sql = "INSERT INTO ORDER_CHECK_TABLE VALUES(?, ?, SYSDATE, ?, 'A', SEQ_ONUM.NEXTVAL, ?, ?)";
			System.out.println(sql);
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, data.getP_num());
			stmt.setInt(2, data.getVolume());
			stmt.setString(3, data.getMemo());
			stmt.setString(4, data.getCus_idNum());
			stmt.setString(5, data.getSup_idNum());
			stmt.executeUpdate();
			
			stmt = null;
			
			sql="INSERT INTO ORDER_CHECKSUP_TABLE VALUES(?, ?, SYSDATE, ?, 'A', SEQ_ONUM.CURRVAL, ?, ?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, data.getP_num());
			stmt.setInt(2, data.getVolume());
			stmt.setString(3, data.getMemo());
			stmt.setString(4, data.getCus_idNum());
			stmt.setString(5, data.getSup_idNum());
			stmt.executeUpdate();
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("insertOrder ERR "+e.getMessage());
		}
	}// insertOrder END
	
	/**
	 * 협력사 납품 테이블
	 * SUPPLIER_DELIVERY_TABLE 
	 * SELECT 값만 보내주기
	 */
	public InvenDTO selectOne(String orderNum){
		InvenDTO invenDto = null;
		try {
			String sql = "SELECT P_NUM, ORDER_VOL, ORDER_NUM, CUS_IDNUM, SUP_IDNUM FROM ORDER_CHECKSUP_TABLE WHERE ORDER_NUM=?";
			invenDto = new InvenDTO();
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			int onum = Integer.parseInt(orderNum);
			stmt.setInt(1, onum);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				invenDto.setP_num(rs.getString(1));
				invenDto.setOrder_vol(rs.getInt(2));
				invenDto.setOrderNum(rs.getInt(3));
				invenDto.setCus_idNum(rs.getString(4));
				invenDto.setSup_idNum(rs.getString(5));
			}
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("selectONE ERR "+e.getMessage());
		}
		return invenDto;
	}//selectOne END
	
	/**
	 * 납품 내용 등록 메서드
	 * 납품 내용 등록 (협력사 납품 테이블)
	 * update 협력사 재고 태이블 (재고 -) 
	 * update 협력사 주문 테이블 complete A=>B
	 */
	public void insertDelivery(InvenDTO invenDto){
		try {
			String sql = "INSERT INTO SUPPLIER_DELIVERY_TABLE VALUES(CONCAT(TO_CHAR(SYSDATE,'YYYYMMDDHHMISS'), ?), ?, SYSDATE, ?, ?, ?, ?)";
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, invenDto.getP_num());
			stmt.setString(2, invenDto.getP_num());
			stmt.setInt(3, invenDto.getOrder_vol());
			stmt.setInt(4, invenDto.getOrderNum());
			stmt.setString(5, invenDto.getCus_idNum());
			stmt.setString(6, invenDto.getSup_idNum());
			stmt.executeUpdate();
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("insertDelivery ERR"+e.getMessage());
		}
	}//insertDelivery END
	
	/*
	 * 로트넘버 꺼내오는 메소드
	 */
	public String selectLOTnum(InvenDTO invenDto){
		ResultSet rs=null;
		String lotNum = null;
		try {
			String sql = "SELECT LOTNUM FROM SUPPLIER_DELIVERY_TABLE WHERE ORDER_NUM = ?";
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
		
			stmt.setInt(1, invenDto.getOrderNum());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				lotNum = rs.getString(1);	
			}
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("selectLOTnum ERR"+e.getMessage());
		}
		return lotNum;
	}//selectLOTnum END
	
	/**
	 * 입고 내용 등록 메서드
	 * 입고 내용 등록(고객사 입고 테이블)
	 * update 고객사 재고 테이블 (재고+)
	 * update 고객사 주문 테이블 complete A=>B
	 */
	public void insertCustom(InvenDTO invenDto, String lotNum){
		try {
			String sql = "INSERT INTO CUSTOMER_IN_TABLE VALUES(?, ?, SYSDATE, ?, ?, ?)";
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, invenDto.getP_num());
			stmt.setInt(2, invenDto.getOrder_vol());
			stmt.setString(3, invenDto.getSup_idNum());
			stmt.setString(4, lotNum);
			stmt.setInt(5, invenDto.getOrderNum());
			
			stmt.executeUpdate();
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("insertDelivery ERR"+e.getMessage());
		}		
	}//insertCustom END
	
	/*
	 * 협력사 재고 수정 메소드
	 */
	public void updateSUPinven(InvenDTO invenDto){
		String sql = "UPDATE PRODUCT_TABLE SET P_INVEN=P_INVEN-? WHERE P_NUM = ?";
		try {
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, invenDto.getOrder_vol());
			stmt.setString(2, invenDto.getP_num());
			System.out.println(invenDto.getOrder_vol()+"SUPinven order vol");
			System.out.println(invenDto.getP_num()+"SUPinven pnum");
			
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("updateSUPinven ERR "+e.getMessage());
		}
	}//updateSUPinven END
	
	/*
	 * 고객사 재고 수정 메소드
	 */
	public void updateCUSinven(InvenDTO invenDto){
		String sql = "UPDATE CUSTOMER_INVEN_TABLE SET CUS_INVEN=CUS_INVEN+? WHERE P_NUM = ?";
		try {
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, invenDto.getOrder_vol());
			stmt.setString(2, invenDto.getP_num());
			System.out.println(invenDto.getOrder_vol()+"CUSinven order vol");
			System.out.println(invenDto.getP_num()+"CUSinven pnum");
			
			
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("updateSUPinven ERR "+e.getMessage());
		}
	}//updateSUPinven END
	
	/**
	 * 고객사 재고 보기 메서드
	 */
	public ArrayList<InvenDTO> selectCInven(){
		ArrayList<InvenDTO> invenList = new ArrayList<InvenDTO>();
		try{
			Connection con=ds.getConnection();
			
			String query="SELECT * FROM CUSTOMER_INVEN_TABLE ";
			PreparedStatement pstmt = con.prepareStatement(query);
						
			ResultSet rs=pstmt.executeQuery();	//쿼리 실행
			System.out.println(query);
			
			while (rs.next()){
				InvenDTO invenDTO= new InvenDTO();
				
				invenDTO.setP_num(rs.getString(1));
				invenDTO.setCus_inven(rs.getInt(2));
				
				invenList.add(invenDTO);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch(SQLException e){
			System.out.println("selectCount() PSTMT ERR : "+e.getMessage());
		}
		return invenList;
	}//selectCInven END
	
	
	/**
	 * 협력사 재고 보기 메서드
	 */
	public ArrayList<InvenDTO> selectSInven(){
		
		ArrayList<InvenDTO> invenList = new ArrayList<InvenDTO>();
		
		try{
			
			Connection con=ds.getConnection();
			
			String query="SELECT * FROM PRODUCT_TABLE";
			PreparedStatement pstmt = con.prepareStatement(query);
						
			ResultSet rs=pstmt.executeQuery();	//쿼리 실행
			System.out.println(query);
			
			while (rs.next()){
				InvenDTO invenDTO= new InvenDTO();
				
				invenDTO.setP_num(rs.getString(1));
				invenDTO.setP_name(rs.getString(2));
				invenDTO.setP_mate(rs.getString(3));
				invenDTO.setP_mPrice(rs.getString(4));
				invenDTO.setP_sPrice(rs.getString(5));
				invenDTO.setP_inven(rs.getInt(6));
				
				invenList.add(invenDTO);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch(SQLException e){
			System.out.println("selectCount() PSTMT ERR : "+e.getMessage());
		} 
		return invenList;
	}//selectSInven END
	
	
	/**
	 * 협력사 납품 목록 보기 메서드
	 */
	public ArrayList<InvenDTO> selectSList(PageDTO page){
		int pageNum =1;
		if (page.getPageNum()!=null){
			pageNum=Integer.parseInt(page.getPageNum());
		}
		String searchComp = page.getSearchComp();
		String searchProduct = page.getSearchProduct();
		int listCount=page.getListCount();
		String whereSQL="";		
		
		ArrayList<InvenDTO> boardList = new ArrayList<InvenDTO>();
		
		try{
			//검색어 쿼리문 둘중 하나만이라도 입력값있으면...
			if(!"".equals(searchComp)||!"".equals(searchProduct)){	//검색어가 있을 경우에만
				whereSQL=" WHERE P_NUM LIKE '%'||?||'%' AND SUP_IDNUM LIKE '%'||?||'%' ";
			}//if(!"".equals(searchProduct)) END
			
			//데이터베이스 객체 생성
			Connection con=ds.getConnection();
			
			//목록의 총 수를 얻는 쿼리 실행
			String end=" ORDER BY DEL_DATE DESC ) A WHERE ROWNUM <= ?+? ) WHERE RNUM > ? ";
	        String first="SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT * FROM SUPPLIER_DELIVERY_TABLE ";
	        String query=first+whereSQL+end;
			
	        PreparedStatement pstmt = con.prepareStatement(query);
	        if(!"".equals(whereSQL)){   //검색어가 있을 경우에만 ? 2개
	           pstmt.setString(1, searchComp);
	           pstmt.setString(2, searchProduct);
	           pstmt.setInt(3, listCount*(pageNum-1));
	           pstmt.setInt(4, listCount);
	           pstmt.setInt(5, listCount*(pageNum-1));
	        }else{
	           pstmt.setInt(1, listCount*(pageNum-1));
	           pstmt.setInt(2, listCount);
	           pstmt.setInt(3, listCount*(pageNum-1));
	        }
	        //if(!"".equals(whereSQL))END
			
			ResultSet rs=pstmt.executeQuery();	//쿼리 실행
			System.out.println(query);
			
			while (rs.next()){
				InvenDTO invenDTO= new InvenDTO();
				
				invenDTO.setLotNum(rs.getString(2));
				invenDTO.setP_num(rs.getString(3));
				invenDTO.setDel_date(rs.getString(4));
				invenDTO.setDel_vol(rs.getInt(5));
				invenDTO.setOrderNum(rs.getInt(6));
				invenDTO.setCus_idNum(rs.getString(7));
				invenDTO.setSup_idNum(rs.getString(8));
				
				boardList.add(invenDTO);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch(SQLException e){
			System.out.println("selectCount() PSTMT ERR : "+e.getMessage());
		} 
		return boardList;
	}//selectSList
	
	
	/**
	 * 고객사 입고 목록 보기 메서드
	 */
	public ArrayList<InvenDTO> selectCList(PageDTO page){
		int pageNum =1;
		if (page.getPageNum()!=null){
			pageNum=Integer.parseInt(page.getPageNum());
		}
		String searchComp = page.getSearchComp();
		String searchProduct = page.getSearchProduct();
		int listCount=page.getListCount();
		String whereSQL="";		
		
		ArrayList<InvenDTO> boardList = new ArrayList<InvenDTO>();

		try{
			//검색어 쿼리문 둘중 하나만이라도 입력값있으면...
			if(!"".equals(searchComp)||!"".equals(searchProduct)){	//검색어가 있을 경우에만
				whereSQL=" WHERE P_NUM LIKE '%'||?||'%' AND SUP_IDNUM LIKE '%'||?||'%' ";
			}//if(!"".equals(searchProduct)) END
			
			//데이터베이스 객체 생성
			Connection con=ds.getConnection();
			
			//목록의 총 수를 얻는 쿼리 실행
			String end=" ORDER BY DEL_DATE DESC ) A WHERE ROWNUM <= ?+? ) WHERE RNUM > ? ";
	        String first="SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT * FROM CUSTOMER_IN_TABLE ";
	        String query=first+whereSQL+end;
			
	        PreparedStatement pstmt = con.prepareStatement(query);
	        if(!"".equals(whereSQL)){   //검색어가 있을 경우에만 ? 2개
	           pstmt.setString(1, searchComp);
	           pstmt.setString(2, searchProduct);
	           pstmt.setInt(3, listCount*(pageNum-1));
	           pstmt.setInt(4, listCount);
	           pstmt.setInt(5, listCount*(pageNum-1));
	        }else{
	           pstmt.setInt(1, listCount*(pageNum-1));
	           pstmt.setInt(2, listCount);
	           pstmt.setInt(3, listCount*(pageNum-1));
	        }
	        //if(!"".equals(whereSQL))END
			
			ResultSet rs=pstmt.executeQuery();	//쿼리 실행
			System.out.println(query);
			
			while (rs.next()){
				InvenDTO invenDTO= new InvenDTO();
				
				invenDTO.setP_num(rs.getString(2));
				invenDTO.setDel_vol(rs.getInt(3));
				invenDTO.setDel_date(rs.getString(4));
				invenDTO.setSup_idNum(rs.getString(5));
				invenDTO.setLotNum(rs.getString(6));
				invenDTO.setOrderNum(rs.getInt(7));
				
				boardList.add(invenDTO);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch(SQLException e){
			System.out.println("selectCount() PSTMT ERR : "+e.getMessage());
		} 
		return boardList;
	}//selectCList END
	
	
	/**
	 * 주문 미완 수 확인 (2개 주문 테이블중 아무거나 가능)
	 */
	public int countIncomplete(){
		int A=0;
		try {
			String sql = "select count(*) from order_checksup_table where complete = 'A'";
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				A = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("countOrder ERR "+e.getMessage());
		}
		return A;
	}//countOrder END
	
	/**
	 * 입고/납품 완료처리 메서드(상태변경)
	 */
	public void updateStatus(String orderNum){
		PreparedStatement stmt=null;
		String sql=null;
		try {
			sql = "UPDATE ORDER_CHECKSUP_TABLE SET COMPLETE = 'B' WHERE ORDER_NUM = ?";
			Connection con = ds.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, Integer.parseInt(orderNum));
			stmt.executeUpdate();
			
			stmt=null;
			
			sql = "UPDATE ORDER_CHECK_TABLE SET COMPLETE = 'B' WHERE ORDER_NUM = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(orderNum));
			
			stmt.executeUpdate();
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("updateStatus ERR"+e.getMessage());
		}
	}//UPDATESTATUS END

	
	/**
	 * 협력사 주문목록 리스트 보기 메서드
	 */
	public ArrayList<InvenDTO> selectSOrderList(PageDTO page){
		int pageNum =1;
		if (page.getPageNum()!=null){
			pageNum=Integer.parseInt(page.getPageNum());
		}
		String searchComp = page.getSearchComp();
		String searchProduct = page.getSearchProduct();
		String whereSQL="";		
		int listCount=page.getListCount();
		System.out.println(listCount+" S오더리스트 리스트 카운트");
		ArrayList<InvenDTO> boardList = new ArrayList<InvenDTO>();
		
		try{
			//검색어 쿼리문 둘중 하나만이라도 입력값있으면...
			if(!"".equals(searchComp)||!"".equals(searchProduct)){	//검색어가 있을 경우에만
				whereSQL=" WHERE P_NUM LIKE '%'||?||'%' AND SUP_IDNUM LIKE '%'||?||'%' ";
			}//if(!"".equals(searchProduct)) END
			
			//데이터베이스 객체 생성
			Connection con=ds.getConnection();
			
			//목록 얻는 쿼리 실행
			
			String end=" ORDER BY COMPLETE, ORDER_NUM DESC ) A WHERE ROWNUM <= ?+? ) WHERE RNUM > ? ";
	        String first="SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT * FROM ORDER_CHECK_TABLE ";
	        String query=first+whereSQL+end;

			
	        PreparedStatement pstmt = con.prepareStatement(query);
	        if(!"".equals(whereSQL)){   //검색어가 있을 경우에만 ? 2개
	           pstmt.setString(1, searchComp);
	           pstmt.setString(2, searchProduct);
	           pstmt.setInt(3, listCount*(pageNum-1));
	           pstmt.setInt(4, listCount);
	           pstmt.setInt(5, listCount*(pageNum-1));
	        }else{
	           pstmt.setInt(1, listCount*(pageNum-1));
	           pstmt.setInt(2, listCount);
	           pstmt.setInt(3, listCount*(pageNum-1));
	        }
	        //if(!"".equals(whereSQL))END
			
			ResultSet rs=pstmt.executeQuery();	//쿼리 실행
			System.out.println(query);
			
			while (rs.next()){
				InvenDTO invenDTO= new InvenDTO();
				
				invenDTO.setP_num(rs.getString(2));
				invenDTO.setOrder_vol(rs.getInt(3));
				invenDTO.setOrder_date(rs.getString(4));
				invenDTO.setMemo(rs.getString(5));
				invenDTO.setComplete(rs.getString(6));
				invenDTO.setOrderNum(rs.getInt(7));
				invenDTO.setCus_idNum(rs.getString(8));
				invenDTO.setSup_idNum(rs.getString(9));

				boardList.add(invenDTO);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch(SQLException e){
			System.out.println("selectCount() PSTMT ERR : "+e.getMessage());
		} 
		return boardList;
	}//selectSOrderList END
	
	
	/**
	 * 고객사 주문목록 리스트 보기 메서드
	 */
	public ArrayList<InvenDTO> selectCOrderList(PageDTO page){
		int pageNum =1;
		if (page.getPageNum()!=null){
			pageNum=Integer.parseInt(page.getPageNum());
		}
		String searchComp = page.getSearchComp();
		String searchProduct = page.getSearchProduct();
		String whereSQL="";		
		
		int listCount=page.getListCount();
		
		ArrayList<InvenDTO> boardList = new ArrayList<InvenDTO>();
		
		try{
			//검색어 쿼리문 둘중 하나만이라도 입력값있으면...
			if(!"".equals(searchComp)||!"".equals(searchProduct)){	//검색어가 있을 경우에만
				whereSQL=" WHERE P_NUM LIKE '%'||?||'%' AND SUP_IDNUM LIKE '%'||?||'%' ";
			}//if(!"".equals(searchProduct)) END

			//데이터베이스 객체 생성
			Connection con=ds.getConnection();
			
			//목록 얻는 쿼리 실행
			
			String end=" ORDER BY COMPLETE, ORDER_NUM DESC ) A WHERE ROWNUM <= ?+? ) WHERE RNUM > ? ";
	        String first="SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT * FROM ORDER_CHECKSUP_TABLE ";
	        String query=first+whereSQL+end;
			
	        PreparedStatement pstmt = con.prepareStatement(query);
	        if(!"".equals(whereSQL)){   //검색어가 있을 경우에만 ? 2개
	           pstmt.setString(1, searchComp);
	           pstmt.setString(2, searchProduct);
	           pstmt.setInt(3, listCount*(pageNum-1));
	           pstmt.setInt(4, listCount);
	           pstmt.setInt(5, listCount*(pageNum-1));
	        }else{
	           pstmt.setInt(1, listCount*(pageNum-1));
	           pstmt.setInt(2, listCount);
	           pstmt.setInt(3, listCount*(pageNum-1));
	        }
	        //if(!"".equals(whereSQL))END
			
			ResultSet rs=pstmt.executeQuery();	//쿼리 실행
			System.out.println(query);
			
			while (rs.next()){
				InvenDTO invenDTO= new InvenDTO();
				
				invenDTO.setP_num(rs.getString(2));
				invenDTO.setOrder_vol(rs.getInt(3));
				invenDTO.setOrder_date(rs.getString(4));
				invenDTO.setMemo(rs.getString(5));
				invenDTO.setComplete(rs.getString(6));
				invenDTO.setOrderNum(rs.getInt(7));
				invenDTO.setCus_idNum(rs.getString(8));
				invenDTO.setSup_idNum(rs.getString(9));
				
				boardList.add(invenDTO);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch(SQLException e){
			System.out.println("selectCount() PSTMT ERR : "+e.getMessage());
		} 
		return boardList;
	}//selectCOrderList END
	
	/**
	 * 재고 대비 생산량 확인 메서드
	 */
	public String checkVolume(String m){
		String success="SUCCESS";
		try {
			String sql = "SELECT CUS_INVEN FROM CUSTOMER_INVEN_TABLE WHERE P_NUM = 'A001'";
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			int manufacturing = Integer.parseInt(m);
			
			if(rs.next()){
				if(rs.getInt(1) < 3*manufacturing){
					String fail="FAIL";
					return fail;
				}
			}//if(rs.next()) A END
			
			stmt= null;
			rs=null;
			
			sql= "SELECT CUS_INVEN FROM CUSTOMER_INVEN_TABLE WHERE P_NUM = 'B001'";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				if(rs.getInt(1) < 4*manufacturing){
					String fail="FAIL";
					return fail;
				}
			}//if(rs.next()) B END
			
			stmt= null;
			rs=null;
			
			sql= "SELECT CUS_INVEN FROM CUSTOMER_INVEN_TABLE WHERE P_NUM = 'C001'";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				if(rs.getInt(1) < 2*manufacturing){
					String fail="FAIL";
					return fail;
				}
			}//if(rs.next()) C END
			
		} catch (SQLException e) {
			System.out.println("checkVolume con ERR "+e.getMessage());
		}
		return success;
	}//checkVolume() END
	
	/**
	 * 협력사 : 재고 대비 납품량 확인 메서드
	 */
	public String checkSupVolume(String order_vol, String p_num){
		String success="SUCCESS";
		try {
			String sql = "SELECT P_INVEN FROM PRODUCT_TABLE WHERE P_NUM = ?";
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, p_num);
			
			ResultSet rs = stmt.executeQuery();
			int delivery = Integer.parseInt(order_vol);
			
			if(rs.next()){
				if(rs.getInt(1) < delivery){
					String fail="FAIL";
					return fail;
				}
			}
			
			/*stmt= null;
			rs=null;
			
			sql= "SELECT P_INVEN FROM PRODUCT_TABLE WHERE P_NUM = 'B001'";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				if(rs.getInt(1) < delivery){
					String fail="FAIL";
					return fail;
				}
			}//if(rs.next()) B END
			
			stmt= null;
			rs=null;
			
			sql= "SELECT P_INVEN FROM PRODUCT_TABLE WHERE P_NUM = 'C001'";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				if(rs.getInt(1) < delivery){
					String fail="FAIL";
					return fail;
				}
			}//if(rs.next()) C END
*/			
		} catch (SQLException e) {
			System.out.println("checkVolume con ERR "+e.getMessage());
		}
		return success;
	}//checkSupVolume() END
	
	/**
	 * 고객사 완제품 목록 구하기
	 */
	public ArrayList<InvenDTO> selectCusGoods(){
		System.out.println("selectCusGoods() 호출됨");
		ArrayList<InvenDTO> invenList = new ArrayList<InvenDTO>();
		try {
			String query="SELECT * FROM CUSTOMER_GOODS_TABLE";
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			
			ResultSet rs=pstmt.executeQuery();
			System.out.println(query);
			
			while (rs.next()){
				InvenDTO invenDTO= new InvenDTO();
				
				invenDTO.setGoods_name(rs.getString(1));
				invenDTO.setGoods_inven(rs.getInt(2));
				
				invenList.add(invenDTO);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("selectCusGoods Con ERR "+e.getMessage());
		}
		return invenList;
	}//selectCusGoods END
	
	/**
	 * 고객사 완제품 갯수 증가, 고객사 부품재고 감소 메서드
	 */
	public void updateProduct(String manuNum){
		System.out.println("updateProduct() 호출됨");
		try {
			String sql = "UPDATE CUSTOMER_GOODS_TABLE SET GOODS_INVEN = GOODS_INVEN + ? WHERE GOODS_NAME='SCM437'";
			Connection con = ds.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(manuNum));
			int a=stmt.executeUpdate();	//고객사 완제품 재고 수량 증가
			System.out.println(a+" 개 업데이트");
			
			stmt=null;
			sql="UPDATE CUSTOMER_INVEN_TABLE SET CUS_INVEN = CUS_INVEN - (3*?) WHERE P_NUM='A001'";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(manuNum));
			stmt.executeUpdate();	//고객사 A부품 재고 수량 감소
			
			stmt=null;
			sql="UPDATE CUSTOMER_INVEN_TABLE SET CUS_INVEN = CUS_INVEN - (4*?) WHERE P_NUM='B001'";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(manuNum));
			stmt.executeUpdate();	//고객사 B부품 재고 수량 감소
			
			stmt=null;
			sql="UPDATE CUSTOMER_INVEN_TABLE SET CUS_INVEN = CUS_INVEN - (2*?) WHERE P_NUM='C001'";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(manuNum));
			stmt.executeUpdate();	//고객사 C부품 재고 수량 감소
			
		} catch (SQLException e) {
			System.out.println("updateProduct ERR "+e.getMessage());
		}
	}//updateProduct END

}//CLASS END
