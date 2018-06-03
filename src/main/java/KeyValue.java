import java.util.*;
public class KeyValue {
    private Object _key;
    private Object _value;
    KeyValue(){

    }

    public void set_key(Object _key) {
        this._key = _key;
    }

    public void set_value(Object _value) {
        this._value = _value;
    }

    public Object get_key() {
        return _key;
    }

    public Object get_value() {
        return _value;
    }
}
