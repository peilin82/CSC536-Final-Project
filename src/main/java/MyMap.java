import java.util.ArrayList;

public class MyMap extends MapBase  {
    @Override
    public Object mapRun(Object key,Object value)
    {
//        System.out.println("****MAP******key="+key.toString()+"  Value="+value.toString());
        ArrayList<String> inwordList=(ArrayList)value;
        ArrayList<String> subWordList=new ArrayList<String>();
        ArrayList<ArrayList<String>> wordList=new ArrayList<ArrayList<String>>();
        String swd="";
        for(String wd:inwordList){
            if(swd.equals(wd)){
                subWordList.add(wd);
            }else{
                swd=wd;
                subWordList =new ArrayList<String>();
                subWordList.add(wd);
                wordList.add(subWordList);
            }
        }
        KeyValue kv=new KeyValue();
        kv.set_key(key);
        kv.set_value(wordList);
        return kv;
    }
 }
