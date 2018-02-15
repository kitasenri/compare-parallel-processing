
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor2 extends UntypedActor {

    //---------------------------------------------------------
    // Static Properties
    //---------------------------------------------------------

    //---------------------------------------------------------
    // Properties
    //---------------------------------------------------------
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef childActor = null;

    //---------------------------------------------------------
    // Lifecycle Methods
    //---------------------------------------------------------
    /**
     * Constructor
     */
    public MyActor2() {
        super();
    }

    //---------------------------------------------------------
    // Methods
    //---------------------------------------------------------
    /**
     * Get child actor
     */
    protected ActorRef getChildActor() {

        if ( this.childActor == null ) {
            Props props = Props.create(MyActor3.class);
            this.childActor = this.getContext().system().actorOf(props, "MyActor3");
        }

        return this.childActor;
    }

    @Override
    public void onReceive(Object request) throws Exception {

        ActorRef self = this.self();
        ActorRef sender = this.sender();
        if ( request instanceof MessageRequest ) {

            log.info( "MyActor2 Received : {}", ((MessageRequest) request).message );

            // create child actor
            ActorRef childActorRef = this.getChildActor();
            AkkaJavaMain.sendMessageToActor( childActorRef, MessageConsts.MESSAGE_ACTOR3_REQUEST );

            sender.tell( AkkaJavaMain.createMessageResponse( MessageConsts.MESSAGE_ACTOR2_RESPONSE ), self );

        } else {
            sender.tell( AkkaJavaMain.createMessageResponse( MessageConsts.MESSAGE_RESPONSE_NOT_MATCH ), self );
        }

    }

}
