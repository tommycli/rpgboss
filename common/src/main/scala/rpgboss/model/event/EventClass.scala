package rpgboss.model.event

import rpgboss.model.HasName
import rpgboss.model.RpgEnum
import rpgboss.model.ProjectData
import org.json4s.ShortTypeHints
import rpgboss.model.ProjectDataEnums

object EventParameterValueType extends RpgEnum {
  val Constant = Value(0, "Constant")
  val LocalVariable = Value(1, "Local Variable")

  def default = Constant
}

trait EventParameter[T] {
  def valueTypeId: Int
  def valueTypeId_=(v: Int)

  def constant: T
  def constant_=(v: T)

  def localVariable: String
  def localVariable_=(v: String)

  def copyValuesFrom(other: EventParameter[T]) = {
    valueTypeId = other.valueTypeId
    constant = other.constant
    localVariable = other.localVariable
  }

  import EventParameterValueType._

  def jsString = EventParameterValueType(valueTypeId) match {
    case Constant => EventJavascript.toJs(constant)
    case LocalVariable => localVariable
  }
}

object EventParameter {
  val hints = ShortTypeHints(List(
    classOf[BooleanParameter],
    classOf[FloatParameter],
    classOf[IntArrayParameter],
    classOf[IntParameter]))
}

case class BooleanParameter(
    var constant: Boolean = false,
    var valueTypeId: Int = EventParameterValueType.Constant.id,
    var localVariable: String = "") extends EventParameter[Boolean]

case class FloatParameter(
    var constant: Float = 0,
    var valueTypeId: Int = EventParameterValueType.Constant.id,
    var localVariable: String = "") extends EventParameter[Float]

case class IntArrayParameter(
    var constant: Array[Int] = Array(),
    var valueTypeId: Int = EventParameterValueType.Constant.id,
    var localVariable: String = "") extends EventParameter[Array[Int]]

case class IntParameter(
    var constant: Int = 0,
    var valueTypeId: Int = EventParameterValueType.Constant.id,
    var localVariable: String = "") extends EventParameter[Int]

case class EventClass(
  var name: String = "",
  var states: Array[RpgEventState] = Array(RpgEventState())) extends HasName