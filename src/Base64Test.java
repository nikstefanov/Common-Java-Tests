/**
 * 
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

//import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Nikolay
 *
 */
public class Base64Test {

	//reads the image from file and produces a Base64 encoded string
	private static String EncodeImage(String filename){
		try{
			File inputFile = new File(filename);
			byte [] byteArr = new byte[(int)inputFile.length()];
			FileInputStream fis = new FileInputStream(inputFile);
			if (fis.read(byteArr)!=byteArr.length)
				throw new IOException("Not all bytes were read.");
			fis.close();
			// return Base64.encodeBase64String(byteArr);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStream b64os = MimeUtility.encode(baos, "base64");
			b64os.write(byteArr);
			b64os.close();
			// return baos.toByteArray();
			return new String(baos.toByteArray());
		}
		catch(IOException ioe){
			System.out.println(ioe.toString());
			return null;
		}

	}

	//decodes the Base64 encoded imgString and writes the file to the filesystem
	//returns the name of the file
	private static String DecodeImage(String imgString){
		UUID uuid = UUID.randomUUID();
		String filename = "D:\\Nikolay\'s Documents\\Test\\"+uuid.toString() + ".png";
		try{			
			FileOutputStream fos = new FileOutputStream(filename);
			byte [] byteArr = Base64.decodeBase64(imgString);
			fos.write(byteArr,0,byteArr.length);				
			fos.close();			
		}
		catch(IOException ioe){
			System.out.println(ioe.toString());
			filename = null;
		}
		return filename;
	}

	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int fileSize;
		char [] charArr;
		String B64S = Base64Test.EncodeImage("D:\\Nikolay\'s Documents\\Test\\fffc8657-e07f-499c-98c2-1c284c7ee06b.png");
		try{
			FileWriter fw = new FileWriter("D:\\Nikolay\'s Documents\\Test\\fffc8657-e07f-499c-98c2-1c284c7ee06b.base64.txt"); 
			fw.write(B64S, 0, B64S.length());
			fw.close();
			
			File file = new File("D:\\Nikolay\'s Documents\\Test\\fffc8657-e07f-499c-98c2-1c284c7ee06b.base64.txt");
			fileSize = (int)file.length();
			charArr = new char[fileSize];
			FileReader fr = new FileReader(file);
			fr.read(charArr,0,fileSize);
		}
		catch(IOException ioe){
			System.out.println(ioe.toString());
			charArr = null;
		}
		Base64Test.DecodeImage(new String(charArr));
	}

}
