import java.io.*;
import java.net.*;

/*
暂时用的是单线程完成C/S交互
*/
public class ChatServer
{
	public static void main(String[] args)
	{
		ServerSocket server = null;
		Socket client = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try{
			server = new ServerSocket(4700);//服务器监听的端口号为4700
			client = server.accept();//将接收到的客户端socket封装成对象
			String ip = client.getInetAddress().getHostAddress();
			System.out.println(ip + "进入聊天室");
			out = new PrintWriter(client.getOutputStream());//获取客户端的输出流，并封装成对象
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));//获取客户端输入流，封装成对象
			String line = in.readLine();//将输入流的数据读取到line中(逐行读取)
			while(line != null)
			{
				System.out.println(ip + "：" + line);
				out.println(ip + ":" + line);
				out.flush();
				line = in.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}