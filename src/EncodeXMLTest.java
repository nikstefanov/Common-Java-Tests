import org.apache.commons.lang3.StringEscapeUtils;


public class EncodeXMLTest {

	public static void main(String[] args){
		String A = "as&rt<li>i\"©µ¿ñø444þ6εδφ";
		String B = StringEscapeUtils.escapeXml(A);
		String C = "24234\\u04020";
		String D =StringEscapeUtils.unescapeXml(C);
		System.out.println(A+"\r\n"+B+"\r\n"+C+"\r\n"+D);
	}
}
