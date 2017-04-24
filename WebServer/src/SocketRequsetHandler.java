import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.logging.Logger;

import sun.util.logging.resources.logging;
public class SocketRequsetHandler implements Runnable {
	
	private SocketChannel socketChannel;
	private static final Logger log = Logger.getLogger(SocketRequsetHandler.class.getName());  
	
	public SocketRequsetHandler (SocketChannel socketChannel)
	{
		this.socketChannel = socketChannel;
	}
	
	public void run()
	{
		//log.info("111");
		try
		{
			String file = "C:\\Users\\asus\\Documents\\GitHub\\WebServer\\WebServer\\src\\index.html";
			FileInputStream inputStream = new FileInputStream(new File(file));
			FileChannel channel = inputStream.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int length = -1;
			
			while ((length = channel.read(buffer)) != -1)
			{
				buffer.flip();
				buffer.clear();
			}
			inputStream.close();
			channel.close();
			ByteBuffer output = ByteBuffer.allocate(1024);
			CharsetDecoder decoder = Charset.forName("GBK").newDecoder();
			StringBuilder s = new StringBuilder();
			String html = decoder.decode(buffer).toString();
			//System.out.println(html);
			s.append("HTTP/1.0 200 OK").append("\r\n");
			s.append("MIME_version:1.0").append("\r\n");
			s.append("Content_Type:text/html").append("\r\n");
			s.append("Content_Length:" + 1000).append("\r\n");
            s.append("\r\n");
            s.append(html).append("\r\n");
            s.append("\r\n");
            output = ByteBuffer.wrap(s.toString().getBytes());
            //log.info(decoder.decode(buffer).toString());
            socketChannel.write(output);
            //socketChannel.write(buffer);
		}
		catch (Exception e)
		{
			
		}
	}
}
