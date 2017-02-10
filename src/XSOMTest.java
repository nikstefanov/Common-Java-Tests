import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.XSType;
import com.sun.xml.xsom.parser.XSOMParser;


public class XSOMTest {

	private XSSchema xsSchema;
	private static final String Tabs="\t\t\t\t\t\t\t\t\t\t\t\t";
	public XSOMTest(){
		XSOMParser parser = new XSOMParser();
		try{
	        parser.parse(new File("D:\\derfiles\\mpa\\mpa11.xsd"));
	        XSSchemaSet schemaSet = parser.getResult();
	        xsSchema = schemaSet.getSchema(1);
//	        printElements(xsSchema,"TMaterialsAndMethods");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(SAXException sex){
			sex.printStackTrace();
		}
	}
	
	public void printElements(String ElemName){
	    XSComplexType xsComplexType = xsSchema.getComplexType(ElemName);
	    XSContentType xsContentType = xsComplexType.getContentType();
	    XSParticle particle = xsContentType.asParticle();
	    if(particle != null){
	        XSTerm term = particle.getTerm();
	        if(term.isModelGroup()){
	            XSModelGroup xsModelGroup = term.asModelGroup();
	            XSParticle[] particles = xsModelGroup.getChildren();
	            for(XSParticle p : particles ){
	                XSTerm pterm = p.getTerm();
	                if(pterm.isElementDecl()){ //xs:element inside complex type
//	                    System.out.println(pterm);
	                	XSElementDecl ed = pterm.asElementDecl();
	                	System.out.println(ed.getName()+"  "+ed.getType().getName());
	                }
	            }
	        }
	    }
	}
	private void printComplexType(XSComplexType xsComplexType,int depth){
		XSContentType xsContentType = xsComplexType.getContentType();
	    XSParticle particle = xsContentType.asParticle();
	    if(particle != null){
	        XSTerm term = particle.getTerm();
	        if(term.isModelGroup()){
	            XSModelGroup xsModelGroup = term.asModelGroup();
	            XSParticle[] particles = xsModelGroup.getChildren();
	            for(XSParticle p : particles ){
	                XSTerm pterm = p.getTerm();
                    System.out.println(pterm);
	                if(pterm.isElementDecl()){ //xs:element inside complex type
	                	XSElementDecl xsElementDecl = pterm.asElementDecl();
	                	System.out.print(Tabs.substring(Tabs.length() - depth)+'<'+xsElementDecl.getName()+'>');
	                	if(!(p.getMaxOccurs().intValue()==1 && p.getMinOccurs().intValue()==1))
	                		System.out.print("maxOccurs="+p.getMaxOccurs()+"   minOccurs="+p.getMinOccurs());
	                	if (xsElementDecl.getType().getName().equals("string"))
	                		System.out.println("string" + "</"+xsElementDecl.getName()+'>');
	                	else{
	                		System.out.println();
	                		printComplexType(xsElementDecl.getType().asComplexType(),depth+1);
	                		System.out.println(Tabs.substring(Tabs.length() - depth)+"</"+xsElementDecl.getName()+'>');
	                	}
	                }
	            }
	        }
	    }
	}
	private void printElementDeclaration(XSElementDecl xsElementDecl,int depth){
		System.out.print(Tabs.substring(Tabs.length() - depth)+'<'+xsElementDecl.getName()+'>');
		if (xsElementDecl.getType().getName().equals("string"))
    		System.out.println("string" + "</"+xsElementDecl.getName()+'>');
    	else{
    		System.out.println();
    		XSComplexType xsComplexType = xsElementDecl.getType().asComplexType();
    		XSContentType xsContentType = xsComplexType.getContentType();
    	    XSParticle particle = xsContentType.asParticle();
    	    if(particle != null){
    	        XSTerm term = particle.getTerm();
    	        if(term.isModelGroup()){
    	            XSModelGroup xsModelGroup = term.asModelGroup();
    	            XSParticle[] particles = xsModelGroup.getChildren();
    	            for(XSParticle p : particles ){
    	                XSTerm pterm = p.getTerm();
    	                if(pterm.isElementDecl()) //xs:element inside complex type
//    	                    System.out.println(pterm);
    	                	printElementDeclaration(pterm.asElementDecl(),depth+1);    	                
    	            }
    	            System.out.println(Tabs.substring(Tabs.length() - depth)+"</"+xsElementDecl.getName()+'>');
    	        }
    	    }
    	}		
	}
	
	public void printElementsPath(XSComplexType xsComplexType,String path){
		String delimiter = (path.length()<=5)?"":">>";
		XSModelGroup.Compositor compositor;		 
	    XSContentType xsContentType = xsComplexType.getContentType();
	    XSParticle particle = xsContentType.asParticle();
	    if(particle != null){
	        XSTerm term = particle.getTerm();
	        if(term.isModelGroup()){
	            XSModelGroup xsModelGroup = term.asModelGroup();
	            compositor = xsModelGroup.getCompositor();
	            XSParticle[] particles = xsModelGroup.getChildren();
	            for(XSParticle p : particles ){
	                XSTerm pterm = p.getTerm();
//                    System.out.println(pterm);
	                if(pterm.isElementDecl()){ //xs:element inside complex type
	                	XSElementDecl xsElementDecl = pterm.asElementDecl();
//	                	 System.out.println("--"+xsElementDecl.getName());
	                	if (compositor!=XSModelGroup.Compositor.CHOICE ||
	                			compositor==XSModelGroup.Compositor.CHOICE && 
	                			xsElementDecl.getName()
	                			.equals(xsComplexType.getName().substring(1)+"Fpa")){
//	                	System.out.println(Tabs.substring(Tabs.length() - depth)+xsElementDecl.getName());	                	
//	                	if(!(p.getMaxOccurs().intValue()==1 && p.getMinOccurs().intValue()==1))
//	                		System.out.print("maxOccurs="+p.getMaxOccurs()+"   minOccurs="+p.getMinOccurs());
	                	if (xsElementDecl.getType().getName().equals("string")){
	                		if (!xsElementDecl.getName().equals("Col") && !xsElementDecl.getName().equals("Text"))
	                			System.out.println(path+delimiter+xsElementDecl.getName());
	                	}else{
//	                		if (!xsElementDecl.getType().getName().equals("TClinicalChemistry"))
	                			printElementsPath(xsElementDecl.getType().asComplexType(),path+delimiter+xsElementDecl.getName());
//	                		System.out.println(Tabs.substring(Tabs.length() - depth)+"</"+xsElementDecl.getName()+'>');
	                	}
	                	}
	                }
	            }
	        }
	    }
	}
	
	
	
	private void printElement(String ElemName){
		XSElementDecl xsElementDecl=xsSchema.getElementDecl(ElemName);
		XSType xsElementType = xsElementDecl.getType();
	}
	
	private void printTerm(XSTerm xsTerm){
//		if (xsTerm.isModelGroup()) 
	}
	
	
	
	
	private XSComplexType getCompType(){
		return xsSchema.getComplexType("TMPAData");
	}
	private XSElementDecl getElementDeclaration(){
		return xsSchema.getElementDecl("FSTRAData");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XSOMTest T = new XSOMTest();
//		T.printElements("TAMAData");
//		T.printComplexType(T.getCompType(),0);
//		T.printElementDeclaration(T.getElementDeclaration(),0);
		T.printElementsPath(T.getCompType(),"MPA||");
	}
}
