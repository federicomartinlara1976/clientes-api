package net.bounceme.chronos.clientesapi.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import net.bounceme.chronos.clientesapi.support.beforesavestrategy.ProcessEntityStrategy;

@Configuration
@Aspect
public class BeforeSaveAspect {
	
	@Autowired
	@Qualifier("processClienteStrategy")
	private ProcessEntityStrategy processClienteStrategy;
	
	@Around("net.bounceme.chronos.clientesapi.aspects.CommonJoinPointConfig.beforeSaveAnnotation()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		return joinPoint.proceed(processArgs(joinPoint.getArgs()));
	}

	private Object[] processArgs(Object[] args) {
		
		Object objectToProcess = args[0];
		ProcessEntityStrategy processEntityStrategy = null;
		
		String clName = objectToProcess.getClass().getName();
		
		if (clName.equals("net.bounceme.chronos.clientesapi.models.entity.Cliente")) {
			processEntityStrategy = processClienteStrategy;
		}
		
		args[0] = processEntityStrategy.processObject(objectToProcess);
		
		return args;
	}

}
