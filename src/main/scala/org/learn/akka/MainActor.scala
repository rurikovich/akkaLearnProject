package org.learn.akka

import java.io.File

import akka.actor.{Actor, ActorLogging, Props, _}

import scala.collection._

case class Start(path: String)

object Stop

case class FileReadResult(data: Map[String, Int])

class MainActor extends Actor with ActorLogging {
  implicit def StringToFile(path: String): File = new File(path)

  var words: Map[String, Int] = mutable.Map.empty
  var n = 0
  var resCounter: Int = 0

  override def receive = {
    case Start(path) =>
      val files = findFilesInPath(path)
      n = files.length

      files.zipWithIndex.foreach {
        case (file, i) => context.actorOf(Props(new FileReaderActor), s"fileReaderActor_$i") ! ReadFileTask(file.getAbsolutePath)
      };

    case FileReadResult(dataMap) =>
      words = dataMap.foldLeft(words) { (mainMap, mapEntry) => mainMap + (mapEntry._1 -> (mainMap.getOrElse(mapEntry._1, 0) + mapEntry._2)) }
      resCounter += 1
      if (resCounter == n) {
        println(words)
        self ! Stop
      }

    case Stop =>
      log.warning(s"Stopping system")
      context.system.terminate()
  }

  def findFilesInPath(dir: File): Array[File] = {
    val filesAndDirs = dir.listFiles
    filesAndDirs.filter(_.isFile) ++ filesAndDirs.filter(_.isDirectory).flatMap(findFilesInPath)
  }

}

