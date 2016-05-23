package demo.modules

import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Inject}
import demo.gateways.decorators.{CachingDecorator, CircuitBreakerDecorator, FallbackDecorator}
import demo.gateways.ServiceGateway

class ApplicationModule @Inject() extends AbstractModule {
  def configure() = {
    bind(classOf[ServiceGateway])
      .annotatedWith(Names.named("with_fallback_and_circuit_breaker_and_caching"))
      .toInstance(new ServiceGateway with FallbackDecorator with CircuitBreakerDecorator with CachingDecorator)
  }
}
