import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class MapFinder extends Mapper<Object, Text, IntWritable, Text> {
    ArrayList<int[]> l = new ArrayList<>();

    private static boolean IsCloseContact(int t0, int t1, int T0, int T1) {
        int t = t0 + t1 * 24;
        int T = T0 + T1 * 24;
        return T >= t && T <= t + 48;
    }

    @Override
    protected void setup(Mapper<Object, Text, IntWritable, Text>.Context context) throws IOException {
        FileInputStream stream = new FileInputStream(context.getConfiguration().get("infpath"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String s;
        while((s = reader.readLine()) != null) {
            String[] _s = s.split(" ");
            l.add(new int[] {
                    Integer.parseInt(_s[0]),
                    Integer.parseInt(_s[1]),
                    Integer.parseInt(_s[2])
            });
        }
        reader.close();
        stream.close();
    }

    @Override
    protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, Text>.Context context) throws IOException, InterruptedException {
        String[] s = value.toString().split(" ");
        int person = Integer.parseInt(s[0]);
        int location = Integer.parseInt(s[1]);
        int time = Integer.parseInt(s[2]);
        int day = Integer.parseInt(s[3]);
        for (int[] t : l) {
            if (location == t[0] && IsCloseContact(t[1], t[2], time, day)) {
                String out = String.format("%d %d %d", location, time, day);
                context.write(new IntWritable(person), new Text(out));
                break;
            }
        }
    }
}