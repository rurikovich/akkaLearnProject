package org.learn.akka

import akka.actor.{ActorSystem, Props}

object FileReaderAkkaRunner extends App {
  val dirPath = "data"

  val system = ActorSystem("fileReader")

  val mainActor = system.actorOf(Props[MainActor], "mainActor")
  mainActor ! Start(dirPath)


//  mainActor ! Stop
}
