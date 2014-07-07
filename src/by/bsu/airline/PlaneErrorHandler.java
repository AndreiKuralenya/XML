package by.bsu.airline;

import java.io.IOException;
import java.util.logging.Logger;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class PlaneErrorHandler extends DefaultHandler {
	// создание регистратора ошибок для пакета by.bsu.valid
	private Logger LOG = Logger.getLogger("by.bsu.airline");

	public PlaneErrorHandler(String log) throws IOException {
		// установка файла и формата вывода ошибок
		LOG.addAppender(new FileAppender(new SimpleLayout(), log));
	}

	public void warning(SAXParseException e) {
		LOG.warning(getLineAddress(e) + "-" + e.getMessage());
	}

	public void error(SAXParseException e) {
		LOG.warning(getLineAddress(e) + " - " + e.getMessage());
	}

	public void fatalError(SAXParseException e) {
		LOG.warning(getLineAddress(e) + " - " + e.getMessage());
	}

	private String getLineAddress(SAXParseException e) {
		// определение строки и столбца ошибки
		return e.getLineNumber() + " : " + e.getColumnNumber();
	}
}