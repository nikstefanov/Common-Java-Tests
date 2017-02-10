import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.*;


public class PictureProperties {

	/**
	 * @param args
	 */
	

	    private void readAndDisplayMetadata(String fileName){
	        try {

	            File file = new File( fileName );
	            System.out.println("File:"+file.toString());
	            ImageInputStream iis = ImageIO.createImageInputStream(file);
	            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

	            if (readers.hasNext()) {
	            	
	                // pick the first available ImageReader
	                ImageReader reader = readers.next();

	                // attach source to the reader
	                reader.setInput(iis, true);
	                
	                System.out.println(reader.getHeight(0));

	                // read metadata of first image
	                IIOMetadata metadata = reader.getImageMetadata(0);

	                String[] names = metadata.getMetadataFormatNames();
	                int length = names.length;
	                for (int i = 0; i < length; i++) {
	                    System.out.println( "Format name: " + names[ i ] );
	                    displayMetadata(metadata.getAsTree(names[i]));
	                }
	            }
	        }
	        catch (Exception e) {

	            e.printStackTrace();
	        }
	    }

	    void displayMetadata(Node root) {
	        displayMetadata(root, 0);
	    }

	    void indent(int level) {
	        for (int i = 0; i < level; i++)
	            System.out.print("    ");
	    }

	    void displayMetadata(Node node, int level) {
	        // print open tag of element
	        indent(level);
	        System.out.print("<" + node.getNodeName());
	        NamedNodeMap map = node.getAttributes();
	        if (map != null) {

	            // print attribute values
	            int length = map.getLength();
	            for (int i = 0; i < length; i++) {
	                Node attr = map.item(i);
	                System.out.print(" " + attr.getNodeName() +
	                                 "=\"" + attr.getNodeValue() + "\"");
	            }
	        }

	        Node child = node.getFirstChild();
	        if (child == null) {
	            // no children, so close element and return
	            System.out.println("/>");
	            return;
	        }

	        // children, so close current tag
	        System.out.println(">");
	        while (child != null) {
	            // print children recursively
	            displayMetadata(child, level + 1);
	            child = child.getNextSibling();
	        }

	        // print close tag of element
	        indent(level);
	        System.out.println("</" + node.getNodeName() + ">");
	    }
	    
	   	public static void main(String[] args) {
				PictureProperties meta = new PictureProperties();
//		        int length = args.length;
//		        for ( int i = 0; i < length; i++ )
//		            meta.readAndDisplayMetadata( args[i] );
//		        	meta.readAndDisplayMetadata( "D:\\derfiles\\FSTRA2\\attachments\\cfb6322a-d12f-4b6e-9ec6-d73f214dc501" );
		        	meta.readAndDisplayMetadata("F:\\Nikolay Stefanov's Documents\\My Pictures\\smile2.jpg");
		    }


}

