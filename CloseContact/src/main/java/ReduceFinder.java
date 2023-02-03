import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

class ReduceFinder extends Reducer<IntWritable, Text, IntWritable, Text> {
    @Override
    protected void reduce(IntWritable key, Iterable<Text> values, Reducer<IntWritable, Text, IntWritable, Text>.Context context) throws IOException, InterruptedException {
        StringBuilder out = new StringBuilder();
        for (Text value : values) {
            out.append(value.toString()).append(",");
        }
        context.write(key, new Text(out.toString()));
    }
}
