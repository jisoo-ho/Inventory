package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import board.dao.BoardDAO;
/**
 * 납품 하기 클래스(납품 하기 눌렀을 때) 주문번호 기반 납품목록 확인
 * @author USER
 *
 */
public class SDeliveryCheckController implements Controller {
	
	private BoardDAO dao;

	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	public SDeliveryCheckController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//입력받은 주문번호
		String orderNum = request.getParameter("orderNum");
		//입력받은 주문수량
		String order_vol = request.getParameter("order_vol");
		//입력받은 주문수량
		String p_num = request.getParameter("p_num");
		
		ModelAndView modelAndView = new ModelAndView("delivery_check");
		
		modelAndView.addObject("orderNum", orderNum);
		modelAndView.addObject("order_vol", order_vol);
		modelAndView.addObject("p_num", p_num);
		return modelAndView;
	}

}
