java 热更新工具</br>
功能： 替换运行中的项目的代码而不用停机维护, 目前能实现的替换是：</br>
1.new出来的新对象,无法修改该对象的静态属性。可修改方法执行过程</br>
2.之前已经在内存中缓存的对象,无法修改其任何属性, 但是可以修改方法的执行过程</br>
3.不可添加新的方法或者属性</br>

部署方式:</br>
1.在src/AgentConfig.properties下配置需要热更的project的Main-Class以及Agent.jar的路径(一会将该项目打包成Agent.jar),并且带上mc-前缀用来标识. 
  比如某个正在运行的project的Main-Class是com.Main, 则配置mc-com.Main=./Agent.jar.</br>
2.将热更的源码打包成jar(注意不要打出目录结构), 然后在src/AgentConfig.properties下配置sourcecode=源码jar包路径</br>
3.使用MANIFEST.MF打包该项目.也就是第一条中的Agent.jar</br>
4.执行src/monitor/Main</br>
注: 因为把监控程序和代理程序整合成了一个项目,所以除了需要运行src/monitor/Main之外,还需要将该项目打成代理jar包</br>

关键字: Agent, Instrumentation, VirtualMachine
