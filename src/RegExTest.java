import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegExTest {

	private static void loadFile(String FilePathIn,StringBuilder sb){    		
		int count,buff_size=1024;
		char[] char_buff = new char[buff_size];
		try{
			BufferedReader buffIn = new BufferedReader(new FileReader(FilePathIn),buff_size);
			while((count=buffIn.read(char_buff, 0, buff_size))>0){
//				System.out.println(count);
				sb.append(char_buff, 0, count);	
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
	Pattern Ptn = Pattern.compile("");
	StringBuilder sb=new StringBuilder();
	loadFile("D:\\Nikolay's Documents\\Downloads\\00_99_890.1100(5).xml",sb);
	/*
	Matcher Math = Ptn.matcher("<PurityGI></PurityGI>"+
		"<Toxicity>TGAI</Toxicity>"+
		"<EPADPBarcode></EPADPBarcode>"+
		"<EPAMRID>99</EPAMRID>");*/
	Matcher Math = Ptn.matcher(sb);
	findAppendTagContent("Toxicity",Math);
	findAppendTagContent("EPADPBarcode",Math);
	findAppendTagContent("EPAMRID",Math);

	}
	static private boolean findAppendTagContent(String Tag,Matcher Math){
		String piece;
		Pattern Ptn = Pattern.compile('<'+Tag+">([^<>\"\']*?)</"+Tag+'>', Pattern.DOTALL);
		System.out.println(Ptn.pattern());
    	Math.usePattern(Ptn);
    	if (Math.find()){
    		piece = Math.group(1);
    		System.out.println("Piece: "+piece);
//    		sb.append(piece).append(delimiter);
    		return true;
    	}else
    		return false;
	}

}
