
import rapture.json._
import rapture.json.jsonBackends.spray._

object JSONRapture extends App {
  
  val jsonText = """{
  "fruits": [
    {
      "name": "apple",
      "color": "red"
    },
    {
      "name": "banana",
      "color": "yellow"
    }
  ]
}"""
  
  val json = Json.parse(jsonText) 
  println(json.fruits(0).color.as[String])
  
}