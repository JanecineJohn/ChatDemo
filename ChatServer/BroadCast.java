import java.io.*;

public class BroadCast extends Thread
{
	//服务器向客户端广播线程
	ClientThread clientThread;
	//声明ServerThread对象
	ServerThread serverThread;
	String str;

	public BroadCast(ServerThread serverThread)
	{
		this.serverThread = serverThread;
	}

	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(200);//线程休眠0.2秒
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			//同步化serverThread.messages
			synchronized(serverThread.messages)
			{
				//判断是否有未发信息
				if (serverThread.messages.isEmpty())
				{
					continue;
				}
				//获取服务器端存储的需要发送的第一条数据信息
				str = (String) this.serverThread.messages.firstElement();
			}
			synchronized(serverThread.clients)
			{
				//利用循环获取服务器中存储的所有与客户端建立的连接
				for (int i = 0;i < serverThread.clients.size();i++ )
				{
					clientThread = (ClientThread) serverThread.clients.elementAt(i);
					try
					{
						//向记录的每一个客户端发送数据信息
						//获取此客户端的输出流，向输出流发送得到的数据信息
						clientThread.out.writeUTF(str);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				//从Vector数组中删除已经发送过的那条数据信息
				this.serverThread.messages.removeElement(str);
			}
		}
	}
}