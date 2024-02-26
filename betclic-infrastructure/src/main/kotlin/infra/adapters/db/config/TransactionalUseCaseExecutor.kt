package infra.adapters.db.config

import org.springframework.transaction.annotation.Transactional
import java.util.function.Supplier

open class TransactionalUseCaseExecutor {
    @Transactional
    open fun <T> executeInTransaction(execution: Supplier<T>): T {
        return execution.get()
    }
}