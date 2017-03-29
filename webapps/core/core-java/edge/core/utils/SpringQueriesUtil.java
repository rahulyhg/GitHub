package edge.core.utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SpringQueriesUtil implements InitializingBean{

	private Resource[] locations;
	private static HashMap<String, String> queriesMap =  new HashMap<String, String>();
	
	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}
	
	public void setLocation(Resource location) {
		this.locations = new Resource[] {location};
	}
	
	private void processResources() throws Exception {      
        for(Resource location : locations){
        	processResource(location);
        }
    }

    private void processResource(Resource location) throws Exception {
    	FileInputStream file = new FileInputStream(location.getFile());        
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();         
        DocumentBuilder builder =  builderFactory.newDocumentBuilder();         
        Document xmlDocument = builder.parse(file);
        
        NodeList nodeList = xmlDocument.getElementsByTagName("query");
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList childNodes = nodeList.item(i).getChildNodes();
            String key = null;
            String value = null;
            for (int j = 0; j < childNodes.getLength(); j++) {        	   
    	        Node nod = childNodes.item(j);
    	        if(nod.getNodeType() == Node.ELEMENT_NODE){
    	        	if("key".equals(nod.getNodeName())){
    	        		key = nod.getFirstChild().getNodeValue();
    	        	}else if("value".equals(nod.getNodeName())){
    	        		value = nod.getFirstChild().getNodeValue();
    	        	}
    	        }
            }
            if(key != null){
            	queriesMap.put(key,value);
            }            
        }
	}

	public static String getQuery(String key) {
        return queriesMap.get(key);
    }
	
	public static String getQuery(String key, Map<String, ?> paramsMap) {
        return SpringVelocityUtil.getMergedString(queriesMap.get(key), paramsMap);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		processResources();
	}

}