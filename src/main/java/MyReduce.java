import java.util.ArrayList;

public class MyReduce extends ReduceBase {
    @Override
    public Object reduceRun(Object key, Object value) {
        ArrayList<String> list=(ArrayList<String>) value;
        ArrayList<WordCount> wcList=new ArrayList<WordCount>();
        WordCount wc=new WordCount();
        for(String wd:list){
                wc._word=wd;
                wc._count++;
        }
        KeyValue kv=new KeyValue();
        kv.set_value(wc);
        kv.set_key(key);
//        System.out.println("*****key="+key+"  word="+((WordCount)kv.get_value()).get_word()+
//                    " count="+((WordCount)kv.get_value())._count);
        return kv;
//
    }
    class WordCount{
        String _word;
        int _count;

        public void set_word(String _word) {
            this._word = _word;
        }

        public void set_Count(int count) {
            this._count = count;
        }

        public int get_count() {
            return _count;
        }

        public String get_word() {
            return _word;
        }
    }
}
