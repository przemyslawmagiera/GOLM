package client.pl.golm.akka;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import client.pl.golm.controller.WebController;

@Controller
@RequestMapping("/")
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

        private WebController gameController;

        private void onStartClientMessage(StartClientMessage message)
        {
            gameController = new WebController();
            new Thread(gameController).start();
            log().info("Client application started");
        }

        public static Props props ()
        {
            return Props.create(ClientActor.class);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public static String main(String[] args)
    {
        ActorSystem actorSystem = ActorSystem.create("MainClientSystem");
        final ActorRef server = actorSystem.actorOf(ClientActor.props(), "client");
        server.tell(new ClientActor.StartClientMessage("start"), ActorRef.noSender());
        return "/mainPage";
    }
}
