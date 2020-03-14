package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import board.dao.BoardDAO;

public class MakeProductController implements Controller {
	
	private BoardDAO dao;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	public MakeProductController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		//입력받은 생산량
		String manuNum = request.getParameter("volume");
		
		//입력받은 생산량 기준으로 현재 재고에서 처리 가능한지 여부
		String success = dao.checkVolume(manuNum);
		System.out.println(success);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("manuNum",manuNum);

		//실패,성공 결과에 따른 페이지 이동 및 업데이트
		if(success.equals("SUCCESS")){
			modelAndView.setViewName("cmSuccess");
			dao.updateProduct(manuNum);
			
		}else{
			modelAndView.setViewName("cmFail");
		}//if END
		
		return modelAndView;
	}

}
