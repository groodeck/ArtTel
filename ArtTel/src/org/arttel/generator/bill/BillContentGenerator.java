package org.arttel.generator.bill;

import org.arttel.generator.ContentGenerator;
import org.springframework.stereotype.Component;

@Component
public class BillContentGenerator extends ContentGenerator {
	@Override
	protected String getTemplateName() {
		return "billTemplate.ftl";
	}
}
