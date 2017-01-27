package pl.golm.akka;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import pl.golm.controller.GameController;

public class MainClient
{
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

    public static void main(String[] args)
    {
        ActorSystem actorSystem = ActorSystem.create("MainClientSystem");
        final ActorRef server = actorSystem.actorOf(ClientActor.props(), "client");
        server.tell(new ClientActor.StartClientMessage("start"), ActorRef.noSender());
    }
}
