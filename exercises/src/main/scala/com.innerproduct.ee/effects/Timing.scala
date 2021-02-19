package com.innerproduct.ee.effects

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object Timing extends App {
  val clock: MyIO[Long] =
    MyIO(() => System.currentTimeMillis) // <1>

  def time[A](action: MyIO[A]): MyIO[(FiniteDuration, A)] =
    for {
      start  <- clock
      result <- action
      end    <- clock
    } yield (FiniteDuration(end - start, TimeUnit.MILLISECONDS), result) // <2>

  val timedHello = Timing.time(MyIO.putStr("hello"))

  timedHello.unsafeRun() match {
    case (duration, _) => println(s"'hello' took $duration")
  }
}
