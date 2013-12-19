package i5.las2peer.services.iStarMLVisualizerService;

import java.awt.Color;
//import java.util.HashMap;

import i5.las2peer.api.Service;
import i5.las2peer.util.simpleRESTMapper.RESTMapper;
import i5.las2peer.util.simpleRESTMapper.Annotations.ContentParam;
import i5.las2peer.util.simpleRESTMapper.Annotations.DefaultValue;
import i5.las2peer.util.simpleRESTMapper.Annotations.GET;
import i5.las2peer.util.simpleRESTMapper.Annotations.POST;
import i5.las2peer.util.simpleRESTMapper.Annotations.Path;
import i5.las2peer.util.simpleRESTMapper.Annotations.QueryParam;


public class IStarMLVisualizerService extends Service
{
	private static final String OK = "OK";
	private RESTMapper mapper;
	public IStarMLVisualizerService()
	{
		initMapper();
	}
	private void initMapper() 
	{
		if(mapper==null)//not yet initialized
		{
			mapper=new RESTMapper(this.getClass());
		}		
	}
	public void cleanUp()
	{
		
	}
	
	@POST
	@Path("")
	public String createVisualization(@DefaultValue("0") @QueryParam("nr") int r1, @DefaultValue("73") @QueryParam("ng") int g1, @DefaultValue("199") @QueryParam("nb") int b1, @DefaultValue("255") @QueryParam("lr")int r2, @DefaultValue("255") @QueryParam("lg") int g2, @DefaultValue("255") @QueryParam("lb") int b2, @ContentParam() String xml) throws Exception
	{		
		//clamp, to be on the safe side
		try
		{
			r1=Util.clamp(r1,0,255);
			r2=Util.clamp(r2,0,255);
			g1=Util.clamp(g1,0,255);
			g2=Util.clamp(g2,0,255);
			b1=Util.clamp(b1,0,255);
			b2=Util.clamp(b2,0,255);
			
			IStarMLParser parser = new IStarMLParser();
		    parser.loadIStarML(xml);	
		   
		    IStarMLGraphCreator creator = new IStarMLGraphCreator(new Color(r1,g1,b1), new Color(r2,g2,b2));   
		    String svgGraphString = creator.createSVG(parser.getNodes(), parser.getEdges());
		    return svgGraphString;			
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		
	}
	@GET
	@Path("")
	public String loginCheck()
	{
		return OK;
	}
	public String restDecoder(String method, String URI, String[][] variables, String content)
	{
		String response="";
		try 
		{
			
			response=mapper.parse(this, method.toLowerCase(), URI,variables,content);//here the mapping magic happens
		} 
		catch (Throwable e) 
		{			
			response="Error: "+e.getClass().getName()+" "+e.getMessage();
		}
		return response;
		/*String result="";
		try
		{
			HashMap<String, Integer> vars= new HashMap<String, Integer>();
			if(variables!=null)
			{
				for (int i = 0; i < variables.length; i++) {
					vars.put(variables[i][0].toLowerCase(), Integer.valueOf(variables[i][1]));
				}
			}			
			
			
			if(!content.trim().isEmpty())
			{
				if(method.toLowerCase().equals("post"))
				{
					
					if(vars.size()>=6)
					{						
						int nr=vars.get("nr");
						int ng=vars.get("ng");
						int nb=vars.get("nb");
						int lr=vars.get("lr");
						int lg=vars.get("lg");
						int lb=vars.get("lb");
						result=createVisualizationColored(nr,ng,nb,lr,lg,lb,content);
					}
					else
					{
						result=createVisualization(content);
					}					
				}
			}
			else//Login check
			{
				result="ok";
			}
		}
		catch (Exception e)
		{
			result= "Error: "+e.getClass().getSimpleName()+" : " +e.getMessage();			
		}
		
		return result;*/
	}
}
