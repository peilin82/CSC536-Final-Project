import java.io.Serializable;


public class MsgOut implements Serializable {
    String _text;
    String _filePath;
    KeyValue kv;
    Class _outClass;

    public void set_outClass(Class _outClass) {
        this._outClass = _outClass;
    }

    public Class get_outClass() {
        return _outClass;
    }

    public void set_filePath(String _filePath) {
        this._filePath = _filePath;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public void setKv(KeyValue kv) {
        this.kv = kv;
    }

    public KeyValue getKv() {
        return kv;
    }

    public String get_text() {
        return _text;
    }

    public String get_filePath() {
        return _filePath;
    }
}
