import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.fileupload.util.Streams;


public class AddTildasToDocx {

//	private int [][] tildaNumbers = {{1,2},{2,2},{12,4},{2,1},{38,4},{4,4},{3,4},{1,45},{2,2}};//mpa
	private int [][] tildaNumbers = {{1,2},{2,2},{13,4},{26,4},{1,9},{3,4},{3,4},{1,45},{2,2}};//fpa
//	private StringBuilder commonCB = new StringBuilder("<!-- -->");
	
	private void doSubstitute() throws IOException{
		StringBuilder SB = new StringBuilder(loadFile("D:\\Nikolay Stefanov's Documents\\DER\\docx\\1450-fpa7\\word\\document.xml"));
		int indexSB = 0;
		StringBuilder CSB;
//		while ((indexSB = SB.indexOf("^"))>0)
		for (int i =0;i<tildaNumbers.length;i++)
			for (int j=0;j<tildaNumbers[i][0];j++){
				indexSB = SB.indexOf("^",indexSB);
				SB.replace(indexSB, indexSB+1, "~");
				indexSB+=26;
				CSB = buildComment(tildaNumbers[i][1]);				
				SB.insert(indexSB, CSB);
				indexSB+=CSB.length();
			}
		saveFile(SB);
	}
	
	private StringBuilder buildComment(int tildas){
		StringBuilder CSB = new StringBuilder("<!-- -->");
		while (tildas-->0) CSB.insert(4, " ~");
		return CSB;		
	}
	
	private String loadFile(String FilePathIn)throws IOException{
		FileInputStream fis = new FileInputStream(FilePathIn);
		return Streams.asString(fis, "UTF-8");
	}
	private void saveFile(StringBuilder SB)throws IOException{
		FileOutputStream fos = new FileOutputStream("D:\\Nikolay Stefanov's Documents\\DER\\docx\\1450-fpa7\\word\\document2.xml");
		fos.write(SB.toString().getBytes("UTF-8"));
		fos.close();
	}
	public static void main(String[] args) {
		AddTildasToDocx A = new AddTildasToDocx();
		try{
			A.doSubstitute();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}

	}

}
