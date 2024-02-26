package infra.adapters.db.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy



@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
class UseCaseTransactionConfiguration {
    @Bean
    fun transactionalUseCaseAspect(transactionalUseCaseExecutor: TransactionalUseCaseExecutor): TransactionalUseCaseAspect {
        return TransactionalUseCaseAspect(transactionalUseCaseExecutor)
    }

    @Bean
    fun transactionalUseCaseExecutor(): TransactionalUseCaseExecutor {
        return TransactionalUseCaseExecutor()
    }
}

