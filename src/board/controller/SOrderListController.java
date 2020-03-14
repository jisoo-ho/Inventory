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

/**
 * 협력사 주문내역 확인 하기(납품하기 버튼 기능) 클래스
 * @author USER
 *
 */
public class SOrderListController extends AbstractCommandController {

	private BoardDAO dao;
	private PageNavigator pageNavigator;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	public void setPageNavigator(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;
	}

	public SOrderListController() {
		// TODO Auto-generated constructor stub
	}

	public SOrderListController(Class commandClass) {
		super(commandClass);
		// TODO Auto-generated constructor stub
	}

	public SOrderListController(Class commandClass, String commandName) {
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
		
		String tableName = " ORDER_CHECK_TABLE ";
		//총 갯수 얻기
		int totalCount=dao.selectCount(page, tableName);
		System.out.println(totalCount);
		String p_navi=pageNavigator.getPageNavigator(totalCount, page , "s_checkOrder.do");
		
		//주문목록 리스트
		ArrayList<InvenDTO> boardList = dao.selectSOrderList(page);
				
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("sup_orderList");
		modelAndView.addObject("totalCount", totalCount);
		modelAndView.addObject("pageNavigator", p_navi);
		modelAndView.addObject("boardPage", page);
		modelAndView.addObject("boardList", boardList);
		
			
		return modelAndView;
	}

}
