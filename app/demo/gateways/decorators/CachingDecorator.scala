package demo.gateways.decorators

import demo.gateways.ServiceGateway
import demo.utils.Address
import play.api.Play.current
import play.api.cache.Cache
import play.api.libs.json.JsValue

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

trait CachingDecorator extends ServiceGateway {

  override def getData(address: Address) = {
    Cache.getAs[JsValue](address.toString) match {
      case Some(value) => Future.successful(value)
      case None        => super.getData(address) map {
        data => Cache.set(address.toString, data, 6.hours); data
      }
    }
  }
}
