import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class Acceptor implements Runnable{
	private Reactor reactor;
	private static final Logger log = Logger.getLogger(Acceptor.class.getName());  
	
	public Acceptor(Reactor reactor)
	{
		this.reactor = reactor;
	}
	

	public void run() 
	{
		try
		{
			SocketChannel socketChannel = reactor.serverSocketChannel.accept();
			log.info("Server: accept client socket " + socketChannel);  
			socketChannel.configureBlocking(false);
			if (socketChannel != null)
			{
				log.info("Acceptor is running now");
				SocketReadHandler s = new SocketReadHandler(reactor.selector, socketChannel);
				s.run();
			}
		}
		catch (IOException e)
		{
			// TODO: handle exception
		}
	}
}
