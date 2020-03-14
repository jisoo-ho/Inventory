package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import board.dao.BoardDAO;
/**
 * 고객사 메인페이지로 이동(미 주문 갯수 뽑기)
 * @author USER
 *
 */
public class CMainController implements Controller {

	private BoardDAO dao;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	public CMainController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		int A = dao.countIncomplete();

		ModelAndView modelAndView = new ModelAndView("custom_main");
		modelAndView.addObject("modelAndView", A);
		return modelAndView;
	}

}
