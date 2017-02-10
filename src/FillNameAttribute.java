import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class FillNameAttribute {
	
	final ArrayList<String> TemplateFileNamesArray;
	final BufferedReader PathNamesReader;
	final String			TemplateDirName;
	int 					replaceCount,fileCount;
	
	private FillNameAttribute() throws IOException{
		replaceCount=0;
		TemplateDirName = "D:\\Nikolay Stefanov\'s Documents\\Archive\\";
		String OwnerClassString = FileUtils.readFileToString(
						new File("D:\\Shared Eclipse workspace\\SixComposersClient\\ExternalSrc\\com\\lmc\\der\\sixComposers\\client\\ui\\FSTRA_UI.java"), "US-ASCII");		
		int indexOwnerClass=0,startOfpath,endOfPath;
		TemplateFileNamesArray = new ArrayList<String>(8);
		String FileName;
		while ((indexOwnerClass=OwnerClassString.indexOf("@UiTemplate",indexOwnerClass))>=0){
			startOfpath =	OwnerClassString.indexOf('\"',indexOwnerClass);
			endOfPath = 	OwnerClassString.indexOf('\"',startOfpath+1);
			FileName = OwnerClassString.substring(startOfpath+1, endOfPath)
					.replace('/', '\\');
			TemplateFileNamesArray.add(FileName);
//			System.out.println("--- "+FileName);
					
			indexOwnerClass = endOfPath+1;
		}
		
		final String PathNamesFileName = 
				"F:\\Work\\DER\\FieldPaths\\FSTRAFieldPaths2.txt";
		PathNamesReader = new BufferedReader(new StringReader( 
				FileUtils.readFileToString(new File(PathNamesFileName), "US-ASCII")));
	}

	private int  iterateTemplateFiles()throws IOException{
		for(String TemplateFileName:TemplateFileNamesArray){
			fileCount=0;		
			replaceFileContent(TemplateDirName+TemplateFileName);
			System.out.println(TemplateFileName+' '+fileCount);
		}
		System.out.println("Files: "+TemplateFileNamesArray.size());
		return replaceCount;		
	}
	
	private void replaceFileContent(String TemplateFileName) throws IOException{
//		System.out.println(TemplateFileName);
		File TemplateFile = new File(TemplateFileName);
		final StringBuilder fileContentSB =
				new StringBuilder(FileUtils.readFileToString(TemplateFile, "US-ASCII"));
		
		String Path;/*
		while ((indexSB=fileContentSB.indexOf("name=\"\"", indexSB))>=0){
			Path = PathNamesReader.readLine();
			fileContentSB.replace(indexSB, indexSB+7, 
					"name=\""+ Path +'\"');
			indexSB+= Path.length()+7;
			replaceCount++;
			fileCount++;
		}*/
		int indexSB=1,index2=0;
		int commentStart=0,nameClause=0;
		while(indexSB>0 && indexSB<fileContentSB.length()){
			if (commentStart==0 && fileContentSB.charAt(indexSB)=='<') {commentStart = 1;nameClause = 0;}				
			else if (commentStart == 1 && fileContentSB.charAt(indexSB)=='!') commentStart=2;				
			else if (commentStart == 2 && fileContentSB.charAt(indexSB)=='-') commentStart=3;
			else if (commentStart == 3 && fileContentSB.charAt(indexSB)=='-')
				{index2 = fileContentSB.indexOf("-->", indexSB)+2;commentStart=0;
//				System.out.println(fileContentSB.substring(indexSB-3,index2+3));
				indexSB=index2+2;}
			else if (nameClause==0 &&fileContentSB.charAt(indexSB)=='n') {nameClause = 1;commentStart = 0;}
			else if (nameClause==1 && fileContentSB.charAt(indexSB)=='a') nameClause = 2;
			else if (nameClause==2 && fileContentSB.charAt(indexSB)=='m') nameClause = 3;
			else if (nameClause==3 && fileContentSB.charAt(indexSB)=='e') nameClause = 4;
			else if (nameClause==4 && fileContentSB.charAt(indexSB)=='=') nameClause = 5;
			else if (nameClause==5 && fileContentSB.charAt(indexSB)=='\"') nameClause = 6;
			else if (nameClause==6 && fileContentSB.charAt(indexSB)=='\"'){
				Path = PathNamesReader.readLine();
				fileContentSB.insert(indexSB, Path);
				indexSB+= Path.length();
				replaceCount++;
				fileCount++;
			}				
			else {nameClause = 0;commentStart=0;}			
			indexSB++;
		}
		
//		FileUtils.writeStringToFile(TemplateFile, fileContentSB.toString(), "US-ASCII");
	}
	
	public static void main(String[] args) throws IOException{
		FillNameAttribute FNA = new FillNameAttribute();
		System.out.println("Replacments: "+FNA.iterateTemplateFiles());
	}
	
}
