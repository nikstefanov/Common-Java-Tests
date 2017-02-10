import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.common.byteSources.ByteSourceInputStream;


public class Sanselan_Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			FileInputStream fis = new FileInputStream("F:\\Nikolay Stefanov's Documents\\My Pictures\\Globe.ico");
			ByteSourceInputStream bsis = new ByteSourceInputStream(fis,null);
			ImageFormat ifrm = Sanselan.guessFormat(bsis);
			System.out.println(ifrm.toString());
			ImageInfo picInfo = Sanselan.getImageInfo(bsis.getInputStream(),null);
			System.out.println(picInfo.toString());
			Dimension d=Sanselan.getImageSize(bsis, null);
			System.out.println(d.toString());
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(ImageReadException ire) {
			ire.printStackTrace();
		}
	}

}
