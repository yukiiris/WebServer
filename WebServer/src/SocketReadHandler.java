import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.logging.Logger;

public class SocketReadHandler {

	private SocketChannel socketChannel;
	private static final Logger log = Logger.getLogger(SocketReadHandler.class.getName());  
	
	public SocketReadHandler(Selector selector, SocketChannel socketChannel) throws IOException
	{
		this.socketChannel = socketChannel;
		socketChannel.configureBlocking(false);
		
		SelectionKey key = socketChannel.register(selector, 0);
		key.attach(this);
		
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		selector.wakeup();
	}
	
	public void run()
	{	
		try
		{		
			SocketRequsetHandler socketRequsetHandler = new SocketRequsetHandler(socketChannel);
			socketRequsetHandler.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
