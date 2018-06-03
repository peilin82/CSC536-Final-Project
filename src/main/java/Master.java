import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.*;
import scala.sys.Prop;

import java.lang.reflect.*;
import java.util.ArrayList;

public class Master extends UntypedAbstractActor {
    ArrayList<Routee> _mapActors = new ArrayList<Routee>();
    Class _mapClass;
    Class _inputClass;
    Class _outClass;
    Class _reduceClass;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    int _numOfMap;
    int _okNumMap;
    ActorSystem _actorSystem;

    Master(Class mapClass,Class reduceClass){
        _mapClass=mapClass;
        _reduceClass=reduceClass;
        log.info("_mapClass="+_mapClass+"_mapClass="+_reduceClass);
        //log.info("_mapClass="+_mapClass+"_mapClass="+_reduceClass);

    }
    Router router;

    @Override
    public void preStart() throws Exception {
        super.preStart();

    }

    @Override
    public void onReceive(Object message) throws Exception{
        if (message instanceof MsgStart) {
            log.info("===>get msg:"+message.toString()
                    +"Sender="+sender().toString()
                    +"Self="+self().toString());
            _numOfMap = ((MsgStart) message).get_maxMap();
            _inputClass=((MsgStart) message).get_inputClass();
            _outClass=((MsgStart) message).get_outClass();
            _actorSystem=((MsgStart) message).get_system();
            String path=((MsgStart) message).get_infilePath();
            Object myObj=_inputClass.newInstance();
            Method myMethod=_inputClass.getMethod("InputRun",Object.class);
            ArrayList<KeyValue > lst=(ArrayList<KeyValue> ) myMethod.invoke(myObj,path);

            ActorSystem _mapsystem = ((MsgStart) message).get_system();
            Router _maprouter = ((MsgStart) message).get_mapRouter();
            for (int i = 0; i < _numOfMap; i++) {
                ActorRef _mapActorRef = context().actorOf(Props.create(MapActor.class,_mapClass), "map" + i);
                context().watch(_mapActorRef);
                _mapActors.add(new ActorRefRoutee(_mapActorRef));

            }

            router = new Router(new RoundRobinRoutingLogic(), _mapActors);
            int lstSize=lst.size();
            if(lstSize<_numOfMap)
                _numOfMap=lstSize;
            for(KeyValue kv:lst) {
                MsgMap _msgmap=new MsgMap();
                _msgmap.set_MaxReduce(((MsgStart) message).get_maxReduce());
                _msgmap.set_ReduceClass(_reduceClass);
                _msgmap.set_outClass(_outClass);
                _msgmap.set_Key(kv.get_key());
                _msgmap.set_Value(kv.get_value());
                _msgmap.set_system(_actorSystem);

                router.route(_msgmap, self());
            }
//            router.route( new Broadcast( _msgmap),self());
        }else if(message instanceof MsgRun){
            log.info("===>get msg:"+message.toString()
                    +"Sender="+sender().toString()
                    +"Self="+self().toString());
            router.route(message, self());
//            log.info(message.toString()+","+context().self().path().name());
        } else if(message instanceof MsgEnded){
            if(message instanceof MsgEnded) {
                _okNumMap++;
                if (_okNumMap >= _numOfMap) {
                    System.out.println(self().path().name()+"  terminated。 Receive message from "
                    +sender().path().name());
                    Thread.sleep(500);
                    _actorSystem.terminate();
                }
            }
//            log.info(context().self().path().name()+"-"+message.toString());
        } else{
//            router.route(message, self());
//            log.info(message.toString()+":"+context().self().path());
        }
    }
}
