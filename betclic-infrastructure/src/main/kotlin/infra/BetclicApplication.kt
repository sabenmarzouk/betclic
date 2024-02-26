package infra
import infra.config.DomainConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DomainConfig::class)
class BetclicApplication
fun main(args: Array<String>) {
    runApplication<BetclicApplication>(*args)
}