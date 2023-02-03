import java.io.IOException;
import java.net.URISyntaxException;

// 数学建模： 1. 每2h向特定基站通信一次。
// 3. 信令的数据由手机识别号（人）， 基站id（位置），时间组成。
// 4. 假设与感染者同处一地2d为密切接触者。（病毒会留在感染者所接触到的公共物品上）
// 5. 对于分布式模拟，假设有个500个基站，150000个人，其所有信令数据全部存储在一个文件中。
// 6. 每个人每3个小时（一天8次），当不在家时，有40%的概率移动回家;晚上22点到早上6点不移动。数学期望是产生 行数据/天。该假设包含感染者。
// 7. 所有文件存储均按天分布。
public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ClassNotFoundException, NoSuchMethodException {
            new JobSelector("mr", args);
    }
}
