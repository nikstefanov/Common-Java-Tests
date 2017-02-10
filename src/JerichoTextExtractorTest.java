import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;


public class JerichoTextExtractorTest {

	public static void main(String[] args) {
//		String HTMLString = "<p style=\"font-style:italic\">line1<br/>Line2</p><p>New Paragraph.</p>\r\n<p>Third Paragraph!<span>Span1</span><span>Span2</span></p>";
		String HTMLString = "<div>A</div><span>span</span><div>B</div>";
		Source htmlSource = new Source(HTMLString);
	    Segment htmlSeg = new Segment(htmlSource, 0, HTMLString.length());
	    Renderer htmlRend = new Renderer(htmlSeg);
	    System.out.println(htmlRend.toString());
//	    TextExtractor te = new TextExtractor(htmlSeg);
//	    System.out.println(
//	    		te.setConvertNonBreakingSpaces(true).toString()); 

	}
}
