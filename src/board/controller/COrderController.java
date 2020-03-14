package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import board.command.CommandClass;
import board.dao.BoardDAO;

/**
 * 주문 하기(주문내역 보여주는) 클래스
 * @author USER
 *
 */
public class COrderController extends AbstractCommandController {

	private BoardDAO dao;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	public COrderController() {
		// TODO Auto-generated constructor stub
	}

	public COrderController(Class commandClass) {
		super(commandClass);
		// TODO Auto-generated constructor stub
	}

	public COrderController(Class commandClass, String commandName) {
		super(commandClass, commandName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException error)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		CommandClass data = (CommandClass) command;
		System.out.println(data.getP_num());
		System.out.println(data.getCus_idNum());
		dao.insertOrder(data);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("order_success");
		
		return modelAndView;
	}

}
