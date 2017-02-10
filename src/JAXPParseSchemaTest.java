import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class JAXPParseSchemaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document doc = db.parse(new File("D:\\Nikolay's Documents\\DER\\AMA\\Schemas\\AMA4.xsd"));
		}catch (SAXException sxe) {
//			System.out.println((new Date()).toString()+" [AMA2] AMA2XMLServer3 doPost()");
			sxe.printStackTrace();
	    }catch (ParserConfigurationException pce) {
	      // Parser with specified options can't be built
//	    	System.out.println((new Date()).toString()+" [AMA2] AMA2XMLServer3 doPost()");
	    	pce.printStackTrace();
	    }catch (IOException ioe){
	    	ioe.printStackTrace();
	    }
	}

}
