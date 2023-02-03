import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import java.net.URI
import java.io.PrintWriter
import scala.collection.mutable.ListBuffer
import scala.io.Source
object main {
  def read_inf(path: String): List[Array[Int]] = {
    val f = Source.fromFile(path)
    val lines = f.getLines()
    val res = new ListBuffer[Array[Int]]()
    for(line <- lines) {
        val data = line.split(" ")
        res+= Array(
          Integer.parseInt(data.apply(0)),
          Integer.parseInt(data.apply(1)),
          Integer.parseInt(data.apply(2))
        )
    }
    res.toList
  }
  private val inf_trace = read_inf("../inf.csv")
  def IsCloseContact(t0: Int, t1: Int, T0: Int, T1: Int): Boolean = {
    val t = t0 + t1 * 24
    val T = T0 + T1 * 24
    T >= t && T <= t + 48
  }
  def map_data(data: String): (Int, String) = {
    val line = data.split(" ")
    val person = Integer.parseInt(line.apply(0))
    val station = Integer.parseInt(line.apply(1))
    val hour = Integer.parseInt(line.apply(2))
    val day = Integer.parseInt(line.apply(3))
    for(trace <- inf_trace) {
      if (trace.apply(0) == station && IsCloseContact(trace.apply(1), trace.apply(2), hour, day))
        return (person, "%d %d %d".format(station, hour, day))
    }
    (person, "")
  }
  def reduce_data(V1: String, V2: String): String = {
    V1 + V2
  }
  def main(args: Array[String]): Unit =
  {
    val conf = new SparkConf().setAppName("STest").setMaster("local")
    val context = new SparkContext(conf)
    val data = context.textFile("hdfs://localhost:9000/data/days/*.csv")
    val data_mapped = data.map(map_data)
    val data_reduced = data_mapped.reduceByKey(_+_)
    data_reduced.saveAsTextFile("res")
  }
}
