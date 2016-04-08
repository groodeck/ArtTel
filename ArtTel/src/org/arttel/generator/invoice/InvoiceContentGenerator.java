package org.arttel.generator.invoice;

import org.arttel.generator.ContentGenerator;
import org.springframework.stereotype.Component;

@Component
public class InvoiceContentGenerator extends ContentGenerator {
	@Override
	protected String getTemplateName() {
		return "invoiceTemplate.ftl";
	}
}
