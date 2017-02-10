import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.SAXException;

import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.parser.XSOMParser;


public class LoadXMLServerTest {
	
	private final char delimiter = '@';//'\u001F';
	private ArrayList<Integer> OccurList;
	//private Pattern Ptn;
	private Matcher Math;
	private StringBuilder sb;
	private XSSchema xsSchema;
	private int addTC,addPar,addRev,addFls,indexPC,numberOfTypes,addCit;
	
	private LoadXMLServerTest(){
//		addRev=0;addCit=2;addPar=0;addTC=3;indexPC=0;numberOfTypes=4;addFls=0;
		OccurList = new ArrayList<Integer>();
		StringBuilder lsb = new StringBuilder();
		Pattern Ptn = Pattern.compile("");
		loadFile("D:\\Nikolay's Documents\\Downloads\\00_99_890.1100(5).xml",lsb);
		Math = Ptn.matcher(lsb);	
		sb = new StringBuilder();
		XSOMParser parser = new XSOMParser();
		
		try{
			parser.parse(new File("D:\\derfiles\\AMA2\\XSD\\AMA5.xsd"));
			XSSchemaSet schemaSet = parser.getResult();
	        xsSchema = schemaSet.getSchema(1);
	        
//	        depthFirstSearchComplexType(xsSchema.getComplexType("TAMAData"),1);
		}catch(SAXException sex){
//			System.out.println((new Date()).toString()+" [AMA2] AMA2LoadServer2 doPost()");
			sex.printStackTrace();
		}catch(IOException ioex){
			ioex.printStackTrace();
		}	
	}
	
