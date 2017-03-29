package edge.core.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringVelocityUtil implements InitializingBean{
	
	private static VelocityEngine velocityEngine;
	
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public static String getMergedString(String str, Map<String, ?> paramsMap){
		VelocityContext context = new VelocityContext(paramsMap);
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(context, writer, "VelocityEngineUtil", str);
		String returnValue = writer.toString();
		if(returnValue != null){
			returnValue = returnValue.trim();
		}
		return returnValue;
	}
	
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}