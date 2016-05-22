package decorator

import play.api.{Application, GlobalSettings}

object Global extends GlobalSettings {

  override def onStart(app: Application) = {
    println("Starting Demonstration...")
  }

  override def onStop(app: Application) = {
    println("Stopping Demonstration...")
  }
}
