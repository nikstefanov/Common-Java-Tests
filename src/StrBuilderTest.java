import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang3.text.StrBuilder;


public class StrBuilderTest {
	private final int buff_size = 4096;
	private 	  int count=0;
	private final char[] char_buff;
	private final byte[]data;
	private final StrBuilder SB;
	private final String xmlTemplatePath="D:\\derfiles\\AMA\\DOC\\xmlTemplate\\word\\document.xml";
	private final String xmlGeneratedPath="D:\\Nikolay's Documents\\DER\\FSTRA\\ExSum1.html";
			//"D:\\derfiles\\AMA\\DOC\\xmlGenerated\\document.xml";
	private final String docxGeneratedDir="D:\\derfiles\\AMA\\DOC\\docxGenerated\\";
	private final String xmlTemplateStaticDir="D:\\derfiles\\AMA\\DOC\\xmlTemplateStatic\\";
	
	public StrBuilderTest(){
		char_buff = new char[buff_size];
		data =   new byte[buff_size];
		SB = new StrBuilder(buff_size);
	}
	
	private void loadFile(String FilePathIn){    		
		try{
			BufferedReader buffIn = new BufferedReader(new FileReader(FilePathIn),buff_size);
			while((count=buffIn.read(char_buff, 0, buff_size))>0){
//				System.out.println(count);
				SB.append(char_buff, 0, count);	
			}
//			fileContentString = sw.toString();
			buffIn.close();
		}catch(IOException ioe){
			 ioe.printStackTrace();
			 System.out.println("IOExcetion!!");
		}
    }
	private void saveFile(){		
		try{
			BufferedWriter buffOut = new BufferedWriter(new FileWriter(xmlGeneratedPath),buff_size);
			BufferedReader buffIn  = new BufferedReader(SB.asReader(),buff_size);
			while((count=buffIn.read(char_buff, 0, buff_size))>0){
//				System.out.println(count);
				buffOut.write(char_buff, 0, count);	
			}
			buffIn.close();
			buffOut.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public void printContent(){
		loadFile(xmlTemplatePath);
		System.out.println(SB.toString());
	}
	public void removeLines(){
		loadFile(xmlTemplatePath);
		int ind1=0,ind2=0,ind3=0;String A;
		while((ind1=SB.indexOf("<w:tr ",ind1))>0&&(ind2=SB.indexOf("</w:tr>",ind1))>0){
			A = SB.substring(ind1, ind2);
//			System.out.println("i1 "+ind1);System.out.println("i2 "+ind2);
//			if ((A.indexOf("Test concentration 1")>0)||
//					(A.indexOf("Test concentration 2")>0)||
//					(A.indexOf("Test concentration 3")>0)){
			if (A.indexOf("Test")>0){
				SB.delete(ind1, ind2+7);
//				ind3++;
				System.out.println(A);
			}else{
				ind1=ind2+7;
			}
		}
//		saveFile();
//		System.out.println(ind3);
	}
	
	public void findTRtags(){
		loadFile(xmlTemplatePath);
		int ind1=0,ind2=0,ind3=0;String A;
		while((ind1=SB.indexOf("<w:tr ",ind1))>0&&(ind2=SB.indexOf("</w:tr>",ind1))>0){
			A = SB.substring(ind1, ind2+1);
//			System.out.println(A);
//			if ((A.indexOf("Test concentration 1")>0)||
//					(A.indexOf("Test concentration 2")>0)||
//					(A.indexOf("Test concentration 3")>0)){
			if (A.indexOf("Test")>0){
//				SB.delete(ind1, ind2+7);
////				ind3++;
				System.out.println(A.substring(0, 60));
			}
//				else{
			ind1=ind2+1;
//			}
		}
//		saveFile();
//		System.out.println(ind3);
	}
	public String generateDocx(){
    	try {
    		loadFile(xmlTemplatePath);
    		//generate random name
    		UUID uuid = UUID.randomUUID();
			FileOutputStream fos = new FileOutputStream(docxGeneratedDir+uuid.toString() + ".docx");
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos,buff_size));
			zos.setMethod(ZipOutputStream.DEFLATED);
			zos.setLevel(Deflater.DEFAULT_COMPRESSION);
			zipping(xmlTemplateStaticDir,"",zos);
			
			zos.putNextEntry(new ZipEntry("word\\document.xml"));
			ReaderInputStream origin = new ReaderInputStream(SB.asReader(),"UTF-8",buff_size);						
            while((count = origin.read(data, 0, buff_size)) != -1) {
               zos.write(data, 0, count);
            }
            origin.close();			
			zos.close();
			return uuid.toString() + ".docx";
	      }
		catch(IOException ioe){
			ioe.printStackTrace();
			return "";
		}
    }
    
