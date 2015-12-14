package monitor;

import java.util.List;
import java.util.regex.Pattern;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * 以agent的方式进行热更新
 * 比如某个project处于运行状态,如果要对此project进行更新:</br>
 * 1.在本项目的AgentConfig.properties中配置需要热更的project的Main-Class,及Agent lib地址</br>
 * 2.然后启动本Main</br>
 * 本项目需要引用sun的tool.jar
 * @author huliangbin
 */
public class Main {
	private static LoadConfig loadConfig;
	private static final String PERFIX = "mc-";
	private static Pattern pattern = Pattern.compile("^" + PERFIX + ".+");
	public static void main(String[] args){
		List<VirtualMachineDescriptor> vmds = VirtualMachine.list();
		loadConfig = LoadConfig.getInstance();
		if(!loadConfig.loadConfig()){
			System.out.println("load config error");
			return;
		}
		//在已经启动的vm中寻找需要热更的vm
		for(String mainClass : loadConfig.getAgentConfigKeys()){
			if(!pattern.matcher(mainClass).matches())
				continue;
			mainClass = mainClass.substring(PERFIX.length());
			for(VirtualMachineDescriptor vmd : vmds){
				if(vmd.displayName().equals(mainClass)){
					try {
						VirtualMachine vm = VirtualMachine.attach(vmd);
						//加载Agent
						vm.loadAgent(loadConfig.getProtperties(mainClass));
						vm.detach();
					}catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}
	}
}
