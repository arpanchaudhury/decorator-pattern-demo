package demo.gateways

import demo.utils.Address
import play.api.Play.current
import play.api.libs.ws.WS

import scala.concurrent.ExecutionContext.Implicits.global

class ServiceGateway {
  lazy val url = current.configuration.underlying.getString("api.geocode.url")
  lazy val apiKey = current.configuration.underlying.getString("api.geocode.api-key")

  def getData(address: Address) = {
    val request = WS.client.url(url).withQueryString(("address", address.toString), ("key", apiKey))
    request.get().map(_.json)
  }
}
