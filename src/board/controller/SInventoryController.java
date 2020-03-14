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
 * 협력사 재고 페이지 
 * 페이징 정보 (협력사 납품 리스트 받아서 보여주기)
 *
 */
public class SInventoryController extends AbstractCommandController {

	private BoardDAO dao;
	private PageNavigator pageNavigator;
	
	public void setPageNavigator(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;
		System.out.println("pageNavigator "+dao);
	}

	public void setDao(BoardDAO dao) {
		this.dao = dao;
		System.out.println("setDao() 호출됨 dao : "+dao);
	}

	public SInventoryController() {
		// TODO Auto-generated constructor stub
	}

	public SInventoryController(Class commandClass) {
		super(commandClass);
		// TODO Auto-generated constructor stub
	}

	public SInventoryController(Class commandClass, String commandName) {
		super(commandClass, commandName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException error)
			throws Exception {
		System.out.println("CInventoryController 호출됨");
		
		request.setCharacterEncoding("UTF-8");
		
		PageDTO page = (PageDTO) command;
		
		String tableName = " SUPPLIER_DELIVERY_TABLE ";
		//총 갯수 얻기
		int totalCount=dao.selectCount(page, tableName);
		System.out.println(totalCount);
		
		String p_navi=pageNavigator.getPageNavigator(totalCount, page , "s_inventory.do");
		
		//입고목록 리스트
		ArrayList<InvenDTO> boardList = dao.selectSList(page);
		//고객사 재고 목록 리스트
		ArrayList<InvenDTO> invenList = dao.selectSInven();
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("s_inventory");
		modelAndView.addObject("boardList", boardList);
		modelAndView.addObject("invenList", invenList);
		modelAndView.addObject("pageNavigator", p_navi);
		modelAndView.addObject("boardPage", page);
		
		return modelAndView;
	}

}
