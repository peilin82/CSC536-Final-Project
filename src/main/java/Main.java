import java.lang.reflect.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {

    public static void main(String[] args) throws Exception {

        Job _job = new Job("AAA");
        _job.regInput(MyInput.class);
        _job.regOutput(MyOut.class);
        _job.regMap(MyMap.class);
        _job.regReduce(MyReduce.class);
        _job.setInputFile("E:\\hws\\2.txt");
        _job.start();
/*
        Job _job2 = new Job("BBB");
        _job2.regInput(MyInput.class);
        _job2.regOutput(MyOut2.class);
        _job2.regMap(MyMap.class);
        _job2.regReduce(MyReduce.class);
        _job2.setInputFile("E:\\hws\\1.txt");
        _job2.start();
        */
    }
}

