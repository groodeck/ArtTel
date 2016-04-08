package org.arttel.generator.bill;

import org.arttel.controller.vo.BillVO;
import org.arttel.generator.FileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

@Component
@Transactional
public class BillFileGenerator extends FileGenerator {

	@Autowired
	private BillContentGenerator contentGenerator;

	public Optional<String> generatePdf(final BillVO invoice, final String sessionId) {
		final String filePath = createFile(sessionId, invoice.getNumber(), contentGenerator.generateHtml(invoice));
		return Optional.fromNullable(filePath);
	}
}
