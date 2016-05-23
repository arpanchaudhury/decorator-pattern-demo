package demo.services

import com.google.inject.name.Named
import com.google.inject.{Inject, Singleton}
import demo.gateways.ServiceGateway
import demo.utils.Address

@Singleton
class ApplicationService @Inject()(@Named("with_circuit_breaker") gateway: ServiceGateway) {
  def getData(address: Address) = gateway.getData(address)
}