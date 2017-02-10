import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;


public class DocxTest {
// Add a row in a table in a docx file.
	
	private  final String FilePathIn,FilePathOut;	
	private  String fileContentString,outString;
	private  final int buff_size;
	private  final char[] char_buff;
	
	public DocxTest(){
		 buff_size = 2048;
		 FilePathIn="D:\\Nikolay's Documents\\Eclipse workspaces\\xmlbiding\\AMA\\src\\com\\lmc\\der\\ama\\client\\StudyDesign.ui.xml";
		 FilePathOut="D:\\Liesmich PhysGUI1.txt";
		 fileContentString = null;
		 char_buff = new char[buff_size];
		 outString = null;
	}
	
	private void loadFromFile(){
		int count;
		StringWriter sw =  new StringWriter(buff_size);		
		try{
			BufferedReader buffIn = new BufferedReader(new FileReader(FilePathIn),buff_size);
			while((count=buffIn.read(char_buff, 0, buff_size))>0)
				sw.write(char_buff, 0, count);
			sw.flush();
			sw.close();
			fileContentString = sw.toString();
			buffIn.close();
		}catch(IOException ioe){
			 fileContentString = null;			 
		}
	}
	
	private void saveToFile(){
		int count;
		StringReader sr =  new StringReader(outString);		
		try{
			BufferedWriter buffOut = new BufferedWriter(new FileWriter(FilePathOut),buff_size);
			while((count=sr.read(char_buff, 0, buff_size))>0)
				buffOut.write(char_buff, 0, count);
			buffOut.flush();
			buffOut.close();
		}catch(IOException ioe){
			System.out.println("Problems w/ writing.");
		}
	}
	public void printContent(){
		loadFromFile();
		System.out.println(fileContentString);
	}
	public void copyFile(){
		loadFromFile();
		outString = fileContentString;
		saveToFile();
	}
	public void addRowToDocx(){
		loadFromFile();
	}
	public void findFields(){
		loadFromFile();
		int ind=0,k=0,i1,i2;String line = null;
		while((k=fileContentString.indexOf('\n', ind+1))>0){
			line = fileContentString.substring(ind+2, k);
//			System.out.println(ind);System.out.println(k);
			if ((i1=line.indexOf("ui:field"))>0&&line.indexOf("RichTextA")>0){
				i2=line.lastIndexOf('\'');
				System.out.print(line.substring(i1+10,i2)+',');
			}
			ind = k;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DocxTest dt = new DocxTest();
//		dt.printContent();
//		dt.copyFile();	
		dt.findFields();
		
	}

}
