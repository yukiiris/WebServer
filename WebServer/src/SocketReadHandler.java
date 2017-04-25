import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class SocketReadHandler {

	private SocketChannel socketChannel;
	//private static final Logger log = Logger.getLogger(SocketReadHandler.class.getName());  
	private String file;
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
			CharsetDecoder decoder = Charset.forName("GBK").newDecoder();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			socketChannel.read(buffer);
			buffer.flip();
			String input = decoder.decode(buffer).toString();
			System.out.println(input);
			if (input.startsWith("GET") || input.startsWith("get"))
			{
				String[] split = input.split("\r");
				String[] result = split[0].split(" ");
				System.out.println(split[0]);
				if (!result[2].equals("HTTP/1.1"))
				{
					System.out.println("3");
				}
				else if (result[1].startsWith("/"))
				{
					file = result[1].substring(1);
					System.out.println(file);
				}
				
			}
			SocketRequsetHandler socketRequsetHandler = new SocketRequsetHandler(socketChannel, file);
			socketRequsetHandler.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
