package swap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import controller.ClassCache;
import monitor.LoadConfig;

/**
 * Agent主类
 * @author huliangbin
 */
public class AgentMain {
	public static Pattern pattern = Pattern.compile(".+\\.class$");
	public static final String SOURCEPATH = "sourcecode"; //源文件配置key
	
	public static void agentmain(String agentargs, Instrumentation inst) throws UnmodifiableClassException, IOException, ClassNotFoundException{
		Collection<String> classNames = new HashSet<String>();
		//获取源码路径
		LoadConfig config = LoadConfig.getInstance();
		if(!config.loadConfig()){
			System.out.println("load config error");
			return;
		}
		String sourcePath = config.getProtperties(SOURCEPATH);
		
		JarFile jarfile = new JarFile(new File(sourcePath));
		Enumeration<JarEntry> enumer = jarfile.entries();
		while(enumer.hasMoreElements()){
			JarEntry jarEntry = enumer.nextElement();
			String entryName = jarEntry.getName();
			if(pattern.matcher(entryName).matches()){
				//源码的classname
				String className = entryName.replace("/", ".").substring(0, entryName.length()-6);
				classNames.add(className);
				byte[] bs = null;
				try {
					InputStream is = jarfile.getInputStream(jarEntry);
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					int i = -1;
					while((i=is.read())!=-1){
						bao.write(i);
					}
					bs = bao.toByteArray();
					bao.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ClassCache.getInstance().saveBytes(className, bs);
			}
		}
		
		inst.addTransformer(new Transformer(),true);
		for(String name : classNames){
			System.out.println(name + "needs hotswap");
			inst.retransformClasses(Class.forName(name));
		}
	}
}
