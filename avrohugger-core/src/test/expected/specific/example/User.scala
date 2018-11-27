/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package example

import scala.annotation.switch

case class User(var name: String, var favorite_number: Option[Int], var favorite_color: Option[String], var test: Either[String, Int], var test2: Option[Either[String, Int]]) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", None, None, None, None)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        name
      }.asInstanceOf[AnyRef]
      case 1 => {
        favorite_number match {
          case None => null
          case Some(x) => x
        }
      }.asInstanceOf[AnyRef]
      case 2 => {
        favorite_color match {
          case None => null
          case Some(x) => x
        }
      }.asInstanceOf[AnyRef]
      case 3 => {
        test match {
          case Left(left) => left
          case Right(right) => right
        }
      }.asInstanceOf[AnyRef]
      case 4 => {
        test2 match {
          case None => null
          case Some(x) => x match {
            case Left(left) => left
            case Right(right) => right
          }
        }
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.name = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.favorite_number = {
        value match {
          case null => None
          case x => Some(x)
        }
      }.asInstanceOf[Option[Int]]
      case 2 => this.favorite_color = {
        value match {
          case null => None
          case x => Some(x.toString)
        }
      }.asInstanceOf[Option[String]]
      case 3 => this.test = {
        value match {
          case (left: String) => Left(left.toString)
          case (right: Int) => Right(right)
        }
      }.asInstanceOf[Either[String, Int]]
      case 4 => this.test2 = {
        value match {
          case null => None
          case x => Some(x match {
            case (left: String) => Left(left.toString)
            case (right: Int) => Right(right)
          })
        }
      }.asInstanceOf[Option[Either[String, Int]]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = User.SCHEMA$
}

object User {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"example\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"favorite_number\",\"type\":[\"int\",\"null\"]},{\"name\":\"favorite_color\",\"type\":[\"string\",\"null\"]},{\"name\":\"test\",\"type\":[\"string\",\"int\"]},{\"name\":\"test2\",\"type\":[\"string\",\"int\",\"null\"]}]}")
}