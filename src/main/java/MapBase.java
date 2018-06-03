public abstract class MapBase {
    public abstract Object mapRun(Object key,Object value);
    public void regMap(Class process){
        _process=process;
    }
    Class _process;
    Object _key;
    Object _value;
    public void setKeyValue(Object k,Object v){
        _key=k;
        _value=v;
    }
    public void Do(){
        mapRun(_key,_value);
    }
}
