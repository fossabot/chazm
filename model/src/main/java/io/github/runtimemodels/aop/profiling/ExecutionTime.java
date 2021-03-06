package io.github.runtimemodels.aop.profiling;

import io.github.runtimemodels.message.L;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.validation.constraints.NotNull;

/**
 * The {@linkplain ExecutionTime} is a method Interceptor that tracks the time it takes for a method to complete.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Slf4j
class ExecutionTime implements MethodInterceptor {

    @Override
    public Object invoke(@NotNull final MethodInvocation invocation) throws Throwable {
        final long start = System.nanoTime();
        try {
            return invocation.proceed();
        } finally {
            final long end = System.nanoTime();
            log.trace(L.EXECUTION_TIME.get(), ExecutionTime.class.getSimpleName(), end - start, invocation.getMethod().getGenericReturnType().getTypeName()
                    .replaceAll("(\\w+\\.)*", ""), invocation.getMethod().getDeclaringClass().getSimpleName(), invocation.getMethod().getName()); //$NON-NLS-1$ //$NON-NLS-2$

        }
    }

}
