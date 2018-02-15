
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor3 extends UntypedActor {

    //---------------------------------------------------------
    // Static Properties
    //---------------------------------------------------------

    //---------------------------------------------------------
    // Properties
    //---------------------------------------------------------
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //---------------------------------------------------------
    // Lifecycle Methods
    //---------------------------------------------------------
    /**
     * Constructor
     */
    public MyActor3() {
        super();
    }

    //---------------------------------------------------------
    // Methods
    //---------------------------------------------------------
    @Override
    public void onReceive(Object request) throws Exception {

        ActorRef self = this.self();
        ActorRef sender = this.sender();
        if ( request instanceof MessageRequest ) {
            log.info( "MyActor3 Received : {}", ((MessageRequest) request).message );
            sender.tell( AkkaJavaMain.createMessageResponse( MessageConsts.MESSAGE_ACTOR3_RESPONSE ), self );
        } else {
            sender.tell( AkkaJavaMain.createMessageResponse( MessageConsts.MESSAGE_RESPONSE_NOT_MATCH ), self );
        }

    }

}
