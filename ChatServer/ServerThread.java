import java.util.*;
import java.io.*;
import java.net.*;

public class ServerThread extends Thread
{
	//服务器监听端口线程

	//声明ServerSocket类对象
	ServerSocket serverSocket;
	public static final int PORT = 4800;//指定服务器监听端口常量

	/*创建一个Vector对象，用于存储客户端连接的ClientThread对象，ClientThread类维持服务器与单个客户端
	的连接，负责接收客户端发来的信息，clients负责存储所有与服务器建立连接的客户端*/
	Vector<ClientThread> clients;
	//创建一个Vector对象，用于存储客户端发送过来的信息
	Vector<Object> messages;
	//BroadCast类负责服务器向客户端广播信息
	BroadCast broadcast;

	String ip;
	InetAddress myIPaddress = null;

	public ServerThread()
	{
		/*创建两个Vector数组，clients负责存储所有与服务器建立连接的客户端，
		messages负责存储服务器接受到的未发送出去的全部客户端的信息*/
		clients = new Vector<ClientThread>();
		messages = new Vector<Object>();

		try
		{
			//创建ServerSocket类对象
			serverSocket = new ServerSocket(PORT);//创建服务器Socket，监听PORT端口
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			//获取本地IP地址信息
			myIPaddress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			System.out.println(e.toString());
		}
		ip = myIPaddress.getHostAddress();
		Server.jTextArea1.append("服务器地址：" + ip + "端口号：" + 
			String.valueOf(serverSocket.getLocalPort()) + "\n");

		//创建广播信息线程并启动
		broadcast = new BroadCast(this);
		broadcast.start();
	}

	public void run()
	{
		while(true)
		{
			try
			{
				//获取客户端连接，并返回一个新的Socket对象
				Socket socket = serverSocket.accept();
				//打印出连接到服务器的客户端地址
				System.out.println(socket.getInetAddress().getHostAddress());

				//开启一条服务器与客户端连接的新线程
				ClientThread clientThread = new ClientThread(socket,this);
				clientThread.start();
				if (socket != null)
				{
					synchronized(clients)
					{
						//将客户端连接加入到Vector数组中保存
						clients.addElement(clientThread);
					}
				}
			}
			catch (IOException e)
			{
				System.out.println("发生异常：" + e);
				System.out.println("建立客户端联机失败！");
				System.exit(2);
			}
		}
	}
	public void finalize()
	{
		try
		{
			//关闭serverSocket
			serverSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		serverSocket = null;
	}
}