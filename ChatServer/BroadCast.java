import java.io.*;

public class BroadCast extends Thread
{
	//��������ͻ��˹㲥�߳�
	ClientThread clientThread;
	//����ServerThread����
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
				Thread.sleep(200);//�߳�����0.2��
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			//ͬ����serverThread.messages
			synchronized(serverThread.messages)
			{
				//�ж��Ƿ���δ����Ϣ
				if (serverThread.messages.isEmpty())
				{
					continue;
				}
				//��ȡ�������˴洢����Ҫ���͵ĵ�һ��������Ϣ
				str = (String) this.serverThread.messages.firstElement();
			}
			synchronized(serverThread.clients)
			{
				//����ѭ����ȡ�������д洢��������ͻ��˽���������
				for (int i = 0;i < serverThread.clients.size();i++ )
				{
					clientThread = (ClientThread) serverThread.clients.elementAt(i);
					try
					{
						//���¼��ÿһ���ͻ��˷���������Ϣ
						//��ȡ�˿ͻ��˵������������������͵õ���������Ϣ
						clientThread.out.writeUTF(str);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				//��Vector������ɾ���Ѿ����͹�������������Ϣ
				this.serverThread.messages.removeElement(str);
			}
		}
	}
}