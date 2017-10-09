import java.net.*;
import java.io.*;
public class ClientThread extends Thread
{
	/*ά�ַ������뵥���ͻ��˵������̣߳�������տͻ��˷�������Ϣ��
	����һ���µ�Socket�������ڱ������������accept�����õ��Ŀͻ�������*/
	Socket clientSocket;

	//�������������д洢��Socket�������������/�����
	DataInputStream in = null;
	DataOutputStream out = null;

	//����ServerThread����
	ServerThread serverThread;

	public ClientThread(Socket socket,ServerThread serverThread)
	{
		clientSocket = socket;
		this.serverThread = serverThread;
		try
		{
			//��������������������/�����
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		}
		catch (IOException e2)
		{
			System.out.println("�����쳣��" + e2);
			System.out.println("����I/Oͨ��ʧ��");
			System.exit(3);
		}
	}
	public void run()
	{
		while (true)
		{
			try
			{
				//����ͻ��˷���������Ϣ
				String message = in.readUTF();
				synchronized(serverThread.messages)
				{
					if (message != null)
					{
						//���ͻ��˷���������Ϣ�浽serverThread��messages������
						serverThread.messages.addElement(message);
						//�ڷ������˵��ı�������ʾ����Ϣ
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