package swap;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import controller.ClassCache;

public class Transformer implements ClassFileTransformer{
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		return ClassCache.getInstance().getBytes(className.replace("/", "."));
	}

}
