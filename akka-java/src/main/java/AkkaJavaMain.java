
import java.util.Random;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import scala.concurrent.Await;
import scala.concurrent.Future;

public class AkkaJavaMain {

    //---------------------------------------------------------
    // Static Properties
    //---------------------------------------------------------

    private static final String PREFIX_ACTOR_PATH = "/layer1/";

    //---------------------------------------------------------
    // Properties
    //---------------------------------------------------------

    private boolean isJVMTerminating = false;

    // ActorSystem requires high resources, so we shold not create multi actor system...
    private ActorSystem actorSystem = ActorSystem.create("SampleAkkaSystem");

    private final LoggingAdapter log = Logging.getLogger(this.actorSystem, this);

    //---------------------------------------------------------
    // Methods
    //---------------------------------------------------------

    /**
     * Create actor
     *
     * @param actorClass actor class ref
     * @param actorName actor name
     * @return ActorAdaptor
     */
    protected <T extends Actor> ActorRef createActor( Class<T> actorClass, String actorName ) {
        Props props = Props.create(actorClass);
        //ActorRef actorRef = this.actorSystem.actorFor(PREFIX_ACTOR_PATH + actorName);
        ActorRef actorRef = this.actorSystem.actorOf(props, actorName);
        return actorRef;
    }

    /**
     * Add destroy process when jvm will be terminated.
     */
    protected void addDestroyProcess() {

        Runnable runnable = () -> {
            this.isJVMTerminating = true;
            this.actorSystem.shutdown();
            this.actorSystem.awaitTermination();
        };

        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }


    //---------------------------------------------------------
    // Static method.
    //---------------------------------------------------------
    /**
     * Entry point
     */
    public static void main( String[] args ) {

        AkkaJavaMain self = new AkkaJavaMain();

        // add listener when jvm is terminated.
        self.addDestroyProcess();

        Random random = new Random();

        // create actor
        ActorRef actor1 = self.createActor(MyActor1.class, "MyActor1");
        ActorRef actor2 = self.createActor(MyActor2.class, "MyActor2");

        // loop infinite
        while ( true ) {

            if ( self.isJVMTerminating ) {
                break;
            }

            int randomNum = random.nextInt(10);
            if ( randomNum % 2 == 0 ) {
                MessageResponse response = sendMessageToActor( actor1, MessageConsts.MESSAGE_ACTOR1_REQUEST );
                self.log.info( "ActorSystem Received Actor1 Result {}", response.message );
            } else if ( randomNum % 2 == 1 ) {
                MessageResponse response = sendMessageToActor( actor2, MessageConsts.MESSAGE_ACTOR2_REQUEST );
                self.log.info( "ActorSystem Received Actor2 Result {}", response.message );
            }

        }

    }

    /**
     * Send message to actor
     *
     * @param actor ActorRef class
     * @param message facade key
     */
    public static MessageResponse sendMessageToActor( ActorRef actor, String message ) {

        MessageRequest request = createMessageRequest( message );

        Future<Object> futureRes = Patterns.ask(
            actor,
            request,
            MessageConsts.TIMEOUT
        );

        MessageResponse response = null;
        try {

            response = (MessageResponse) Await.result(
                futureRes,
                MessageConsts.TIMEOUT_DURATION
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Create message request dto
     */
    public static MessageRequest createMessageRequest( String message ) {
        MessageRequest request = new MessageRequest();
        request.message = message;
        return request;
    }

    /**
     * Create message response dto
     */
    public static MessageResponse createMessageResponse( String message ) {
        MessageResponse response = new MessageResponse();
        response.message = message;
        return response;
    }

}
