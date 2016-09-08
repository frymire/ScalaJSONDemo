
import rapture.json._
import rapture.json.jsonBackends.spray._

case class Fruit(name: String, variants: Set[String])

object JSONRapture extends App {
  
  val jsonText = """{ "fruits": [ { "name": "apple", "color": "red" }, { "name": "banana", "color": "yellow" } ] }"""
  
  val parsedJSON = Json.parse(jsonText) 
  println(parsedJSON.fruits)
  println(parsedJSON.fruits(0))
  println(parsedJSON.fruits(0).color)
  println(parsedJSON.fruits(0).color.as[String])
  
  // Generate JSON using interpolated string notation
  println(json"""{"name": "apple", "variants": ${List("cox", "braeburn")}}""")
  
  // Generate JSON from a case class (works but Eclipse shows an error)
//  println(Json(Fruit("apple", Set("cox", "braeburn"))))
  
}
