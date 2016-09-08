
import spray.json._
import spray.json.DefaultJsonProtocol._

object JSONSpray extends App {

  val jsonText = """{ "hello": "world", "list": [1, 2, 3], "nested": { "Brady": "sucks", "really?": true } }"""
  val jsonAST = jsonText.parseJson
  
  println(jsonAST.compactPrint)
  println(jsonAST.prettyPrint)
  
  // Spray-JSON doesn't allow you to browse the AST
//  println(jsonAst("hello"))
  
  println(List(1, 2, 3).toJson)

}