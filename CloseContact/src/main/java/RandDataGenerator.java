import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandDataGenerator {
    private final int N1;
    private final int N2;
    private final int f;
    private final float p;
    private final Map<Integer, Integer> home = new HashMap<>();
    private void GenerateHome() {
        for (int i = 0; i < this.N1; i++)
            this.home.put(i, new Random().nextInt(this.N2));
    }
    public RandDataGenerator (int N1, int N2, float p, int f){
        this.N1 = N1;
        this.N2 = N2;
        this.p = p;
        this.f = f;
        GenerateHome();
    }

    private ArrayList<String> RandInfGenerator(int day, int home) {
        ArrayList<String> res = new ArrayList<>();
        res.add(String.format("%d %d %d\n", home, 6, day));
        int start = 6;
        int end = 22;
        int out_f = (end - start) / f;
        for (int i = 1; i < out_f; i++) {
            int place = home;
            if(new Random().nextFloat() < this.p){
                while(place == home)
                    place = new Random().nextInt(this.N2);
            }
            res.add(String.format("%d %d %d\n", place, start + i * this.f, day));
        }
        res.add(String.format("%d %d %d\n", home, 22, day));
        return res;
    }
    private void InfWriter() throws IOException {
        int home = new Random().nextInt(this.N2);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("inf.csv"));
        for (int i = 1; i <= 14; i++) {
            ArrayList<String> traces = RandInfGenerator(i, home);
            for(String trace: traces)
                bufferedWriter.write(trace);
        }
        bufferedWriter.close();
    }
    private void RandPopGenerator(int day) throws IOException {
        int start = 6;
        int end = 22;
        int out_f = (end - start) / this.f;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.format("%d.csv", day)));
        for (int i = 0; i < this.N1; i++) {
            bufferedWriter.write(String.format("%d %d %d %d\n", i, this.home.get(i), 6, day));
        }
        for (int i = 1; i < out_f; i++) {
            for (int j = 0; j < this.N1; j++) {
                int place = this.home.get(j);
                if(new Random().nextFloat() < this.p) {
                    while(place == this.home.get(j))
                        place = new Random().nextInt(this.N2);
                }
                bufferedWriter.write(String.format("%d %d %d %d\n", j, place, start + this.f * i, day));
            }
        }
        for (int i = 0; i < this.N1; i++) {
            bufferedWriter.write(String.format("%d %d %d %d\n", i, this.home.get(i), 22, day));
        }
        bufferedWriter.close();
    }
    private void PopWriter() throws IOException {
        for (int i = 1; i <= 14; i++)
            RandPopGenerator(i);
    }
    public void RandomDataWriter(String mode) throws IOException, NoSuchMethodException {
        if (Objects.equals(mode, "inf"))
            InfWriter();
        else if (Objects.equals(mode, "pop"))
            PopWriter();
        else throw new NoSuchMethodException("Invalid mode selection.");
    }
}