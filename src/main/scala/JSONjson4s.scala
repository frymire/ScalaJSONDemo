
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

object JSONJson4s extends App {
  
  // Use parse to create a JValue from a JSON string (though it prints as a JObject).
  val fromString = parse("""{"name":"luca", "age": 26, "cool":true, "numbers": [1, 2, 3, 4] }""")
  println(fromString)
  
  // Two ways to print JValues
  println(compact(fromString))
  println(pretty(fromString))

  // Two ways to create a JObject using the ~ operator
  println
  val fromPairs = ("name", "luca") ~ ("age", 26) ~ ("cool", true)
  val fromMap = ("name" -> "luca") ~ ("age" -> 26) ~ ("cool" -> true)
  println(compact(fromPairs))
  println(compact(fromMap))
  
  // For a JObject var, you could also use ~ to add values incrementally
  var incremental = ("name", "luca") ~ ("age", 26)
  incremental = incremental ~ ("cool", true)
  println(compact(incremental))
  
  // To add to a JValue, use merge rather than ~. The render() creates a single JValue, rather than a pair.
  val incrementalJValue = fromString merge render("height", 175) 
  println
  println(compact(incrementalJValue))
  
  
  // Now let's browse the AST...
  
  // Access elements as JValues
  println(fromString \ "name") // JString(luca)
  println(fromString.children(1)) // JInt(26)
  println(fromString \ "nonexistent") // JNothing

  // Pull out a specific field
  val name = fromString findField {
    case JField("name", _) => true
    case _ => false
  }  
  println
  println(s"Specific field: ${compact(name)}")
  
  // Transform fields
  val transformed = fromString transformField {
    case JField("name", _) => ("Name", JString("Luca")) // Change the field name and value
    case JField("age", JInt(age)) => ("age", JInt(age+1)) // Extract and update the value    
  }
  println
  println(s"Transformed: ${compact(transformed)}")
  
  // See a diff between two JValues
  val Diff(changed, added, deleted) = fromString diff transformed 
  println(s"  Changed: ${compact(changed)}")
  println(s"  Added: ${compact(added)}")
  println(s"  Deleted: ${compact(deleted)}")
  
  // Remove fields
  val removed = fromString removeField {
    case JField("name", _) => true
    case _ => false
  }
  println
  println(s"Removed: ${compact(removed)}")
  
  // Filter fields
  val filtered = fromString filterField {
    case JField("name", _) => false
    case JField("cool", _) => true
    case JField("numbers", _) => false
    case _ => true
  }
  println(s"Filtered: ${compact(filtered)}")
  
  
}
