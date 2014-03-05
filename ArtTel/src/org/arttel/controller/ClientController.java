package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.ClientVO;
import org.arttel.controller.vo.filter.ClientFilterVO;
import org.arttel.dao.ClientDAO;
import org.arttel.dictionary.context.DictionaryPurpose;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController extends BaseController {

	@Autowired
	private ClientDAO clientDao;

	private String targetPage = jspContextPrefix + "clients.jsp";

	private final Logger log = Logger.getLogger(ClientController.class);

	private enum Event {
		MAIN, SAVE, EDIT, NEW, DELETE, SEARCH, BACK
	}

	private static final String SELECTED_CLIENT = "selectedClient";
	private static final String CLIENT_LIST = "clientList";
	private static final String CLIENT_FILTER = "clientFilter";

	@RequestMapping("/clients.app")
	public String process(HttpServletRequest request, HttpServletResponse response) {

		UserContext userContext = getUserContext(request);

		ClientVO clientVO = (ClientVO) getForm(ClientVO.class, request);
		clientVO.populate(request, "");

		final Event event = getEvent(request, Event.MAIN);

		switch (event) {
		case MAIN:
			performActionMain(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, clientVO, request);
			break;
		case EDIT:
			performActionEdit(userContext, request);
			break;
		case NEW:
			performActionNew(userContext, request);
			break;
		case DELETE:
			performActionDelete(userContext, request);
			break;
		case SEARCH:
			performActionSearch(userContext, clientVO.getClientFilter(),
					request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap());
		return "clients";
	}

	private void performActionEdit(final UserContext userContext,
			final HttpServletRequest request) {

		final String clientId = request.getParameter(EVENT_PARAM);

		if (clientId != null) {
			ClientVO clientDetails = clientDao.getClientById(clientId);
			request.setAttribute(SELECTED_CLIENT, clientDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionNew(final UserContext userContext,
			final HttpServletRequest request) {

		final ClientVO clientDetails = new ClientVO();
		request.setAttribute(SELECTED_CLIENT, clientDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionDelete(final UserContext userContext,
			final HttpServletRequest request) {

		final String clientId = request.getParameter(EVENT_PARAM);
		if (clientId != null) {
			clientDao.deleteClientById(clientId);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionSave(final UserContext userContext,
			final ClientVO clientVO, final HttpServletRequest request) {
		
		clientDao.save(clientVO, userContext.getUserName());
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionSearch(final UserContext userContext,
			final ClientFilterVO clientFilterVO, final HttpServletRequest request) {
		
		clientFilterVO.populate(request);
		clientFilterVO.setUser(userContext.getUserName());
		request.getSession().setAttribute(CLIENT_FILTER, clientFilterVO);
		final List<ClientVO> clientList = clientDao
				.getClientList(clientFilterVO);
		request.setAttribute(CLIENT_LIST, clientList);
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionBackToSearchresults(
			final UserContext userContext, final HttpServletRequest request) {
		final ClientFilterVO clientFilterVO = getClientFilterFromRequest(request, userContext.getUserName());
		clientFilterVO.setUser(userContext.getUserName());
		final List<ClientVO> clientList = clientDao.getClientList(clientFilterVO);
		request.setAttribute(CLIENT_LIST, clientList);
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private ClientFilterVO getClientFilterFromRequest(
			final HttpServletRequest request, String userName) {
		ClientFilterVO clientFilterVO = (ClientFilterVO) request
				.getSession().getAttribute(CLIENT_FILTER);
		if(clientFilterVO == null){
			clientFilterVO = new ClientFilterVO(userName, DictionaryPurpose.forInvoice);
			request.setAttribute(CLIENT_FILTER, clientFilterVO);
		}
		return clientFilterVO;
	}

	private void performActionMain(final UserContext userContext,
			final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.MAIN);
	}

	protected Event getEvent(final HttpServletRequest request,
			final Event defaultValue) {

		Event event = defaultValue;
		String eventStr = request.getParameter("event");
		if (eventStr != null) {
			event = Event.valueOf(eventStr.toUpperCase());
		}
		return event;
	}

	private Map<String, List<? extends ComboElement>> prepareSelectsMap() {
		return new HashMap<String, List<? extends ComboElement>>();
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

}
