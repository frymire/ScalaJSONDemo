
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

case class Address(street: String, city: String)
case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
case class Person(name: String, addresses: Map[String, Address], children: List[Child])

object JSONjson4sMapper extends App {
  
  implicit val formats = DefaultFormats

  // Extract into a case class with a map field, a list field, and nested entries with an option field
  val jsonText = """
         {
           "name": "joe", 
           "addresses": { 
             "address1": {"street": "Bulevard", "city": "Helsinki" },
             "address2": {"street": "Soho", "city": "London"}
           },
           "children": [
             { "name": "Mary", "age": 5, "birthdate": "2004-09-04T18:06:22Z"},
             { "name": "Mazy", "age": 3 }
           ]
         }"""

  val json = parse(jsonText)
  println(json.extract[Person])
  
  

  // Extract instances from subfields of the main instance
  println
  println( (json \ "addresses").extract[Map[String, Address]] )
  println( (json \ "children").extract[List[Child]] )
  
}
