
import spray.json._
import spray.json.DefaultJsonProtocol._

object JSONSpray extends App {

  val jsonText = """{ "hello": "world", "list": [1, 2, 3], "nested": { "Brady": "sucks", "really?": true } }"""
  val jsonAST = jsonText.parseJson
  
  println(jsonAST.compactPrint)
  println(jsonAST.prettyPrint)
  
  println(List(1, 2, 3).toJson)
  
  // Can build JSON strings from maps, but doesn't work for a Map[String, Any]
  println(Map("hello" -> "world", "Brady" -> "sucks").toJson)
//  println(Map("hello" -> "world", "Brady" -> "sucks", "numbers" -> List(1,2,3)).toJson)

  // Spray-JSON doesn't allow you to browse the AST
//  println(jsonAst("hello"))
  
}