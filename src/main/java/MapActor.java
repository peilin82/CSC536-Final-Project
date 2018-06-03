import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapActor extends UntypedAbstractActor {
    ArrayList<Routee> _reduceActors = new ArrayList<Routee>();
    Class _myMapClass;
    Class _myReduceClass;
    Class _myoutClass;
    Router _router;
    Object _key;
    Object _value;
    MsgMap _msgMap;
    ActorRef _outActor;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    int _numOfReduce;
    int _okNumReduce;
    int _numOfOut;
    int _okNumOut;
    ActorRef _parent;
    ActorSystem _actorSystem;
    MsgStop msgStop;
    MapActor(Class mymapClass) {

        _myMapClass = mymapClass;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MsgMap) {
//            log.notifyInfo("LOG display");
            _outActor = context().actorOf(Props.create(OutActor.class), "Output");
//            System.out.println("===>get msg:" + message.toString()
//                    + "Sender=" + sender().toString()
//                    + "Self=" + self().toString());
            _parent = sender();
            _msgMap = (MsgMap) message;
            _key = ((MsgMap) message).get_Key();
            _value = ((MsgMap) message).get_Value();
            _myoutClass = ((MsgMap) message).get_outClass();
            _numOfReduce = ((MsgMap) message).get_maxReduce();
            ActorSystem _mapsystem = ((MsgMap) message).get_system();
            _myReduceClass = ((MsgMap) message).get_ReduceClass();
            _router = ((MsgMap) message).get_reduceRouter();
            for (int i = 0; i < _numOfReduce; i++) {
                ActorRef _reduceActorRef = context().actorOf(Props.create(ReduceActor.class), "Reduce" + i);
                context().watch(_reduceActorRef);
                _reduceActors.add(new ActorRefRoutee(_reduceActorRef));
            }
            _router = new Router(new RoundRobinRoutingLogic(), _reduceActors);
            //System.out.println(context().self().path().name() + ":router=" + _router.toString());
            Object myMapObj = _myMapClass.newInstance();
            Method myMapMethod = _myMapClass.getMethod("mapRun", Object.class, Object.class);


            KeyValue kv = (KeyValue) myMapMethod.invoke(myMapObj, _key, _value);
            ArrayList<ArrayList<String>> lst = (ArrayList<ArrayList<String>>) kv.get_value();
            for (ArrayList<String> v : lst) {
                MsgM2R _m2r = new MsgM2R();

                _m2r.set_Key(kv.get_key());
                _m2r.set_Value(v);
                _m2r.set_ReduceClass(_myReduceClass);
                _m2r.set_outClass(_myoutClass);
                _m2r.set_outActor(_outActor);
                _m2r.set_system(_actorSystem);
                _router.route(_m2r, self());
            }
            msgStop = new MsgStop();
            _router.route(new Broadcast(msgStop), self());
            // _router.route(new Broadcast(new MsgStop()));
        } else if (message instanceof MsgEnded) {
                _okNumReduce++;
                System.out.println( self().path().name()+" receive from "+sender().path().name());

                if (_okNumReduce >= _numOfReduce) {
                    System.out.println( self().path().name()+" semd Ended Message to "+sender().path().name());
                    _outActor.tell(msgStop,self());

//                    _parent.tell(new MsgEnded(), self());
                }

//            System.out.println("===>get msg:" + message.toString()
//                    + "Sender=" + sender().toString()
//                    + "Self=" + self().toString());
//            _router.route(MsgRun.class,self());
//            System.out.println(_router.toString()+":"+context().self().path());

        } else if (message instanceof MsgOutStop){
            _okNumOut++;
            System.out.println( self().path().name()+" receive from "+sender().path().name());

            if (_okNumOut >= _numOfOut) {
                System.out.println( self().path().name()+" semd Ended Message to "+sender().path().name());

                _parent.tell(new MsgEnded(), self());
            }

        }
    }

    public void sendMsg2Reduce(String methodName) {

    }
}
