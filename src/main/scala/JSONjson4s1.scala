
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

object JSONJson4s1 extends App {
  
  // Use parse to create a JValue from a JSON string (though it prints as a JObject).
  val fromString = parse("""{"name":"luca", "age": 26, "cool":true, "numbers": [1, 2, 3, 4] }""")
  println(fromString)
  
  // Access elements as JValues
  println(fromString \ "name")

  // Two ways to print JValues
  println
  println(compact(fromString))
  println(pretty(fromString))

  // Two ways to create a JObject using the ~ operator
  println
  val fromPairs = ("name", "luca") ~ ("age", 26) ~ ("cool", true)
  val fromMap = ("name" -> "luca") ~ ("age" -> 26) ~ ("cool" -> true)
  println(compact(fromPairs))
  println(pretty(fromMap))
  
  // For a JObject var, you could also use ~ to add values incrementally or change them.
  var incremental = ("name", "luca") ~ ("age", 26)
  incremental = incremental ~ ("cool", true)
  println(compact(incremental))
  
  // To add to a JValue, use merge, rather than ~. The render method  
  // creates a single JValue, rather than a (String, String) 
  val incrementalJValue = fromString merge render("height", 175) 
  println(compact(incrementalJValue))
  
  val out = fromString findField {
    case JField("name", _) => true
    case _ => false
  }
  
  println(compact(out))

  println("Hi")
}
