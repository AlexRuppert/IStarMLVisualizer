package i5.las2peer.services.iStarMLVisualizerService;


import i5.las2peer.httpConnector.HttpConnector;

import i5.las2peer.p2p.LocalNode;
import i5.las2peer.security.ServiceAgent;
import i5.las2peer.security.UserAgent;
import i5.las2peer.testing.MockAgentFactory;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;


import org.junit.After;
import org.junit.Before;



public class IStarMLVisualizerServiceTest {

	//private static final String HTTP_ADDRESS = "localhost";
	//private static final int HTTP_PORT = HttpConnector.DEFAULT_HTTP_CONNECTOR_PORT;

	private LocalNode node;
	private HttpConnector connector;
	private ByteArrayOutputStream logStream;
	private UserAgent adam = null;
	//private static final String testPass = "adamspass";
	
	private static final String testServiceClass = "i5.las2peer.services.iStarMLVisualizerService.IStarMLVisualizerService";
	
	private static String _basePath="./testXMLFiles/";
	
	public static String getFile(String path)throws Exception 
	{		   
		   File file = new File(_basePath+path+".xml"); //for ex foo.txt
		  
		    FileInputStream fis = new FileInputStream(file);
		    byte[] data = new byte[(int)file.length()];
		    fis.read(data);
		    fis.close();
		    //
		    String s = new String(data, "UTF-8");
		   return s;
	}
	
	@Before
	public void startServer() throws Exception {
		// start Node
		node = LocalNode.newNode();
		adam=MockAgentFactory.getAdam();
		node.storeAgent(adam);
		node.launch();

		ServiceAgent testService = ServiceAgent.generateNewAgent(
				testServiceClass, "a pass");
		testService.unlockPrivateKey("a pass");

		node.registerReceiver(testService);
		// start connector
		logStream = new ByteArrayOutputStream();
		connector = new HttpConnector();
		connector.setSocketTimeout(10000);
		connector.setLogStream(new PrintStream(logStream));
		connector.start(node);

	}

	@After
	public void shutDownServer() throws Exception {
		connector.stop();
		node.shutDown();

		connector = null;
		node = null;

		LocalNode.reset();

		System.out.println("Connector-Log:");
		System.out.println("--------------");

		System.out.println(logStream.toString());
	}
	
	
	/*@Test
	public void testSimpleCall() {
		Client c = new Client(HTTP_ADDRESS, HTTP_PORT, "adam", testPass);
		//Client c = new Client(HTTP_ADDRESS, HTTP_PORT, "UserA", "userAPass");

		try {
			c.connect();
			
			
			
			Object result = c.invoke(testServiceClass, "createVisualization", getFile("testFile1"));
			System.out.println(result);
			
			//c.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception: " + e);
		}
	}*/

}
