package org.learn.akka

import akka.actor.{Actor, ActorLogging, PoisonPill}

case class ReadFileTask(fPath: String)

class FileReaderActor extends Actor with ActorLogging {

  override def receive = {
    case ReadFileTask(fPath) =>
      val lines = readFileLines(fPath)
      sender ! FileReadResult(countWordsMap(lines))
      self ! PoisonPill

    case r => log.info(s"unexpected message $r")
  }

  def readFileLines(fPath: String): List[String] = io.Source.fromFile(fPath).getLines().toList

  def countWordsMap(lines: List[String]): Map[String, Int] = lines.flatMap(_.split("\\W+"))
    .foldLeft(Map.empty[String, Int]) { (map, word) => map + (word -> (map.getOrElse(word, 0) + 1)) }
}
