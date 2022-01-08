package FINAL.project;
import java.io.IOException;

public class AppServer 
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(3000);
        server.listen();
        
        
     
    }

}
	
