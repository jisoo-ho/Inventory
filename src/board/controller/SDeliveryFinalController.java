package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import board.dao.BoardDAO;
import board.dto.InvenDTO;

/**
 * 최종 납품 하기 (form으로 입력받은 값) 및 납품하였습니다로 보내는 클래스
 * 
 * @author main
 *
 */
public class SDeliveryFinalController extends AbstractCommandController {

	private BoardDAO dao;

	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	public SDeliveryFinalController() {
		// TODO Auto-generated constructor stub
	}

	public SDeliveryFinalController(Class commandClass) {
		super(commandClass);
		// TODO Auto-generated constructor stub
	}

	public SDeliveryFinalController(Class commandClass, String commandName) {
		super(commandClass, commandName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException error) throws Exception {
		request.setCharacterEncoding("UTF-8");

		String orderNum = request.getParameter("orderNum");
		System.out.println(orderNum + " = orderNum / SDelivery Final Controller ");
		InvenDTO invenDto = dao.selectOne(orderNum);

		// 입력받은 주문번호에서 요구하는 수량이 현재고로 처리 가능한지 검토
		String order_vol = request.getParameter("order_vol");
		String p_num = request.getParameter("p_num");
		System.out.println(order_vol + " = order_vol / SDeliveryFinalController / p_num = " + p_num);
		String success = dao.checkSupVolume(order_vol, p_num);
		System.out.println(success + " = Success / SDeliveryFinalController ");

		ModelAndView modelAndView = new ModelAndView();

		// 실패, 성공에 따라 페이지 이동 및 업데이트
		if (success.equals("SUCCESS")) {
			dao.insertDelivery(invenDto);

			String lotNum = dao.selectLOTnum(invenDto);

			dao.insertCustom(invenDto, lotNum);
			dao.updateSUPinven(invenDto);
			dao.updateCUSinven(invenDto);
			dao.updateStatus(orderNum);

			modelAndView.setViewName("delivery_success");
		} else {
			modelAndView.setViewName("delivery_fail");
		}
		return modelAndView;
	}// MODEL AND VIEW END

}
