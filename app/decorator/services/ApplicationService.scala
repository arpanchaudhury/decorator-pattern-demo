package decorator.services

import com.google.inject.{Inject, Singleton}
import decorator.gateway.ServiceGateway
import decorator.utils.Address

@Singleton
class ApplicationService @Inject()(gateway: ServiceGateway) {
  def getData(address: Address) = gateway.getData(address)
}
