package Common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
	
	public static List<String> xmltoList(String xmlpath,String xmlentity)
	{
		List<String> result=new ArrayList<>();
		try
		{
			File xmlfile=new File(xmlpath);
			 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile.getAbsoluteFile());
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(xmlentity);
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					result.add(eElement.getTextContent() )
					;
				}
			}

		}
		catch(Exception exp)
		{
			System.out.print(exp.toString());
		}
		return result;
	}

}
