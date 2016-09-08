
import spray.json._
import DefaultJsonProtocol._ 

abstract class Parent
case class ChildA(name: String) extends Parent
case class ChildB(firstName: String, lastName: String) extends Parent
case class Container(x: Parent)

object PolyProtocol extends DefaultJsonProtocol {
  
  implicit val aFmt = jsonFormat1(ChildA)
  implicit val bFmt = jsonFormat2(ChildB)

  implicit object ParentJSONFormat extends RootJsonFormat[Parent] {
    
    def write(p: Parent) = p match {
      case a: ChildA => a.toJson
      case b: ChildB => b.toJson
    }

    def read(v: JsValue) = v match {
      case obj: JsObject if (obj.fields.size == 2) => v.convertTo[ChildB]
      case obj: JsObject => v.convertTo[ChildA]
    }
    
  }

  implicit val cFmt = jsonFormat1(Container)
}


object JSONSprayPolymorphic extends App {

  import PolyProtocol._

  val in = List(Container(ChildA("Mark Frymire")), Container(ChildB("Kurt", "Frymire")) )
  val inJSON = in.toJson
  val out = inJSON.convertTo[List[Container]]

  println(in)
  println(inJSON)
  println(out)
  
}
