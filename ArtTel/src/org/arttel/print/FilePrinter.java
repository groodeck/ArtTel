package org.arttel.print;

import java.io.FileInputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;

import org.springframework.stereotype.Component;

@Component
public class FilePrinter {

	public void printFile(final String file){
		try {
			final PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
			if(printService != null){
				final FileInputStream fis = new FileInputStream(file);
				final DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
				final Doc pdfDoc = new SimpleDoc(fis, flavor, null);
				final DocPrintJob printJob = printService.createPrintJob();
				printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
				fis.close();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
