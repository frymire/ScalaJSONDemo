
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


object JSONSprayMapper extends App {

  import MyJsonProtocol._

  val redSox = Team("Red Sox", Some(Color("Red", 255, 0, 0)))
  println(redSox)

  // Automatically serialize to JSON strings from case classes
  val redSoxString = redSox.toJson.compactPrint
  println(redSoxString)

  // Map to case class from JSON string
  println(redSoxString.parseJson.convertTo[Team])

  // Using an option type for color in Team also makes these possible
  println("""{ "name": "Red Sox", "jersey": null }""".parseJson.convertTo[Team])
  println("""{ "name": "Red Sox" }""".parseJson.convertTo[Team])

}
