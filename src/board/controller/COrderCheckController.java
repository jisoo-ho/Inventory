package board.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import board.dao.BoardDAO;
import board.dto.InvenDTO;
import board.page.PageDTO;
import board.util.PageNavigator;

public class COrderCheckController extends AbstractCommandController {

	private BoardDAO dao;
	private PageNavigator pageNavigator;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
		System.out.println("setDao() 호출됨 dao : "+dao);
	}

	public void setPageNavigator(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;
	}

	public COrderCheckController() {
		// TODO Auto-generated constructor stub
	}

	public COrderCheckController(Class commandClass) {
		super(commandClass);
		// TODO Auto-generated constructor stub
	}

	public COrderCheckController(Class commandClass, String commandName) {
		super(commandClass, commandName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException error)
			throws Exception {
		System.out.println("COrderCheckController 호출됨");
		
		request.setCharacterEncoding("UTF-8");
		
		PageDTO page = (PageDTO) command;
		page.setListCount(10);
		
		String tableName=" ORDER_CHECKSUP_TABLE ";
		//총 갯수 얻기
		int totalCount=dao.selectCount(page , tableName);
		System.out.println(totalCount);
		String p_navi=pageNavigator.getPageNavigator(totalCount, page , "c_orderCheck.do");
		
		//주문목록 리스트
		ArrayList<InvenDTO> boardList = dao.selectCOrderList(page);
				
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("custom_orderList");
		modelAndView.addObject("totalCount", totalCount);
		modelAndView.addObject("pageNavigator", p_navi);
		modelAndView.addObject("boardPage", page);
		modelAndView.addObject("boardList", boardList);
		
		return modelAndView;
	}

}
