import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class ReduceActor extends UntypedAbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    Class _myoutClass;
    ActorRef _outActor;
    ActorSystem _actorSytem;

    @Override
    public void onReceive(Object message) throws Exception{
        if(message instanceof MsgM2R){
//            log.info("===>get msg:"+message.toString()
//                    +"Sender="+sender().toString()
//                    +"Self="+self().toString());
            Object reduceObject=((MsgM2R) message).get_ReduceClass().newInstance();
            Method reduceRun=((MsgM2R) message).get_ReduceClass().getMethod("reduceRun",Object.class,Object.class);
            Object value=((MsgM2R) message).get_Value();
            Object key=((MsgM2R) message).get_Key();
            _myoutClass=((MsgM2R) message).get_outClass();
            _actorSytem=((MsgM2R) message).get_system();
            KeyValue kv=(KeyValue) reduceRun.invoke(reduceObject,key,value);
            MsgOut msgOut=new MsgOut();
            msgOut.set_outClass(_myoutClass);
            msgOut.setKv(kv);
            msgOut.set_filePath("E:\\hws\\abc.txt");
            msgOut.set_text("xxx");

            _outActor=((MsgM2R) message).get_outActor();
            _outActor.tell(msgOut,self());
            //log.info("key="+kv.get_key()+"  value="+kv.get_value());
        }else if(message instanceof MsgStop){
            sender().tell(new MsgEnded(),self());
            System.out.println(self().path().name()+": send stop");
        }
        //log.info(message.toString()+","+context().self().path());
    }
}
