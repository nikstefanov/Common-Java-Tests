import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoadXMLServer2Test {

	private StringBuilder sb;
	private String XMLString;
	private int index;
	
	private LoadXMLServer2Test(){
		sb = new StringBuilder();
		loadFile("D:\\Nikolay's Documents\\Downloads\\__890.1100(5).xml",sb);
		XMLString = sb.toString();
		sb = new StringBuilder();
		index = 0;
	}		
	
	private void loadTest(){
		/*
		String Tag;
		index = XMLString.indexOf('<', index); 
		while((Tag=findTag())!=null){
			
		}*/
		Pattern Ptn = Pattern.compile("<([A-Z][A-Za-z]+)>([^<>\"\']*)</\\1>");
		Matcher Math = Ptn.matcher(XMLString);
		int i=0;
		while(Math.find())
				if(!Math.group(1).equals("Text") && !Math.group(1).equals("XSDVersion")){
					System.out.println(Math.group(1)+"  "+Math.group(2)+"  "+i++);
		}
	}
	private String findTag(){
		int openIndex =  index;
		if (openIndex>=0){ 
			index = XMLString.indexOf('>', openIndex);
			return XMLString.substring(openIndex+1, index);
		}else
			return null;
	}
	private String getContent(){
		int beginOfContent = index+1;
		index = XMLString.indexOf('<', index+1);
		return XMLString.substring(beginOfContent, index); 
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoadXMLServer2Test A= new LoadXMLServer2Test();
		A.loadTest();

	}

}
