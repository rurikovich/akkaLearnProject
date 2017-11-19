package org.learn.akka

import akka.actor.{ActorSystem, Props}

object FileReaderAkkaRunner extends App {
  val dirPath = "C:\\Users\\User\\IdeaProjects\\AkkaLearnProject\\data"

  val system = ActorSystem("fileReader")

  val mainActor = system.actorOf(Props[MainActor], "mainActor")
  mainActor ! Start(dirPath)
}
