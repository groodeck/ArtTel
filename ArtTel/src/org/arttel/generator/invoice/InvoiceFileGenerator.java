package org.arttel.generator.invoice;

import java.util.List;

import org.arttel.controller.vo.InvoiceVO;
import org.arttel.generator.FileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Component
@Transactional
public class InvoiceFileGenerator extends FileGenerator {

	@Autowired
	private InvoiceContentGenerator contentGenerator;

	public Optional<String> generatePdf(final InvoiceVO invoice, final String sessionId) {
		final String filePath = createFile(sessionId, invoice.getNumber(), contentGenerator.generateHtml(invoice));
		return Optional.fromNullable(filePath);
	}

	public List<String> generatePdf(final List<InvoiceVO> invoices, final String sessionId) {
		final List<String> resultFilesPaths = Lists.newArrayList();
		for(final InvoiceVO invoice : invoices){
			final Optional<String> generatedFilePath = generatePdf(invoice, sessionId);
			if(generatedFilePath.isPresent()){
				resultFilesPaths.add(generatedFilePath.get());
			}
		}
		return resultFilesPaths;
	}
}
