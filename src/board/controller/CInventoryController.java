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
 * 고객사 재고 페이지 
 * 페이징 정보 (고객사 입고 리스트 받아서 보여주기)
 *
 */
public class CInventoryController extends AbstractCommandController {
	
	private BoardDAO dao;
	private PageNavigator pageNavigator;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
		System.out.println("setDao() 호출됨 dao : "+dao);
	}

	public void setPageNavigator(PageNavigator pageNavigator) {
		this.pageNavigator = pageNavigator;
	}

	public CInventoryController() {
		// TODO Auto-generated constructor stub
	}

	public CInventoryController(Class commandClass) {
		super(commandClass);
		// TODO Auto-generated constructor stub
	}

	public CInventoryController(Class commandClass, String commandName) {
		super(commandClass, commandName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException error)
			throws Exception {

		System.out.println("CInventoryController 호출됨");
		
		request.setCharacterEncoding("UTF-8");
		
		PageDTO page = (PageDTO) command;
		
		String tableName=" CUSTOMER_IN_TABLE ";
		//총 갯수 얻기
		int totalCount=dao.selectCount(page,tableName);
		
		String p_navi=pageNavigator.getPageNavigator(totalCount, page , "c_inventory.do");
		
		//입고목록 리스트
		ArrayList<InvenDTO> boardList = dao.selectCList(page);
		//고객사 재고 목록 리스트
		ArrayList<InvenDTO> invenList = dao.selectCInven();
				
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("c_inventory");
		modelAndView.addObject("totalCount", totalCount);// 리스트 총 갯수 (int)
		modelAndView.addObject("pageNavigator", p_navi); // 페이징 (String)
		modelAndView.addObject("boardPage", page);
		modelAndView.addObject("boardList", boardList);  //입고목록 들어있는 리스트
		modelAndView.addObject("invenList",invenList);   //재고목록 리스트
				
		return modelAndView;
	}

}
