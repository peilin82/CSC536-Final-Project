

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Created by liyanxin on 2015/1/12.
 */
public class PropsDemo extends UntypedAbstractActor {

    private int x;
    private int y;

    public PropsDemo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create Props for an actor of this type.
     * 这相当于一个静态工场方法，直接通过该静态方法创建Props instance
     */
    public static Props props(final int x, final int y) {
        return Props.create(new Creator<PropsDemo>() {
            private static final long serialVersionUID = 1L;

            //@Override
            public PropsDemo create() throws Exception {
                return new PropsDemo(x, y);
            }
        });
    }


    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("message=" + message);
        int result = x + y;
        this.getSender().tell(result, this.getSelf());
        this.getContext().stop(this.getSelf());
    }
}