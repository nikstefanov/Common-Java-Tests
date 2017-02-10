import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;


public class SerchingDuplicateElementsInXSD {

	private  final String XSDPath;
	private  BufferedReader bread;
	private  final StringWriter sw;
	private  String contentXSD;
	private  final int buff_size;
	private  final char[] char_buff;
	private final ArrayList<String> Elements;
	
	 public SerchingDuplicateElementsInXSD(){
		 buff_size = 2048;
		 XSDPath="F:\\Work\\DER\\Schemas\\AMA8.xsd";
		 contentXSD = null;
		 char_buff = new char[buff_size];
		 sw = new StringWriter(buff_size);
		 Elements = new ArrayList<String>(150);
		 try{
			 bread = new BufferedReader(new FileReader(XSDPath),buff_size);
		 }catch(FileNotFoundException ioe){
			 bread=null;
		 }		 
	}

	
	private void search(){
		loadFromFile();
		if (contentXSD.equals(null))
			return;
//		System.out.println(contentXSD);
		int index1=0,index2=0,i=0;
		String ElementName;
		while((index1=contentXSD.indexOf("<xs:element", index2))>0){
			index1=contentXSD.indexOf('\"', index1);
			index2=contentXSD.indexOf('\"', index1+1);
			ElementName = contentXSD.substring(index1+1, index2);			
			if(Elements.contains(ElementName))
				System.out.println(ElementName);
			else
				Elements.add(ElementName);
//			i++;
		}
//		System.out.println(i);
		
	}
	
	private void loadFromFile(){
		if (bread.equals(null))
			return;
		int count;
		try{
			while((count=bread.read(char_buff, 0, buff_size))>0)
				sw.write(char_buff, 0, count);
			sw.flush();
			sw.close();
			contentXSD = sw.toString();
			bread.close();
		}catch(IOException ioe){
			 contentXSD = null;			 
		}
	}
	public static void main(String[] args) {
		SerchingDuplicateElementsInXSD obj = new SerchingDuplicateElementsInXSD();
		obj.search();		
	}
}
