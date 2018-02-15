import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;

public class MessageConsts {

    public static final long TIMEOUT = 30L * 1000L;
    public static final Duration TIMEOUT_DURATION = Duration.create( MessageConsts.TIMEOUT, TimeUnit.MILLISECONDS);

    public static final String MESSAGE_ACTOR1_REQUEST = "MessageActor1Request";
    public static final String MESSAGE_ACTOR1_RESPONSE = "MessageActor1Response";

    public static final String MESSAGE_ACTOR2_REQUEST = "MessageActor2Request";
    public static final String MESSAGE_ACTOR2_RESPONSE = "MessageActor2Response";

    public static final String MESSAGE_ACTOR3_REQUEST = "MessageActor3Request";
    public static final String MESSAGE_ACTOR3_RESPONSE = "MessageActor3Response";

    public static final String MESSAGE_REQUEST_NOT_MATCH = "MessageRequest_NotMatch";
    public static final String MESSAGE_RESPONSE_NOT_MATCH = "MessageResponse_NotMatch";


}
