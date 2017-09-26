import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class RuleManager {
	
	TaxRule rule;
	static RuleManager ruleManagerInstance;
	DocumentBuilderFactory builderFactory;
	
	private RuleManager() {
		builderFactory = DocumentBuilderFactory.newInstance();
		load();
	}
		
	public static RuleManager getInstance() {
		if (ruleManagerInstance == null) {
			ruleManagerInstance = new RuleManager();
		}
		return ruleManagerInstance;
	}
	
	public TaxRule getTaxRule() { return this.rule; }	
	
	public Document parse(String filePath) { 
	     Document document = null; 
	     try { 
	        //DOM parser instance 
	        DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
	        //parse an XML file into a DOM tree 
	        document = builder.parse(new File(filePath)); 
	     } catch (ParserConfigurationException e) { 
	        e.printStackTrace();  
	     } catch (SAXException e) { 
	        e.printStackTrace(); 
	     } catch (IOException e) { 
	        e.printStackTrace(); 
	     } 
	     return document; 
	}
	
	public void load() {
		Document document = parse("resource/config.xml");
		try {
			int numOfLevel = Integer.parseInt(document.getElementsByTagName("numOfLevel").item(0).getTextContent());
			double firstThreshold = Integer.parseInt(document.getElementsByTagName("firstThreshold").item(0).getTextContent());
			double threshold[] = new double[numOfLevel];
			double taxRate[] = new double[numOfLevel];
			Node thresholdNode = document.getElementsByTagName("threshold").item(0);
			Node taxRateNode = document.getElementsByTagName("taxRate").item(0);
			if (thresholdNode.getNodeType() == Node.ELEMENT_NODE && taxRateNode.getNodeType() == Node.ELEMENT_NODE) {
				Element thresholdElement = (Element) thresholdNode;
				Element taxRateElement = (Element) taxRateNode;
				for (int i = 0; i < numOfLevel; i++) {
					threshold[i] = Double.parseDouble(thresholdElement.getElementsByTagName("item").item(i).getTextContent());
					taxRate[i] = Double.parseDouble(taxRateElement.getElementsByTagName("item").item(i).getTextContent());
				}
			}
			rule = new TaxRule(numOfLevel, firstThreshold, threshold, taxRate);
		}
		catch (Exception e) {
			System.out.println("Wrong format of config.xml!");
		}
	}
	
	public void reload() {
		load();
	}
	
	public void modify(TaxRule taxRule) {
		
		try {
			Document document = parse("resource/config.xml");
			int numOfLevel = taxRule.getNumOfLevel();
			double firstThreshold = taxRule.getFirstThreshold();
			int oldNumOfLevel = Integer.parseInt(document.getElementsByTagName("numOfLevel").item(0).getTextContent());
			document.getElementsByTagName("numOfLevel").item(0).setTextContent(String.valueOf(numOfLevel));
			document.getElementsByTagName("firstThreshold").item(0).setTextContent(String.valueOf(firstThreshold));
			Node thresholdNode = document.getElementsByTagName("threshold").item(0);
			Node taxRateNode = document.getElementsByTagName("taxRate").item(0);
			if (thresholdNode.getNodeType() == Node.ELEMENT_NODE && taxRateNode.getNodeType() == Node.ELEMENT_NODE) {
				Element thresholdElement = (Element) thresholdNode;
				Element taxRateElement = (Element) taxRateNode;
				for (int i = 0; i < numOfLevel; i++) {
					if (i >= oldNumOfLevel) {
						Element thresholdItem = document.createElement("item");
						thresholdItem.appendChild(document.createTextNode(String.valueOf(taxRule.getThreshold()[i])));
						thresholdElement.appendChild(thresholdItem);
						Element taxRateItem = document.createElement("item");
						taxRateItem.appendChild(document.createTextNode(String.valueOf(taxRule.getTaxRate()[i])));
						taxRateElement.appendChild(taxRateItem);
					}
					else {
						thresholdElement.getElementsByTagName("item").item(i).setTextContent((String.valueOf(taxRule.getThreshold()[i])));
						taxRateElement.getElementsByTagName("item").item(i).setTextContent((String.valueOf(taxRule.getTaxRate()[i])));
					}
				}
				if (oldNumOfLevel > numOfLevel) {
					for (int i = numOfLevel; i < oldNumOfLevel; i++) {
						thresholdElement.removeChild(thresholdElement.getElementsByTagName("item").item(i));
						taxRateElement.removeChild(taxRateElement.getElementsByTagName("item").item(i));
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(document);
	        StreamResult result = new StreamResult("config.xml");
	        transformer.transform(source, result);
		}
		catch (Exception e) {
			System.out.println("Wrong format of config.xml!");
		}
	}
	
public void modify(String xml) {
		String filepath = "resource/config.xml";
		try {
			FileWriter writer = new FileWriter(filepath);
			writer.write(xml);
			writer.close();
		}
		catch (Exception e) {
			System.out.println("Wrong format of config.xml!");
		}
	}
}
