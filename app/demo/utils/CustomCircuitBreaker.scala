package demo.utils

import akka.actor.ActorSystem
import akka.pattern.CircuitBreaker

import scala.concurrent.duration.FiniteDuration

object CustomCircuitBreaker {
  private val scheduler = ActorSystem("CustomCircuitBreaker").scheduler

  def getCircuitBreaker(maxFailures: Int, callTimeout: FiniteDuration, resetTimeout: FiniteDuration,
    onOpenCallback:  => Unit, onHalfOpenCallback: => Unit, onCloseCallback: => Unit): CircuitBreaker = {

    CircuitBreaker(scheduler, maxFailures = maxFailures, callTimeout = callTimeout, resetTimeout = resetTimeout)
        .onOpen(onOpenCallback).onHalfOpen(onHalfOpenCallback).onClose(onCloseCallback)
  }
}