import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.logging.Logger; 

public class Reactor implements Runnable {

	public ServerSocketChannel serverSocketChannel;
	public Selector selector;
	private static final Logger log = Logger.getLogger(Reactor.class.getName());  
	public Reactor(int port) throws IOException
	{
		selector = Selector.open();
		
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(8000));
		serverSocketChannel.configureBlocking(false);

		SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		key.attach(new Acceptor(this));
		log.info("Reactor created successfully!");
	}
	
	public void run() 
	{
		try
		{
			while (true)
			{
				selector.select();
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			
				while (it.hasNext())
				{		
					SelectionKey selKey = (SelectionKey) it.next();
					log.info("Connected successfully!");
					dispatch(selKey);
					//it.remove();
					//it.remove();
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	void dispatch(SelectionKey key)
	{
		Runnable k = (Runnable)key.attachment();
		if (k != null)
		{
			k.run();
		}
	}
	public static void main(String[] args) throws IOException
	{
		Reactor reactor = new Reactor(8000);
		reactor.run();
	}
	
}
