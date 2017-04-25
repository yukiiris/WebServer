import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//import java.util.logging.Logger;
public class SocketRequsetHandler implements Runnable {
	
	private SocketChannel socketChannel;
	//private static final Logger log = Logger.getLogger(SocketRequsetHandler.class.getName());  
	public String filename;
	
	public SocketRequsetHandler (SocketChannel socketChannel, String filename)
	{
		this.socketChannel = socketChannel;
		this.filename = filename;
	}
	
	public void run()
	{
		try
		{
			CharsetDecoder decoder = Charset.forName("GBK").newDecoder();
			Path path = Paths.get("WebServer/src/" + filename);
			if (Files.exists(path))
			{		
				FileInputStream inputStream = new FileInputStream(new File("WebServer/src/" + filename));
				FileChannel channel = inputStream.getChannel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				
				while ((channel.read(buffer)) != -1)
				{
					buffer.flip();
					buffer.clear();
				}
				inputStream.close();
				channel.close();
				ByteBuffer output = ByteBuffer.allocate(1024);
				StringBuilder s = new StringBuilder();
				String html = decoder.decode(buffer).toString();
				s.append("HTTP/1.0 200 OK").append("\r\n");
				s.append("MIME_version:1.0").append("\r\n");
				s.append("Content_Type:text/html").append("\r\n");
				s.append("Content_Length:" + 1000).append("\r\n");
	            s.append("\r\n");
	            s.append(html).append("\r\n");
	            s.append("\r\n");
	            output = ByteBuffer.wrap(s.toString().getBytes());
	            socketChannel.write(output);
			}
			else
			{
				StringBuilder result = new StringBuilder();
				result.append("HTTP/1.1 400 bad request Connection: close");
				result.append(" ");
				ByteBuffer output = ByteBuffer.allocate(1024);
				output = ByteBuffer.wrap(result.toString().getBytes());
				socketChannel.write(output);
			}
		}
		catch (Exception e)
		{
			
		}
	}
}
