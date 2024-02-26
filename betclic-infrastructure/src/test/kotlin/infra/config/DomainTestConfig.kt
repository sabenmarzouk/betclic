package infra.config


import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@TestConfiguration
@Import(DomainConfig::class)
class DomainTestConfig {

}