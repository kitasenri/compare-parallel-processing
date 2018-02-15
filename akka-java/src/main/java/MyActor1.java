
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class MyActor1 extends AbstractActor {

    //---------------------------------------------------------
    // Static Properties
    //---------------------------------------------------------

    //---------------------------------------------------------
    // Properties
    //---------------------------------------------------------
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private PartialFunction<Object, BoxedUnit> receiveMessage =
        ReceiveBuilder.match(
            MessageRequest.class,
            it -> {
                log.info( "MyActor1 Received : {}", it.message );
                this.processAnything( it.message );
            }
        ).matchAny(
            it -> {
                log.info( "MyActor1 Received : Unknown" );
            }
        ).build();

    //---------------------------------------------------------
    // Lifecycle Methods
    //---------------------------------------------------------
    /**
     * Constructor
     */
    public MyActor1() {
        super();
        this.receive( this.receiveMessage );
    }

    //---------------------------------------------------------
    // Methods
    //---------------------------------------------------------
    /**
     * Process anything if argument message is hit.
     *
     * @param message message key
     */
    protected void processAnything( String message ) {

        ActorRef self = this.self();
        ActorRef sender = this.sender();
        switch ( message ) {
            case MessageConsts.MESSAGE_ACTOR1_REQUEST: {
                sender.tell( AkkaJavaMain.createMessageResponse( MessageConsts.MESSAGE_ACTOR1_RESPONSE ), self );
                break;
            }
            default: {
                sender.tell( AkkaJavaMain.createMessageResponse( MessageConsts.MESSAGE_RESPONSE_NOT_MATCH ), self );
                break;
            }
        }

    }

}
