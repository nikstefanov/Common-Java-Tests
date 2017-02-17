package in_jar_uri;

import java.io.InputStream;



public class In_Jar_Uri {
	
	
	public void method() throws Exception{
		//URL resUrl = this.getClass().getClassLoader().getResource("in_jar_uri/Message.txt");
		//System.out.println(resUrl.toString());
		//System.out.println(new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(resUrl.toURI())),"UTF-8"));
		InputStream in;
		byte[] b = new byte[100];
		in = this.getClass().getClassLoader().getResourceAsStream("in_jar_uri/Message.txt");
		in.read(b);
		System.out.println((new String(b)));
	}

	public static void main(String[] args) throws Exception{
		//URI fileUri = new File("in_jar_uri/Message.txt").toURI();		//System.out.println(fileUri.toString());
		//System.out.println(new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(fileUri)),"UTF-8"));
//		System.out.println(
//				new String(
//						java.nio.file.Files.readAllBytes(
//								java.nio.file.Paths.get("Message.txt","")
//						),
//						"UTF-8"
//				)
//		);
				
		(new In_Jar_Uri()).method();
	}

}