  //Special care taken currentFileRelativePath not to start with '\'
    private void zipping(String targetDirAbsolutePath,
			String currentFileRelativePath,ZipOutputStream zos)throws IOException{		
		File currentFile = new File(targetDirAbsolutePath+currentFileRelativePath);
		if (currentFile.isDirectory()){
			if(!currentFileRelativePath.isEmpty()){
				zos.putNextEntry(new ZipEntry(currentFileRelativePath+'/'));
				currentFileRelativePath = currentFileRelativePath+'\\';
			}			
			for (String fileName : currentFile.list()){
				zipping(targetDirAbsolutePath,currentFileRelativePath+fileName,zos);
			}
		}
		else{
			zos.putNextEntry(new ZipEntry(currentFileRelativePath));
			FileInputStream fis = new FileInputStream(currentFile);
			BufferedInputStream origin = new BufferedInputStream(fis, buff_size);				
            while((count = origin.read(data, 0, buff_size)) != -1) {
               zos.write(data, 0, count);
            }
            origin.close();
//          zos.flush();
            zos.closeEntry();

		}
	}
    
    public void htmlTagging(){
    	loadFile("D:\\Nikolay's Documents\\DER\\FSTRA\\ExSum1.txt");//"D:\\Nikolay's Documents\\DER\\FSTRA\\ExSum.txt"
    	/*
    	SB.replaceAll("   [", "[");
    	SB.replaceAll("  [", "[");
    	SB.replaceAll(" [", "[");
    	SB.replaceAll("]   ", "[");
    	SB.replaceAll("]  ", "]");
    	SB.replaceAll("] ", "]");
    	*/
    	SB.replaceAll((new Character('[')).toString(),"\r\n<span style=\"color:firebrick\">[");
    	SB.replaceAll((new Character(']')).toString(),"]</span>\r\n");
    	saveFile();//"D:\\Nikolay's Documents\\DER\\FSTRA\\ExSum1.txt";
    }
    public void uiFieldsPrinter(String filename){
    	loadFile(filename);    	
    	System.out.println(uiFieldsFinder("<g:TextBox"));    	
    	System.out.println(uiFieldsFinder("<g:TextArea"));    	
    	System.out.println(uiFieldsFinder("<p:YNListBox"));
    	System.out.println(uiFieldsFinder("<g:ListBox"));
    	System.out.println(uiFieldsFinder("<g:RichTextArea"));
    	
    }
    
    private String uiFieldsFinder(String type){
    	int typeIndex=0,fieldStart,fieldEnd;
    	String A=type+"\r\n"; 
    	while((typeIndex=SB.indexOf(type, typeIndex+1))!=-1){    	
    		fieldStart=SB.indexOf("ui:field", typeIndex);
    		fieldStart=SB.indexOf('\'', fieldStart+1);
    		fieldEnd=SB.indexOf('\'', fieldStart+1);
    		A=A+(SB.substring(fieldStart+1, fieldEnd)+"\t\t,\r\n");    		
    	}
    	return A;
    }
    
    private void findNonASCIISymbols(String filePath){
    	loadFile(filePath);
    	int i=SB.length();
    	char sym;
    	for(int j=0;j<i;j++){
    		if ((sym = SB.charAt(j))>127){
    			System.out.println(j+": "+sym);
    		}
    	}
    }
	
	public static void main(String[] args){
		StrBuilderTest sbt = new StrBuilderTest();
//		sbt.printContent();
//		sbt.removeLines();
//		sbt.findTRtags();
//		sbt.htmlTagging();
//		sbt.uiFieldsPrinter("D:\\Nikolay's Documents\\Eclipse workspaces\\xmlbiding\\DERs\\src\\com\\lmc\\der\\ders\\client\\AStudyDesign.ui.xml");
//		sbt.findNonASCIISymbols("D:\\derfiles\\AMA2\\XML\\Empty.ama");
		sbt.findNonASCIISymbols("D:\\Nikolay's Documents\\Downloads\\__890.1100.ama");
	}
}
