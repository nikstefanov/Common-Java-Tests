import java.io.IOException;
import java.util.Properties;

public class DERProperties {

	private static Properties properties = new Properties();
	private static String storageRoot;
	
	private static String readStorageRoot() {
		try {
			//read the composers.properties file from the default classpath (in our case $Apache$\Tomcat 7.0\lib)
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("composers.properties"));
			storageRoot = properties.getProperty("StorageRoot");
		} catch (IOException ex) {
			ex.printStackTrace();
			storageRoot = "";
		}
		return storageRoot;
	}
	
	public static String getStorageRoot() {
		if (storageRoot != null && !storageRoot.isEmpty()) {
			return storageRoot;
		} else {
			return readStorageRoot();
		}
	}
	
	public static void main(String[] args){
		System.out.println("Test:"+readStorageRoot());
	}
}
