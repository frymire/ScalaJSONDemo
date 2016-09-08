
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
  val jsonText = """{ "hello": "world", "list": [1, 2, 3], "nested": { "Brady": "sucks", "really?": true } }"""
  val jsonAST = jsonText.parseJson  
  println(jsonAST.compactPrint)
  println(jsonAST.prettyPrint)

  // Spray-JSON doesn't allow you to browse the AST
//  println(jsonAst("hello"))
    
  // You can build JSON strings from maps, but doesn't work for a Map[String, Any]
  println(Map("hello" -> "world", "Brady" -> "sucks").toJson)
//  println(Map("hello" -> "world", "Brady" -> "sucks", "numbers" -> List(1,2,3)).toJson)

  // You can, however, serialize from case classes to JSON strings 
  println
  val redSox = Team("Red Sox", Some(Color("Red", 255, 0, 0)))
  println(redSox)
  val redSoxString = redSox.toJson.compactPrint
  println(redSoxString)

  // Map to case class from JSON string
  println(redSoxString.parseJson.convertTo[Team])

  // Using an option type for color in Team also makes these possible
  println
  println("""{ "name": "Red Sox", "color": null }""".parseJson.convertTo[Team])
  println("""{ "name": "Red Sox" }""".parseJson.convertTo[Team])
  
  // It's also possible, though tedious, to build deserializers for non-case classes

}
