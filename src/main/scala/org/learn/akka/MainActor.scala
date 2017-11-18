package org.learn.akka

import akka.actor.{Actor, ActorLogging, Props, _}

import scala.collection._

case class Start(path: String)

object Stop

case class ReadFileTask(fPath: String)

case class FileReadResult(data: Map[String, Int])

class MainActor extends Actor with ActorLogging {
  val words: Map[String, Int] = mutable.Map.empty

  override def receive = {
    case Start(path) =>
      findFilesInPath(path).foreach(fPath => context.actorOf(Props(new FileReaderActor), s"fileReaderActor_$fPath") ! ReadFileTask(fPath));

    case FileReadResult(data) =>
      mergeMap(data)

    case Stop =>
      log.warning(s"Stopping system")
      context.system.terminate()
  }

  def findFilesInPath(path: String): List[String] = List.empty

  def mergeMap(data: Map[String, Int]): Unit = {}
}

class FileReaderActor extends Actor with ActorLogging {

  def readFile(fPath: String): String = {
    ""
  }

  override def receive = {
    case ReadFileTask(fPath) =>
      readFile(fPath).split(" ")
  }
}

