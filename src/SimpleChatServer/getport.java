package SimpleChatServer;

public class getport extends ServerCommand {

    public getport(String str, EchoServer1 server) {
        super(str, server);
    }//end getport

    public void doCommand() {
        getServer().serverUI().display("The port number is: " +
                getServer().getPort());
    }//end doCommand()
}
