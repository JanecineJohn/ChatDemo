import java.net.*;
import java.io.*;
public class ClientThread extends Thread
{
	/*维持服务器与单个客户端的连接线程，负责接收客户端发来的信息，
	声明一个新的Socket对象，用于保存服务器端用accept方法得到的客户端连接*/
	Socket clientSocket;

	//声明服务器端中存储的Socket对象的数据输入/输出流
	DataInputStream in = null;
	DataOutputStream out = null;

	//声明ServerThread对象
	ServerThread serverThread;

	public ClientThread(Socket socket,ServerThread serverThread)
	{
		clientSocket = socket;
		this.serverThread = serverThread;
		try
		{
			//创建服务器端数据输入/输出流
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		}
		catch (IOException e2)
		{
			System.out.println("发生异常：" + e2);
			System.out.println("建立I/O通道失败");
			System.exit(3);
		}
	}
	public void run()
	{
		while (true)
		{
			try
			{
				//读入客户端发送来的信息
				String message = in.readUTF();
				synchronized(serverThread.messages)
				{
					if (message != null)
					{
						//将客户端发送来的信息存到serverThread的messages数组中
						serverThread.messages.addElement(message);
						//在服务器端的文本框中显示新信息
						Server.jTextArea1.append(message + '\n');
					}
				}
			}
			catch (IOException e)
			{
				break;
			}
		}
	}
}