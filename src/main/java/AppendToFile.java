import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 将内容追加到文件尾部
 */
public class AppendToFile implements Runnable{
  String _writePath;
  String _txt;
    private Thread t;
    private String threadName;

    public void run() {
        appendMethodA(_writePath,_txt);
    }
    AppendToFile(String writePath,String txt){
        _txt=txt;
        _writePath=writePath;
        threadName="write file";
    }
    public void start () {
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
    /**
     * A方法追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public static void appendMethodA(String fileName,

                                     String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * B方法追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void appendMethodB(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

