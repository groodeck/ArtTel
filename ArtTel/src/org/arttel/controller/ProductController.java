package org.arttel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.ProductVO;
import org.arttel.controller.vo.filter.ProductFilterVO;
import org.arttel.dao.ProductDAO;
import org.arttel.dictionary.UnitType;
import org.arttel.dictionary.VatRate;
import org.arttel.exception.DaoException;
import org.arttel.ui.TableHeader;
import org.arttel.view.ComboElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController extends BaseController {

	private enum Event {
		MAIN, SAVE, EDIT, NEW, DELETE, SEARCH, BACK
	}

	@Autowired
	private ProductDAO productDao;

	private String targetPage = jspContextPrefix + "products.jsp";

	private final Logger log = Logger.getLogger(ProductController.class);

	private static final String SELECTED_PRODUCT = "selectedProduct";
	private static final String PRODUCT_LIST = "productList";
	private static final String PRODUCT_FILTER = "productFilter";

	protected Event getEvent(final HttpServletRequest request,
			final Event defaultValue) {

		Event event = defaultValue;
		final String eventStr = request.getParameter("event");
		if (eventStr != null) {
			event = Event.valueOf(eventStr.toUpperCase());
		}
		return event;
	}

	@Override
	protected TableHeader getModelDefaultHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	private ProductFilterVO getProductFilterFromRequest(
			final HttpServletRequest request) {
		ProductFilterVO clientFilterVO = (ProductFilterVO) request
				.getSession().getAttribute(PRODUCT_FILTER);
		if(clientFilterVO == null){
			clientFilterVO = new ProductFilterVO();
			request.setAttribute(PRODUCT_FILTER, clientFilterVO);
		}
		return clientFilterVO;
	}

	@Override
	protected String getTableHeaderAttrName() {
		return "productTableHeader";
	}

	private void performActionBackToSearchresults(
			final UserContext userContext, final HttpServletRequest request) {
		final ProductFilterVO productFilterVO = getProductFilterFromRequest(request);
		productFilterVO.setUser(userContext.getUserName());
		final List<ProductVO> productList = productDao.getProductList(productFilterVO);
		request.setAttribute(PRODUCT_LIST, productList);
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private void performActionDelete(final UserContext userContext,
			final HttpServletRequest request) {

		final String productId = request.getParameter(EVENT_PARAM);
		try {
			if (productId != null) {
				productDao.deleteProductById(productId);
			}
		} catch (final DaoException e) {
			log.error("DaoException", e);
		}
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionEdit(final UserContext userContext,
			final HttpServletRequest request) {

		final String productId = request.getParameter(EVENT_PARAM);

		if (productId != null) {
			final ProductVO productDetails = productDao.getProductById(productId);
			productDetails.applyPermissions(userContext.getUserName());
			request.setAttribute(SELECTED_PRODUCT, productDetails);
		}
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionMain(final UserContext userContext,
			final HttpServletRequest request) {
		request.setAttribute(EVENT, Event.MAIN);
	}

	private void performActionNew(final UserContext userContext,
			final HttpServletRequest request) {

		final ProductVO productDetails = new ProductVO();
		productDetails.applyPermissions(userContext.getUserName());
		request.setAttribute(SELECTED_PRODUCT, productDetails);
		request.setAttribute(EVENT, Event.EDIT);
	}

	private void performActionSave(final UserContext userContext,
			final ProductVO productVO, final HttpServletRequest request) {

		productDao.save(productVO, userContext.getUserName());
		performActionBackToSearchresults(userContext, request);
	}

	private void performActionSearch(final UserContext userContext,
			final ProductFilterVO productFilterVO, final HttpServletRequest request) {

		productFilterVO.populate(request);
		productFilterVO.setUser(userContext.getUserName());
		request.getSession().setAttribute(PRODUCT_FILTER, productFilterVO);
		final List<ProductVO> productList = productDao.getProductList(productFilterVO);
		request.setAttribute(PRODUCT_LIST, productList);
		request.setAttribute(EVENT, Event.SEARCH);
	}

	private Map<String, List<? extends ComboElement>> prepareSelectsMap(final String user) {
		final Map<String, List<? extends ComboElement>> selectsMap = new HashMap<String, List<? extends ComboElement>>();
		selectsMap.put("unitTypesDictionary", UnitType.getComboElementList(false));
		selectsMap.put("vatRateDictionary", VatRate.getComboElementList(false));
		selectsMap.put("productDictionary", productDao.getProductDictionary(false, user));
		return selectsMap;
	}

	@RequestMapping("/products.app")
	public String process(final HttpServletRequest request, final HttpServletResponse response) {

		final UserContext userContext = getUserContext(request);

		final ProductVO productVO = (ProductVO) getForm(ProductVO.class, request);
		productVO.populate(request);

		final Event event = getEvent(request, Event.MAIN);

		switch (event) {
		case MAIN:
			performActionMain(userContext, request);
			break;
		case SAVE:
			performActionSave(userContext, productVO, request);
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
			performActionSearch(userContext, productVO.getProductFilter(),
					request);
			break;
		case BACK:
			performActionBackToSearchresults(userContext, request);
			break;
		default:
		}
		request.setAttribute("selectsMap", prepareSelectsMap(userContext.getUserName()));
		return "products";
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

}
