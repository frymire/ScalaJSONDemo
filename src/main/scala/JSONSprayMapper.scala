
import spray.json._
 
case class Name(first: String, last: String)

case class ColorCase(colorName: String, red: Int, green: Int, blue: Int, personName: Name)

object MyProtocol extends DefaultJsonProtocol {
  
  // To make the nesting work, declare both formats within the same protocol object
  implicit val nameFormat = jsonFormat2(Name)
  implicit val colorFormat = jsonFormat5(ColorCase) // use jsonFormat5, since Color has 5 fields
}

object JSONSprayMapper extends App {
  
  import MyProtocol._

  val meJSONString = """{"first": "Mark", "last": "Frymire"}"""
  val me = meJSONString.parseJson.convertTo[Name] 
  println(me)

  
  val colorJSONString = ColorCase("CadetBlue", 95, 158, 160, me).toJson  
  println(colorJSONString.prettyPrint)
  
  // Map to a Color instance
  println(colorJSONString.convertTo[ColorCase])

}