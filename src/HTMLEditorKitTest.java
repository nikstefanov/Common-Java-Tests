import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;


public class HTMLEditorKitTest extends ParserCallback {

	private final StringBuilder s;
	
	public HTMLEditorKitTest(){
		s = new StringBuilder();
	}
	
	@Override
	public void handleText(char[] text, int pos) {
		   s.append(text);
		 }

	public void parse(Reader in) throws IOException {
		   s.delete(0, s.length());
		   ParserDelegator delegator = new ParserDelegator();
		   // the third parameter is TRUE to ignore charset directive
		   delegator.parse(in, this, Boolean.TRUE);
		 }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String HTMLString = "<p style=\"font-style:italic\">line1<br/>Line2</p><p>New Paragraph.</p>\r\n<p>Third Paragraph!<span>Span1</span><span>Span2</span></p>";
		StringReader sr = new StringReader(HTMLString);
		HTMLEditorKitTest kit = new HTMLEditorKitTest();
		try{
			kit.parse(sr);
			System.out.println(kit.s.toString());
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

}
