package i5.las2peer.services.iStarMLVisualizerService;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;





import org.junit.Test;





public class teststuff {

	//private static String _basePath="./testXMLFiles/";
	public static String getFile(String path)throws Exception 
	{		   
		File file = new File(path);		  
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int)file.length()];
		fis.read(data);
		fis.close();
		String s = new String(data, "UTF-8");
		   return s;
	}
	public static void saveFile(String path,String text)throws Exception 
	{		   
		File file = new File(path);
		FileOutputStream fop = new FileOutputStream(file);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		// get the content in bytes
		byte[] contentInBytes = text.getBytes();

		fop.write(contentInBytes);
		fop.flush();
		fop.close();
	}
	@Test
	public void test() throws Exception {
		String s=getFile("T:/test.xml");
		IStarMLVisualizerService service= new IStarMLVisualizerService();
		String result=service.createVisualization(255,255,255,0,0,0,s);
		//System.out.println(result);
		saveFile("T:/test.svg",result);
	}

}
