package demo.controllers

import com.google.inject.{Inject, Singleton}
import demo.services.ApplicationService
import demo.utils.Address
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ApplicationController @Inject()(service: ApplicationService) extends Controller {
  def test(address: Address) = Action.async {
    service.getData(address).map(Ok(_))
  }
}
