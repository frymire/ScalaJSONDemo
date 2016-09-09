
import spray.json._
import DefaultJsonProtocol._ 

abstract class Parent
case class ChildA(name: String, kind: String = "A") extends Parent
case class ChildB(person: String, kind: String = "B") extends Parent
case class Container(x: Parent)

object PolyProtocol extends DefaultJsonProtocol {
  
  implicit val aFmt = jsonFormat2(ChildA)
  implicit val bFmt = jsonFormat2(ChildB)

  // Since parent isn't a case class, we need to write a custom format for it
  implicit object ParentJSONFormat extends RootJsonFormat[Parent] {
    
    def write(p: Parent) = p match {
      case a: ChildA => a.toJson
      case b: ChildB => b.toJson
    }

    // Use a "kind" field in the JSON to indicate which subclass to use
    def read(v: JsValue) = v.asJsObject.fields("kind") match {
      case JsString("A") => v.convertTo[ChildA]
      case JsString("B") => v.convertTo[ChildB]
      case _ => throw new Error
    }
    
  }

  implicit val cFmt = jsonFormat1(Container)
}


object JSONSprayPolymorphic extends App {

  import PolyProtocol._

  val in = List(Container(ChildA("Mark")), Container(ChildB("Kurt")) )
  val inJSON = in.toJson
  val out = inJSON.convertTo[List[Container]]

  println(in)
  println
  println(inJSON)
  println
  println(out)
  
}
