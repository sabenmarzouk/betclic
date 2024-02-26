package infra.adapters.db.config

import UseCase
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut


@Aspect
class TransactionalUseCaseAspect(private val transactionalUseCaseExecutor: TransactionalUseCaseExecutor) {

    @Pointcut("@within(useCase)")
    fun inUseCase(useCase: UseCase?) {
    }

    @Around("inUseCase(useCase)")
    fun useCase(proceedingJoinPoint: ProceedingJoinPoint?, useCase: UseCase?): Any? {
        return transactionalUseCaseExecutor.executeInTransaction {
            proceed(
                proceedingJoinPoint
            )
        }
    }

    companion object {
        private fun proceed(joinPoint: ProceedingJoinPoint?): Any? {
            return joinPoint?.let { joinPoint.proceed() }
        }
    }
}