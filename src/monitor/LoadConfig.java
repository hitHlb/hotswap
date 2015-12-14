package monitor;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class LoadConfig {
	private Properties agentConfig = new Properties();
	
	public static LoadConfig getInstance(){
		return LoadConfigHolder.instance;
	}
	
	public boolean loadConfig(){
		try{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("AgentConfig.properties");
			if(null == is)
				return false;
			agentConfig.load(is);
			is.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Set<String> getAgentConfigKeys(){
		return agentConfig.stringPropertyNames();
	}
	
	public String getProtperties(String key){
		return agentConfig.getProperty(key);
	}

	public static class LoadConfigHolder{
		private static LoadConfig instance = new LoadConfig();
	}
}
