package org.arttel.generator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class ContentGenerator {

	public String generateHtml(final PrintableContent model) {
		final StringWriter out = new StringWriter();
		final Configuration cfg = new Configuration();
		cfg.setDateFormat("dd/MM/yyyy");
		cfg.setObjectWrapper( new DefaultObjectWrapper());
		try {
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			final InputStream stream = classLoader.getResourceAsStream(getTemplateName());
			final Reader reader = new InputStreamReader(stream);
			final Template temp = new Template(  getTemplateName() , reader, cfg);
			temp.setDateFormat("dd/MM/yyyy");
			temp.process(model, out);
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final TemplateException e) {
			e.printStackTrace();
		}
		return out.getBuffer().toString();
	}

	protected abstract String getTemplateName();
}
