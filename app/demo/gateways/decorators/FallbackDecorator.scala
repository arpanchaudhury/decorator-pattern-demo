package demo.gateways.decorators

import demo.gateways.ServiceGateway
import demo.utils.{Address, CustomCircuitBreaker}
import org.slf4j.LoggerFactory

import scala.concurrent.duration.DurationInt

trait FallbackDecorator extends ServiceGateway {

  override def getData(address: Address) = {
    super.getData(address)
      .fallbackTo(super.getData(address.copy(street = "")))
        .fallbackTo(super.getData(address.copy(street = "", city = "")))
  }
}
