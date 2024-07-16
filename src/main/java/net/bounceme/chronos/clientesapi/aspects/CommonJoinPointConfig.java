package net.bounceme.chronos.clientesapi.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(net.bounceme.chronos.clientesapi.aspects.BeforeSave)")
	public void beforeSaveAnnotation() {}
}
