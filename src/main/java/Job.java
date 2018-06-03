import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;
import akka.actor.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.sslconfig.util.LoggerFactory;
import akka.event.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Job extends Thread{
    String _name;
    private Class _mapClass ;
    private Class _reducerClass;
    private Class _inputClass ;
    private Class _outputClass;
    int _maxMap=5;
    int _maxReduce=5;
    String _infilePath;
    String _outfilePath;
    private  Thread t;
    //ActorSystem _actorsystem=ActorSystem.create("Jobsys");
    public  ActorSystem _actorsystem = ActorSystem.create("parent", ConfigFactory
            .load().getConfig("LocalSys"));
    ActorRef _master;
    Job(String name){
        _maxMap=ConfigFactory.load().getInt("LocalSys.maxMap");
        _maxReduce=ConfigFactory.load().getInt("LocalSys.maxReduce");
        _maxReduce=_actorsystem.settings().config().getInt("maxReduce");
        _name=name;
    }
    private void setMaster(){
        _master=_actorsystem.actorOf(Props.create(Master.class,_mapClass,_reducerClass),"mapMonitor");
        MsgStart _mapcreate=new MsgStart();
        _mapcreate.setMaxMap(_maxMap);
        _mapcreate.set_maxReduce(_maxReduce);
        _mapcreate.set_system(_actorsystem);
        _mapcreate.setInputClass(_inputClass);
        _mapcreate.set_outClass(_outputClass);
        _mapcreate.set_infilePath(_infilePath);
        //_mapcreate.setMapRouter(new Router(new RoundRobinRoutingLogic()));
        _master.tell(_mapcreate,ActorRef.noSender());

    }
    private void setMap(){
        //ActorRef _reducemonitor=_actorsystem.actorOf(Props.create(MapActor.class),"mapActor");
        MsgMap _createMap=new MsgMap();
        _createMap.set_MaxReduce(_maxReduce);
        _createMap.set_system(_actorsystem);
        _createMap.set_outClass(_outputClass);
        //_reducecreate.set_ReduceRouter(new Router(new RoundRobinRoutingLogic()));
        _master.tell(_createMap,ActorRef.noSender());

    }

    Job() throws Exception{

    }
    public void regInput(Class inputCls){
        _inputClass=inputCls;
    }
    public void regOutput(Class outputCls){
        _outputClass=outputCls;
    }
    public void setInputFile(String path){
        _infilePath=path;
    }
    public void setOutputFile(String path){
        _outfilePath=path;
    }
    public void regMap(Class mapCls){
        _mapClass=mapCls;
    }
    public void regReduce(Class reducerCls){
        _reducerClass=reducerCls;
    }
    private Config createConfig(String host,String port) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("akka.loglevel", "ERROR");
        map.put("akka.stdout-loglevel", "ERROR");

        //开启akka远程调用
        map.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider");

        List<String> remoteTransports = new ArrayList<String>();
        remoteTransports.add("akka.remote.netty.tcp");
        map.put("akka.remote.enabled-transports", remoteTransports);

        map.put("akka.remote.netty.tcp.hostname", host);
        map.put("akka.remote.netty.tcp.port", port);

        map.put("akka.remote.netty.tcp.maximum-frame-size", 100 * 1024 * 1024);

        //forkjoinpool默认线程数 max(min(cpu线程数 * parallelism-factor, parallelism-max), 8)
        map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-factor", "50");
        map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-max", "50");

        //logger.info("akka.remote.netty.tcp.hostname="+map.get("akka.remote.netty.tcp.hostname"));
        //logger.info("akka.remote.netty.tcp.port="+map.get("akka.remote.netty.tcp.port"));

        return ConfigFactory.parseMap(map);
    }

    public void start(){
        setMaster();
        try {
            Thread.sleep(1000);
            setMap();
            Thread.sleep(1000);
        }catch(Exception e){

        }
            //_master.tell(new MsgRun(),ActorRef.noSender());
        //_master.tell(new MsgRun(),ActorRef.noSender());
        //_master.tell(new MsgRun(),ActorRef.noSender());
        //_master.tell(new MsgRun(),ActorRef.noSender());

    }
 }
