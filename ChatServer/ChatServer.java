import java.io.*;
import java.net.*;

/*
��ʱ�õ��ǵ��߳����C/S����
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
			server = new ServerSocket(4700);//�����������Ķ˿ں�Ϊ4700
			client = server.accept();//�����յ��Ŀͻ���socket��װ�ɶ���
			String ip = client.getInetAddress().getHostAddress();
			System.out.println(ip + "����������");
			out = new PrintWriter(client.getOutputStream());//��ȡ�ͻ��˵������������װ�ɶ���
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));//��ȡ�ͻ�������������װ�ɶ���
			String line = in.readLine();//�������������ݶ�ȡ��line��(���ж�ȡ)
			while(line != null)
			{
				System.out.println(ip + "��" + line);
				out.println(ip + ":" + line);
				out.flush();
				line = in.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}