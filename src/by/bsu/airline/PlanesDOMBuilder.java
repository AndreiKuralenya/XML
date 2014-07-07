package by.bsu.airline;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.bsu.airline.model.Plane;

public class PlanesDOMBuilder {
	private Set<Plane> planes;
	private DocumentBuilder docBuilder;

	public PlanesDOMBuilder() {
		this.planes = new HashSet<Plane>();
		// создание DOM-анализатора
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println("Ошибка конфигурации парсера: " + e);
		}
	}

	public Set<Plane> getPlanes() {
		return planes;
	}

	public void buildSetPlanes(String fileName) {
		Document doc = null;
		try {
			// parsing XML-документа и создание древовидной структуры
			doc = docBuilder.parse(fileName);
			Element root = doc.getDocumentElement();
			// получение списка дочерних элементов <student>
			NodeList planesList = root.getElementsByTagName("plane");
			for (int i = 0; i < planesList.getLength(); i++) {
				Element planeElement = (Element) planesList.item(i);
				Plane plane = buildPlane(planeElement);
				planes.add(plane);
			}
		} catch (IOException e) {
			System.err.println("File error or I/O error: " + e);
		} catch (SAXException e) {
			System.err.println("Parsing failure: " + e);
		}
	}

	private Plane buildPlane(Element planeElement) {
		Plane plane = new Plane();
		// заполнение объекта plane
		plane.setView(planeElement.getAttribute("view")); // проверка
															// на null
		plane.setName(getElementTextContent(planeElement, "name"));
		Integer year = Integer.parseInt(getElementTextContent(planeElement,
				"year"));
		plane.setYear(year);
		Plane.Characteristics characteristics = plane.getCharacteristics();
		// заполнение объекта characteristics
		Element characteristicsElement = (Element) planeElement
				.getElementsByTagName("characteristics").item(0);
		characteristics.setCapacity(getElementTextContent(
				characteristicsElement, "capacity"));
		characteristics.setPassanger(getElementTextContent(
				characteristicsElement, "passanger"));
		characteristics.setFuelWeight(getElementTextContent(
				characteristicsElement, "fuel_weight"));
		characteristics.setCruisingSpeed(getElementTextContent(
				characteristicsElement, "cruising_speed"));
		characteristics.setRange(getElementTextContent(characteristicsElement,
				"range"));
		characteristics.setLogin(planeElement.getAttribute("login"));
		return plane;
	}

	// получение текстового содержимого тега
	private static String getElementTextContent(Element element,
			String elementName) {
		NodeList nList = element.getElementsByTagName(elementName);
		Node node = (Node) nList.item(0);
		String text = node.getTextContent();
		return text;
	}
}