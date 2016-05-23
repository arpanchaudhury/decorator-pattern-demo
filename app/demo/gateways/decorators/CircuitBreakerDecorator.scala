package demo.gateways.decorators

import demo.gateways.ServiceGateway
import demo.utils.{Address, CustomCircuitBreaker}
import org.slf4j.LoggerFactory

import scala.concurrent.duration.DurationInt

trait CircuitBreakerDecorator extends ServiceGateway {
  val logger = LoggerFactory.getLogger(this.getClass)

  override def getData(address: Address) = {
    CustomCircuitBreaker.getCircuitBreaker(
      maxFailures         = 3,
      callTimeout         = 1.second,
      resetTimeout        = 10.second,
      onOpenCallback      = {logger.error("Service is temporarily down.")},
      onHalfOpenCallback  = {logger.error("Service will be called again to check availability.")},
      onCloseCallback     = {logger.error("Service is back up.")}
    ).withCircuitBreaker(super.getData(address))
  }
}
