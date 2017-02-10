import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;


public class HtmlparserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 StringBean sb = new StringBean ();
	     Parser parser = new Parser ();
//	     String HTMLString = "<p style=\"font-style:italic\">line1<br/>Line2</p><p>New Paragraph.</p>\r\n<p>Third Paragraph!<span>Span1</span><span>Span2</span></p>";
	     String HTMLString = "<div>A</div><span>span</span><div>B</div>";
	     try{
		     parser.setInputHTML(HTMLString);
		     parser.visitAllNodesWith (sb);
		     System.out.println(sb.getStrings ());
	     }catch(ParserException pe){
	    	 pe.printStackTrace();
	     }
	}

}
