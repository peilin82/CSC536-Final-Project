import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.*;
import java.util.logging.*;
public class MyOut2 extends OutputBase {
    BufferedWriter Writer;
    static int num=0;
    @Override
    public void OutputRun(Object key,Object value)
    {
        System.out.println(">>>A Out put runing.");
        try {
            MyReduce.WordCount kv= (MyReduce.WordCount) value;
            String mykey=(String) key;
            String kv_word=kv.get_word();
            String kv_count=Integer.toString( kv.get_count());
            String str=num+" <"+mykey+"> : ["+kv_word+"]  ( "+kv_count+" )\n";
            System.out.println(num+">>>"+str);
            num++;
            AppendToFile.appendMethodA("E:\\hws\\bbb.txt", str);
        }catch(Exception e){
            System.out.println(">>>"+e.toString());

        }

    }
}
