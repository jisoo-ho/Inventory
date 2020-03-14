package board.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import board.dao.BoardDAO;
import board.dto.InvenDTO;
/**
 * 고객사 완재품 수량과 부품 재고 확인하고
 * 제품 생산하는 컨트롤러
 * @author USER
 *
 */
public class CusMFController implements Controller {
	
	private BoardDAO dao;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	public CusMFController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		//완제품 갯수 뽑아오기
		ArrayList<InvenDTO> boardList= dao.selectCusGoods();
		
		//고객사 부품 재고 구하기
		ArrayList<InvenDTO> invenList = dao.selectCInven();
		
		//생산 페이지(cus_mf.jsp) 로 자료들들 보내기
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cus_mf");
		modelAndView.addObject("invenList", invenList);
		modelAndView.addObject("boardList", boardList);
		
		return modelAndView;
	}//MODEL AND VIEW END

}
