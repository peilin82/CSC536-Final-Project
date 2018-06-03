import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

import java.lang.reflect.Method;

public class OutActor extends UntypedAbstractActor {
    Class _myoutClass;
    @Override
    public void onReceive(Object message) throws Exception{
        if(message instanceof MsgOut){
            _myoutClass=((MsgOut) message).get_outClass();
            KeyValue kv=((MsgOut) message).getKv();
            Object myOutObj=_myoutClass.newInstance();
            Method myOutMethod=_myoutClass.getMethod("OutputRun",Object.class,Object.class);
            myOutMethod.invoke(myOutObj,kv.get_key(),kv.get_value());
            //Thread.sleep(10);
        }else if(message instanceof MsgStop){
            MsgOutStop msgOutStop = new MsgOutStop();
            sender().tell(msgOutStop,self());
        }

    }
}
