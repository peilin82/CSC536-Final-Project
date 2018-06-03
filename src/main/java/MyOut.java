import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
public class MyOut extends OutputBase {
    BufferedWriter Writer;
    static int num=0;
    @Override
    public void OutputRun(Object key,Object value)
    {
        //System.out.println(">>>A Out put runing.");
        try {
            MyReduce.WordCount kv= (MyReduce.WordCount) value;
            String mykey=(String) key;
            String kv_word=kv.get_word();
            String kv_count=Integer.toString( kv.get_count());
            String str=num+" <"+mykey+"> : ["+kv_word+"]  ( "+kv_count+" )\n";
            System.out.println(num+">>>"+str);
            num++;
            //AppendToFile ap=new AppendToFile("E:\\hws\\aaa.txt", str);
            //ap.start();
            //Thread.sleep(5);
            //AppendToFile.appendMethodA("E:\\hws\\aaa.txt", str);
        }catch(Exception e){
            System.out.println(">>>"+e.toString());

        }

    }
}
