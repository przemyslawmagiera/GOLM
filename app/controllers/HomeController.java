package controllers;

import akka.NotUsed;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.LoggingAdapter;
import akka.japi.Pair;
import akka.japi.pf.PFBuilder;
import akka.japi.pf.ReceiveBuilder;
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import pl.golm.controller.GameController;
import play.libs.F;
import play.mvc.*;

import akka.event.Logging;

import javax.inject.Inject;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * A very simple chat client using websockets.
 */
public class HomeController extends Controller {

    private final Flow userFlow;

    @Inject
    public HomeController(ActorSystem actorSystem,
                          Materializer mat) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
        LoggingAdapter logging = Logging.getLogger(actorSystem.eventStream(), logger.getName());

        //noinspection unchecked
        Source<String, Sink<String, NotUsed>> source = MergeHub.of(String.class)
                .log("source", logging)
                .recoverWithRetries(-1, new PFBuilder().match(Throwable.class, e -> Source.empty()).build());
        Sink<String, Source<String, NotUsed>> sink = BroadcastHub.of(String.class);

        Pair<Sink<String, NotUsed>, Source<String, NotUsed>> sinkSourcePair = source.toMat(sink, Keep.both()).run(mat);
        Sink<String, NotUsed> chatSink = sinkSourcePair.first();
        Source<String, NotUsed> chatSource = sinkSourcePair.second();
        this.userFlow = Flow.fromSinkAndSource(chatSink, chatSource).log("userFlow", logging);
    }

    static class ClientActor extends AbstractLoggingActor
    {
        // Protocol (all messages that a main server can receive)
        static class StartClientMessage
        {
            private final String message;

            public  StartClientMessage(String message)
            {
                this.message = message;
            }
        }

        {
            receive(ReceiveBuilder.match(StartClientMessage.class, this::onStartClientMessage).build());
        }

        private GameController gameController;

        private void onStartClientMessage(StartClientMessage message)
        {
            gameController = new GameController();
            new Thread(gameController).start();
            log().info("Client application started");
        }

        public static Props props ()
        {
            return Props.create(ClientActor.class);
        }
    }

    public Result startGame()
    {
        ActorSystem actorSystem = ActorSystem.create("MainClientSystem");
        final ActorRef server = actorSystem.actorOf(ClientActor.props(), "client");
        server.tell(new ClientActor.StartClientMessage("start"), ActorRef.noSender());
        return Results.ok(views.html.index.render("/startGame"));
    }

}
