import akka.actor.ActorSystem;
import akka.routing.Router;
import java.io.Serializable;


public class MsgStart implements Serializable {
    private int _maxMap;
    private int _maxReduce;
    private Class _mapClass;
    private Class _inputClass;
    private Class _outClass;
    private Router _mapRouter;
    private ActorSystem _system;
    private String _infilePath;
    private String _outfilePath;

    public void set_maxReduce(int _maxReduce) {
        this._maxReduce = _maxReduce;
    }

    public int get_maxReduce() {
        return _maxReduce;
    }

    public void set_infilePath(String _infilePath) {
        this._infilePath = _infilePath;
    }

    public String get_infilePath() {
        return _infilePath;
    }

    public void set_outClass(Class _outClass) {
        this._outClass = _outClass;
    }

    public Class get_outClass() {
        return _outClass;
    }

    public int get_maxMap(){
        return _maxMap;
    }
    public Class get_inputClass(){
        return _inputClass;
    }
    public Class get_mapClass(){
        return _mapClass;
    }
    public Router get_mapRouter(){
        return _mapRouter;
    }
    public ActorSystem get_system(){
        return _system;
    }
    public void setMaxMap(int maxMap){
        _maxMap=maxMap;
    }
    public  void setInputClass(Class inputClass){
        _inputClass=inputClass;
    }
    public  void setMapClass(Class mapClass){
        _mapClass=mapClass;
    }
    public void setMapRouter(Router router){
        _mapRouter=router;
    }
    public void set_system(ActorSystem system){
        _system=system;
    }

}
