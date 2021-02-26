import java.util.concurrent.ConcurrentHashMap

//Mutable-Mutable
var a = Array(1, 2, 3, 4)
println(a.mkString(","))
a(1) = 100
println(a.mkString(","))
a = Array(5, 6, 7, 8, 9)
println(a.mkString(","))

//Mutable-Immutable
var b = Seq(1, 2, 3, 4)
println(b.mkString(","))
//Produces compiler error:
//b(1) = 100
b = Seq(5, 6, 7, 8, 9)
println(b.mkString(","))

@volatile var c = Seq(1, 2, 3, 4)
println(b.mkString(","))
c = c :+ 1000
println(c.mkString(","))

class D{
  private var x = 0
  def incrementX = synchronized{
    x = x + 1
    x
  }
  def getX = (x)
}
val d = new D
println(d.getX)
d.incrementX
println(d.getX)

//Immutable-Mutable
val e = new ConcurrentHashMap[String, Long]
val runnable = new Runnable {
  override def run(): Unit = {
    val t = java.lang.System.currentTimeMillis()
    println(s"Putting new time $t by ${Thread.currentThread().getId}")
    e.put("time", t)
  }
}
val t1 = new Thread(runnable)
t1.start()
val t2 = new Thread(runnable)
t2.start()

//Immutable-Immutable
val states = Seq("CA", "NY", "FL")
class USStateWidget(val states: Seq[String]) extends Runnable {
  override def run(): Unit = {
    states.foreach(x => println(s"State: $x"))
  }
}
val widget1 = new Thread(new USStateWidget(states))
widget1.start()
val widget2 = new Thread(new USStateWidget(states))
widget2.start()
val widgetN = new Thread(new USStateWidget(states))
widgetN.start()
