
import spray.json._

case class Color(name: String, red: Int, green: Int, blue: Int)
case class Team(name: String, color: Option[Color])

object MyJsonProtocol extends DefaultJsonProtocol {

  implicit val colorFormat = jsonFormat4(Color) // use jsonFormat4, since Color has 4 fields
  implicit val teamFormat = jsonFormat2(Team)

//  // You could also overload the field names like this
//  implicit val colorFormat = jsonFormat(Color, "name", "r", "g", "b")
//  implicit val teamFormat = jsonFormat(Team, "name", "jersey")

}

object JSONSpray extends App {

  import MyJsonProtocol._
  
  // Parse from JSON string
  val jsonText = """{ "hello": "world", "list": [[1, 2, 3],[4, 5, 6]], "nested": { "Brady": "sucks", "really?": true } }"""
  val jsonAST = jsonText.parseJson  
  println(jsonAST.compactPrint)
  println(jsonAST.prettyPrint)

  // It isn't especially easy to browse the AST
  println(jsonAST.asJsObject.fields("hello"))
  println(jsonAST.asJsObject.fields("list"))
  println(jsonAST.asJsObject.fields("nested").asJsObject.fields("really?"))
      
  // You can build JSON strings from maps, but doesn't work for a Map[String, Any]
  println(Map("hello" -> "world", "Brady" -> "sucks").toJson)
//  println(Map("hello" -> "world", "Brady" -> "sucks", "numbers" -> List(1,2,3)).toJson)

  // You can, however, serialize from case classes to JSON
  println
  val redSox = Team("Red Sox", Some(Color("Red", 255, 0, 0)))
  println(redSox)
  val redSoxString = redSox.toJson.compactPrint
  println(redSoxString)

  // Map from JSON string to case class
  println(redSoxString.parseJson.convertTo[Team])

  // Using an option type for color in Team also makes these possible
  println
  println("""{ "name": "Red Sox", "color": null }""".parseJson.convertTo[Team])
  println("""{ "name": "Red Sox" }""".parseJson.convertTo[Team])

}
