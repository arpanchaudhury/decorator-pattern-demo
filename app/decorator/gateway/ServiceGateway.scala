package decorator.gateway

import com.google.inject.Inject
import decorator.utils.Address
import play.api.Configuration
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global

class ServiceGateway @Inject()(ws: WSClient, configuration: Configuration) {
  val url = configuration.underlying.getString("api.geocode.url")
  val apiKey = configuration.underlying.getString("api.geocode.api-key")

  def getData(address: Address) = {
    val request = ws.url(url).withQueryString(("address", address.toString), ("key", apiKey))
    request.get().map(_.json)
  }
}
