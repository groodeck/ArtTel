package org.arttel.mapper;

import javax.servlet.ServletContext;

import org.arttel.controller.AgreementsController;
import org.arttel.controller.BaseController;
import org.arttel.controller.ClientController;
import org.arttel.controller.CorrectionController;
import org.arttel.controller.DealingController;
import org.arttel.controller.HomePageController;
import org.arttel.controller.InstallationsController;
import org.arttel.controller.InvoicesController;
import org.arttel.controller.OrdersController;
import org.arttel.controller.ProductController;
import org.arttel.controller.ReportsController;
import org.arttel.controller.SettingsController;
import org.arttel.controller.SqueezesController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public enum ControlMapper {
	
	HOME("home.app","home.jsp", HomePageController.class),
	INSTALATION("instalations.app","instalations.jsp", InstallationsController.class),
	ORDER("orders.app","orders.jsp", OrdersController.class),
	AGREEMENTS("agreements.app","agreement.jsp", AgreementsController.class),
	DEALING("dealing.app","dealing.jsp", DealingController.class),
	SQUEEZES("squeezes.app","squeezes.jsp", SqueezesController.class),
	SETTINGS("settings.app","settings.jsp", SettingsController.class),
	REPORTS("reports.app","reports.jsp", ReportsController.class),
	INVOICES("invoices.app","invoices.jsp", InvoicesController.class),
	PRODUCTS("products.app","products.jsp", ProductController.class),
	CLIENTS("clients.app","clients.jsp", ClientController.class),
	CORRECTION("correction.app", "correction.jsp", CorrectionController.class);
	
	
	private String action;
	private String pageName;
	private Class controlClass;
	
	private ControlMapper( final String action, final String page, final Class controlClass ){
		this.action = action;
		this.pageName = page;
		this.controlClass = controlClass;
	}
	
	public static BaseController getControllerByAction( final String action, ServletContext ctx ){
		for( ControlMapper mapper : values() ){
			if(mapper.getAction().equals(action)){
				WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
				return appCtx.getBean(mapper.getControlClass());
			}
		}
		return null;
	}
	
	public Class getControlClass() {
		return controlClass;
	}
	
	public String getPageName() {
		return pageName;
	}

	public String getAction() {
		return action;
	}
}
