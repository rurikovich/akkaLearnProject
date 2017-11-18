import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.learn.akka.{FileReadResult, FileReaderActor}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.collection.immutable.HashMap
import scala.concurrent.duration._

class FileReaderAkkaSpec(_system: ActorSystem)
  extends TestKit(_system)
    with ImplicitSender
    with Matchers
    with FlatSpecLike
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("FileReaderAkkaSpec"))

  override def afterAll: Unit = {
    system.terminate()
    system.awaitTermination(10.seconds)
  }

  "An FileReaderActor" should "be able to add new FileReadResult" in {
    val fileReader = TestActorRef(Props[FileReaderActor])
    fileReader ! FileReadResult(HashMap("sss" -> 2))
  }
}
