package com.nano.demoware.hystrix.faulttolerance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

@Aspect
@Component
public class FaultToleranceAspect {

	/** A very basic, synchronous Hystrix command */
	private final class HystrixCommandExtension extends HystrixCommand<Object> {
		private final ProceedingJoinPoint joinPoint;

		private HystrixCommandExtension(String groupName, String commandName, ProceedingJoinPoint joinPoint) {
	        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
	                .andCommandKey(HystrixCommandKey.Factory.asKey(commandName)));
			this.joinPoint = joinPoint;
		}

		protected Object run() throws Exception {
			try {
				return joinPoint.proceed();
			} catch (Exception e) {
				throw e;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Logger log = LoggerFactory.getLogger(FaultToleranceAspect.class);

	/**
	 * Wrap any {@link FaultTolerant} annotated method with this advice, which
	 * wraps the method invocation in a simple synchronous Hystrix command.
	 * <p>
	 * This pattern limits the functionality of Hystrix quite a bit, but it's
	 * non-invasive. This aop approach can be enhanced quite a bit too.
	 */
	@Around("@annotation(faultTolerance)")
	public Object synchExec(final ProceedingJoinPoint joinPoint, FaultTolerant faultTolerance) throws Throwable {
		log.debug("Adding fault tolerant advice to the following method: " + joinPoint.getSignature());

		String groupName = getGroupName(joinPoint, faultTolerance);
		String commandName = getCommandName(joinPoint, faultTolerance);
		new HystrixCommandExtension(groupName, commandName, joinPoint).execute();
		return null;
	}

	private String getCommandName(ProceedingJoinPoint joinPoint, FaultTolerant faultTolerance) {
		if (faultTolerance.command().length() > 0) {
			return faultTolerance.command();
		}
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod().getName();
	}

	/**
	 * Gets the group key from the annotation if specified, otherwise uses the
	 * (simple) class name
	 */
	private String getGroupName(ProceedingJoinPoint joinPoint,
			FaultTolerant faultTolerance) {
		if (faultTolerance.group() != ServiceGroup.NONE) {
			return faultTolerance.group().name().toLowerCase();
		}
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod().getDeclaringClass().getSimpleName();
	}
}
