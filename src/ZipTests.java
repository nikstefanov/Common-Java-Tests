import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;
//import java.nio.file;

public class ZipTests {

	private static final int BUFFER = 4096 * 1024;
	private static final byte[]data = new byte[BUFFER];
	private static final String inputFilePath = "D:\\Nikolay's Documents\\WordTest2.docx"; 
			//"D:\\derfiles\\DERs\\DOC\\fstra.zip";
			// "D:\\Nikolay's Documents\\DER\\FSTRA\\1350-fish-short-term-template-edited.docx";
			// "D:\\Nikolay's Documents\\Downloads\\dbeb315c-16a2-4181-902c-c6d43a1bac17.docx";
	private static final String outputFilePath = "D:\\Nikolay's Documents\\DER\\FSTRA\\Result.zip";
	private static final String addFilePath = "D:\\derfiles\\AMA\\DOC\\word\\Contents.htm";
	
	public static void main(String[] args) {
//		entriesCount();
//		copyArchive();
		zipFolderContent("D:\\Nikolay's Documents\\DER\\FSTRA\\docx");
	}
	// not working
	public static void test1(){
		try {
	         BufferedInputStream origin = null;
	         File outputFile = new File(outputFilePath);
	         long outpuFileLength = outputFile.length();
	         FileOutputStream dest = new 
	           FileOutputStream(outputFile);
	         ZipOutputStream out = new ZipOutputStream(new 
	           BufferedOutputStream(dest));
	         //out.setMethod(ZipOutputStream.DEFLATED);
	         byte data[] = new byte[BUFFER];
//	         // get a list of files from current directory
//	         File f = new File(".");
//	         String files[] = f.list();
//
//	         for (int i=0; i<files.length; i++) {
	            System.out.println("Adding: "+addFilePath);
	            FileInputStream fi = new 
	              FileInputStream(addFilePath);
	            origin = new 
	              BufferedInputStream(fi, BUFFER);
	            ZipEntry entry = new ZipEntry("word\\Contents.htm");
	            out.putNextEntry(entry);
	            int count;
	            while((count = origin.read(data, 0, 
	              BUFFER)) != -1) {
	               out.write(data, (int)outpuFileLength, count);
	            }
	            origin.close();
//	         }
	         out.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }

		
	}
	// not working
	public static void entriesCount(){
		ZipEntry entry;
        ZipFile zipfile;
        try{
        	zipfile = new ZipFile(inputFilePath);
            Enumeration<? extends ZipEntry> e = zipfile.entries();
            while(e.hasMoreElements()) {
            	entry = (ZipEntry) e.nextElement();
            	System.out.println("Extracting: " +entry);
            }
        }
        catch(IOException ioe){
        	ioe.printStackTrace();
        }

	}
	// not working
	public static void copyArchive(){
		try{
		int count;
		ZipEntry entry = null;
		FileOutputStream fos = new FileOutputStream(outputFilePath);
		FileInputStream fis = new FileInputStream(inputFilePath);
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos,BUFFER));
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis,BUFFER));
		zos.setMethod(ZipOutputStream.DEFLATED);
		zos.setLevel(Deflater.DEFAULT_COMPRESSION);
		while((entry = zis.getNextEntry())!=null){
			zos.putNextEntry(entry);
			while ((count = zis.read(data, 0, BUFFER)) > 0) {
					zos.write(data, 0, count);
		            }
			zos.flush();
			zos.closeEntry();
		}
		zos.close();
		zis.close();
		fis.close();
		fos.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static void zipFolderContent(String dirAbsolutePath){
			
		try {
			FileOutputStream fos = new FileOutputStream(outputFilePath);
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos,BUFFER));
			zos.setMethod(ZipOutputStream.DEFLATED);
			zos.setLevel(Deflater.DEFAULT_COMPRESSION);
			zipping(dirAbsolutePath,"",zos);
			zos.close();
	      }
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	//Special care taken currentFileRelativePath not to start with '\'
	private static void zipping(String targetDirAbsolutePath,
			String currentFileRelativePath,ZipOutputStream zos)throws IOException{		
		File currentFile = new File(targetDirAbsolutePath+currentFileRelativePath);
		if (currentFile.isDirectory()){
//			if(!currentFileRelativePath.isEmpty()){
//				zos.putNextEntry(new ZipEntry(currentFileRelativePath+'/'));
//				currentFileRelativePath = currentFileRelativePath+'\\';
//			}			
			for (String fileName : currentFile.list()){
				zipping(targetDirAbsolutePath,currentFileRelativePath+'\\'+fileName,zos);
			}
		}
		else{
			zos.putNextEntry(new ZipEntry(currentFileRelativePath.substring(1).replace('\\', '/')));
			FileInputStream fis = new FileInputStream(currentFile);
			BufferedInputStream origin = new BufferedInputStream(fis, BUFFER);
			int count;			
            while((count = origin.read(data, 0, BUFFER)) != -1) {
               zos.write(data, 0, count);
            }
            origin.close();
//          zos.flush();
            zos.closeEntry();

		}
	}
}
