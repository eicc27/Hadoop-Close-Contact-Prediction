import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class JobSelector {
    public JobSelector(String mode, String[] args) throws NoSuchMethodException, IOException, URISyntaxException, InterruptedException, ClassNotFoundException {
        if (Objects.equals(mode, "gen")) {
            RandDataGenerator generator = new RandDataGenerator(300000, 500
                    , 0.6f, 2);
            generator.RandomDataWriter("inf");
            generator.RandomDataWriter("pop");
        }
        else if (Objects.equals(mode, "mr")) {
            Configuration configuration = new Configuration();
            configuration.set("infpath", args[0]);
            Job job = Job.getInstance(configuration);
            job.setMapperClass(MapFinder.class);
            job.setReducerClass(ReduceFinder.class);
            job.setJarByClass(Main.class);
            job.setOutputKeyClass(IntWritable.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.setInputPaths(job, new Path(new URI("hdfs://localhost:9000/data/days/*.csv")));
            FileOutputFormat.setOutputPath(job, new Path("./output"));
            job.waitForCompletion(true);
        }
    }
}
