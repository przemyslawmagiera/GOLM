package server.pl.golm.server.akka;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import server.pl.golm.server.GameServer;

public class MainServer
{
    static class ServerActor extends AbstractLoggingActor
    {
        // Protocol (all messages that a main server can receive)
        static class StartServerMessage
        {
            private final String message;

            public  StartServerMessage(String message)
            {
                this.message = message;
            }
        }

        {
            receive(ReceiveBuilder.match(StartServerMessage.class, this::onStartServerMessage).build());
        }

        private GameServer gameServer;

        private void onStartServerMessage(StartServerMessage message)
        {
            gameServer = new GameServer();
            new Thread(gameServer).start();
            log().info("Server started");
        }

        public static Props props ()
        {
            return Props.create(ServerActor.class);
        }
    }

    public static void main(String[] args)
    {
        ActorSystem actorSystem = ActorSystem.create("MainServerSystem");
        final ActorRef server = actorSystem.actorOf(ServerActor.props(), "server");
        server.tell(new ServerActor.StartServerMessage("start"), ActorRef.noSender());
    }
}