	private void depthFirstSearchComplexType(XSComplexType xsComplexType,int depth){
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
	                	XSElementDecl xsElementDecl = pterm.asElementDecl();
	                	int occur;
	                	if((p.getMaxOccurs().equals(p.getMinOccurs()))) occur = p.getMaxOccurs().intValue();
	                	else occur = OccurList.remove(0).intValue();
	                	System.out.println("Occur: "+occur);
	                	System.out.println(xsElementDecl.getName());
	                	for(;occur>0;occur--){
//	                		pw.print(Tabs.substring(Tabs.length() - depth)+'<'+xsElementDecl.getName()+'>');	                			                		
			                if (xsElementDecl.getType().getName().equals("string")){			                
			                	if(!xsElementDecl.getName().equals("Text") &&
			                		!xsElementDecl.getName().equals("XSDVersion"))
			                		findAppendTagContent(xsElementDecl.getName());
//			                		System.out.println(xsElementDecl.getName()+"   "+xsElementDecl.getType().getName());
			                }
			                else{
//			                	pw.println();
			                	depthFirstSearchComplexType(xsElementDecl.getType().asComplexType(),depth+1);
//			               		pw.println(Tabs.substring(Tabs.length() - depth)+"</"+xsElementDecl.getName()+'>');
			               	}
	                	}
	                }
	            }
	        }
	    }
	}
	private boolean findAppendTagContent(String Tag){
		String piece;
		Pattern Ptn = Pattern.compile('<'+Tag+">([^<>\"\']*?)</"+Tag+'>', Pattern.DOTALL);
//		System.out.println(Ptn.pattern());
    	Math.usePattern(Ptn);
    	if (Math.find()){
    		piece = Math.group(1);
//    		System.out.println(piece);
    		if (Tag.equals("InterfaceInfo")){
    			String[] Numbs = piece.split(";");
    			addRev=Integer.parseInt(Numbs[0]);
    			addCit=Integer.parseInt(Numbs[1]);
    			addPar=Integer.parseInt(Numbs[2]);
    			addTC=Integer.parseInt(Numbs[3]);
    			indexPC=Integer.parseInt(Numbs[4]);
    			numberOfTypes=Integer.parseInt(Numbs[5]);
    			addFls=Integer.parseInt(Numbs[6]);
    			sb.append(piece.replace(';', delimiter)).append(delimiter);
    			fillOccurList();
    		}else
    			sb.append(piece).append(delimiter);
    		System.out.println(Tag+"  "+piece);
    		return true;
    	}else
    		return false;
	}
	private void startSearch(){
		depthFirstSearchComplexType(xsSchema.getComplexType("TAMAData"),1);
	}
	private void printResult(){
		System.out.println(sb.toString());
	}
	private void loadFile(String FilePathIn,StringBuilder lsb){    		
		int count,buff_size=1024;
		char[] char_buff = new char[buff_size];
		try{
			BufferedReader buffIn = new BufferedReader(new FileReader(FilePathIn),buff_size);
			while((count=buffIn.read(char_buff, 0, buff_size))>0){
//				System.out.println(count);
				lsb.append(char_buff, 0, count);	
			}
//			fileContentString = sw.toString();
			buffIn.close();
		}catch(IOException ioe){
			 ioe.printStackTrace();
			 System.out.println("IOExcetion!!");
		}
    }
	private void fillOccurList(){
		OccurList.add(addRev);for(int i=addRev;i>0;i--)OccurList.add(4);
//		addToOccurList(1,addRev,4);
		OccurList.add(addCit);for(int i=addCit;i>0;i--)OccurList.add(2);
//		addToOccurList(1,addCit,2);
		//Tbl1
		OccurList.add(addTC);for(int i=addTC;i>0;i--)OccurList.add(7);
//		addToOccurList(1,addTC,7);
		OccurList.add(1-indexPC);for(int i=1-indexPC;i>0;i--)OccurList.add(7);
//		addToOccurList(1,1-indexPC,7);
		//Tbl5
		for(int i=9;i>0;i--)OccurList.add(4);
		OccurList.add(addPar);for(int i=addPar;i>0;i--)OccurList.add(5);
//		addToOccurList(1,addPar,5);
		//Tbl8
		OccurList.add(4);OccurList.add(4);
		OccurList.add(addTC);for(int i=addTC;i>0;i--)OccurList.add(4);
//		addToOccurList(1,addTC,4);
		OccurList.add(0);
		
		fillOccurListForTResultsTable(6);//Tbl9
		fillOccurListForTResultsTable(6);//Tbl10
		fillOccurListForTResultsTable(8);//Tbl11
		fillOccurListForTResultsTable(12);//Tbl12
		fillOccurListForTResultsSeverityTable(4,8);//Tbl13
		fillOccurListForTResultsSeverityTable(4,10);//Tbl14
		fillOccurListForTResultsSeverityTable(numberOfTypes,3);//Tbl15
		OccurList.add(13);fillOccurListForTResultsTable(13);//Tbl16
		OccurList.add(8);fillOccurListForTResultsTable(8);//Tbl17			
		//TblAtt
		OccurList.add(addFls);
	}
	private void fillOccurListForTResultsTable(int col){
		OccurList.add(col);OccurList.add(col);		
		OccurList.add(addTC);for(int i=addTC;i>0;i--)OccurList.add(col);
//		addToOccurList(1,addTC,col);
		OccurList.add(1-indexPC);for(int i=1-indexPC;i>0;i--)OccurList.add(col);
//		addToOccurList(1,1-indexPC,col);
	}
	private void fillOccurListForTResultsSeverityTable(int subrows,int col){
		OccurList.add(subrows);for(int i=subrows;i>0;i--)OccurList.add(col);
//		addToOccurList(1,subrows,col);
		OccurList.add(subrows);for(int i=subrows;i>0;i--)OccurList.add(col);
//		addToOccurList(1,subrows,col);
		OccurList.add(addTC);for(int j=addTC;j>0;j--){OccurList.add(subrows);for(int i=subrows;i>0;i--)OccurList.add(col);}
//		addToOccurList(addTC,subrows,col);
		OccurList.add(1-indexPC);for(int j=1-indexPC;j>0;j--){OccurList.add(subrows);for(int i=subrows;i>0;i--)OccurList.add(col);}
//		addToOccurList(1-indexPC,subrows,col);
	}
	private void addToOccurList(int a,int b,int c){
		OccurList.add(a);for(int j=a;j>0;j--){OccurList.add(b);for(int i=b;i>0;i--)OccurList.add(c);}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoadXMLServerTest A = new LoadXMLServerTest();		
		A.startSearch();
		while(A.findAppendTagContent("FileName"))A.findAppendTagContent("EncodedContent");
		A.printResult();
	}

}
