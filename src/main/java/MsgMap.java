import akka.actor.ActorSystem;
import akka.routing.Router;
import akka.actor.ActorSystem;
import akka.routing.Router;

import java.io.Serializable;

public class MsgMap implements Serializable{

    private int _maxReduce;
    private Class _reduceClass;
    private Class _outClass;
    private Router _reduceRouter;
    private ActorSystem _system;
    private Object _key;
    private Object _value;

    public void set_outClass(Class _outClass) {
        this._outClass = _outClass;
    }

    public Class get_outClass() {
        return _outClass;
    }

    public int get_maxReduce(){
        return _maxReduce;
    }
    public Router get_reduceRouter(){
        return _reduceRouter;
    }
    public Object get_Key(){
        return _key;
    }
    public void set_Key(Object key){
        _key=key;
    }
    public Object get_Value(){
        return _value;
    }
    public void set_Value(Object value){
        _value=value;
    }

    public ActorSystem get_system(){
        return _system;
    }
    public void set_MaxReduce(int maxReduce){
        _maxReduce=maxReduce;
    }
    public  void set_ReduceClass(Class mapClass){
        _reduceClass=mapClass;
    }
    public  Class get_ReduceClass(){
        return _reduceClass;
    }
    public void set_ReduceRouter(Router router){
        _reduceRouter=router;
    }
    public void set_system(ActorSystem system){
        _system=system;
    }

}

