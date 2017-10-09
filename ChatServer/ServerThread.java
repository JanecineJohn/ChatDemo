import java.util.*;
import java.io.*;
import java.net.*;

public class ServerThread extends Thread
{
	//�����������˿��߳�

	//����ServerSocket�����
	ServerSocket serverSocket;
	public static final int PORT = 4800;//ָ�������������˿ڳ���

	/*����һ��Vector�������ڴ洢�ͻ������ӵ�ClientThread����ClientThread��ά�ַ������뵥���ͻ���
	�����ӣ�������տͻ��˷�������Ϣ��clients����洢������������������ӵĿͻ���*/
	Vector<ClientThread> clients;
	//����һ��Vector�������ڴ洢�ͻ��˷��͹�������Ϣ
	Vector<Object> messages;
	//BroadCast�ฺ���������ͻ��˹㲥��Ϣ
	BroadCast broadcast;

	String ip;
	InetAddress myIPaddress = null;

	public ServerThread()
	{
		/*��������Vector���飬clients����洢������������������ӵĿͻ��ˣ�
		messages����洢���������ܵ���δ���ͳ�ȥ��ȫ���ͻ��˵���Ϣ*/
		clients = new Vector<ClientThread>();
		messages = new Vector<Object>();

		try
		{
			//����ServerSocket�����
			serverSocket = new ServerSocket(PORT);//����������Socket������PORT�˿�
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			//��ȡ����IP��ַ��Ϣ
			myIPaddress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			System.out.println(e.toString());
		}
		ip = myIPaddress.getHostAddress();
		Server.jTextArea1.append("��������ַ��" + ip + "�˿ںţ�" + 
			String.valueOf(serverSocket.getLocalPort()) + "\n");

		//�����㲥��Ϣ�̲߳�����
		broadcast = new BroadCast(this);
		broadcast.start();
	}

	public void run()
	{
		while(true)
		{
			try
			{
				//��ȡ�ͻ������ӣ�������һ���µ�Socket����
				Socket socket = serverSocket.accept();
				//��ӡ�����ӵ��������Ŀͻ��˵�ַ
				System.out.println(socket.getInetAddress().getHostAddress());

				//����һ����������ͻ������ӵ����߳�
				ClientThread clientThread = new ClientThread(socket,this);
				clientThread.start();
				if (socket != null)
				{
					synchronized(clients)
					{
						//���ͻ������Ӽ��뵽Vector�����б���
						clients.addElement(clientThread);
					}
				}
			}
			catch (IOException e)
			{
				System.out.println("�����쳣��" + e);
				System.out.println("�����ͻ�������ʧ�ܣ�");
				System.exit(2);
			}
		}
	}
	public void finalize()
	{
		try
		{
			//�ر�serverSocket
			serverSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		serverSocket = null;
	}
}