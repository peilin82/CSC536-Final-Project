import java.io.*;
import java.util.*;

public class MyInput extends InputBase {
    List<KeyValue> list=new ArrayList<KeyValue>();
    ArrayList<String> inPath=new ArrayList<String>();
    ArrayList<String> wordList=new ArrayList<String>();
    @Override
    public Object InputRun(Object myPath)  {
        inPath.add("E:\\hws\\1.txt");
        inPath.add("E:\\hws\\2.txt");
        inPath.add("E:\\hws\\3.txt");
        int k=0;
        for(String path:inPath) {
            try {
                 wordList=(ArrayList<String>)sort1((String) path);
            } catch (Exception e) {

            }
            KeyValue kv = new KeyValue();
            kv.set_key(path);
            kv.set_value(wordList);
            list.add(kv);
        }
        return list;
        /*
        KeyValue kv=new KeyValue();
        String Title[]={
                "AAAAAAAAA",
                "BBBBBBBBB" ,
                        "CCCCCCCC",
            "DDDDDDDDD",
            "EEEEEEEEE",
                        "FFFFFFFF",
                        "GGGGGGG",
                        "HHHHHHH",
                        "LLLLLLL",
                        "MMMMMMM"
        };
        System.out.println("Enter Input rotine");
        ArrayList<KeyValue> kvList=new ArrayList<KeyValue>();
        for(int i=0;i<10;i++) {

            ArrayList<Object> lst = new ArrayList<Object>();
            for (int J = 0; J < 10; J++)
                lst.add(J);

            kv.set_key(Title[i]);
            kv.set_value(lst);
            kvList.add(kv);
        }
        return  kvList;
        */
    }
    List sort1(String path)throws  IOException{
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                new FileInputStream(
                new File(path)),"utf-8"));
        BufferedWriter Writer = new BufferedWriter(
                new OutputStreamWriter(
                new FileOutputStream(
                new File("E:\\hws\\user_content_count.txt")),"utf-8"));
        String s=null;
        int c=0;
        List<String> list=new ArrayList<String>();
        while ((s=reader.readLine())!=null) {
            if(s=="") continue;
            String arry[] =s.split("[\\p{Punct}\\s]+");
            for (int i = 0; i < arry.length; i++) {
                if(arry[i].equals(""))
                    continue;
                list.add(arry[i]);
            }
        }
        //if the number of the word is 1,output the word and the number

        //将map.entrySet()转换成list
        //List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordCount.entrySet());
        Collections.sort(list, new Comparator<String>() {
            //降序排序
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
                //return o2.getValue().compareTo(o1.getValue());
            }
        });

//        for (String mapping : list) {
//            System.out.println(mapping);
//            Writer.append(mapping+"\r\n");
//        }
        return list;
    }
    List sort(String path)throws  IOException{
        Hashtable<String, Integer> wordCount = new Hashtable<String, Integer>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(path)),"utf-8"));
        BufferedWriter Writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(
                                new File("E:\\hws\\user_content_count.txt")),"utf-8"));
        String s=null;
        int c=0;
        while ((s=reader.readLine())!=null) {
            if(s=="") continue;
            String arry[] =s.split("[\\p{Punct}\\s]+");
            for (int i = 0; i < arry.length; i++) {
                if (!wordCount.containsKey(arry[i])) {
                    wordCount.put(arry[i], Integer.valueOf(1));
                } else {
                    wordCount.put(arry[i], Integer.valueOf(wordCount.get(arry[i]).intValue() + 1));
                }
            }
        }
        for (java.util.Map.Entry<String, Integer> j : wordCount.entrySet()) {
            String key = j.getKey();
            int value = j.getValue();
        }
        //if the number of the word is 1,output the word and the number

        //将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordCount.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            //降序排序
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
                //return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (Map.Entry<String, Integer> mapping : list) {
            System.out.println(mapping.getKey() + ":" + mapping.getValue());
            Writer.append(mapping.getKey() + ":" + mapping.getValue()+"\r\n");
        }
        return list;
    }
}
