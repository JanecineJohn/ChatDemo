import java.net.*;
import java.io.*;

public class Test
{
	public static void main(String[] args)
	{
		try
		{
			Socket socket = new Socket("192.168.56.1",4800);
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.writeUTF("����һ��������Ϣ");

		}
		catch (IOException e)
		{
		}
	}
}