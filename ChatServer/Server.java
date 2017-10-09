import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;

public class Server extends JFrame implements ActionListener
{
	/**
	������������������棬�Լ�����ServerThread�����߳�
	*/

	//�߿�����
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	//�������
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	//������ť
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	//�����ɻ������
	JScrollPane jScrollPane1 = new JScrollPane();
	//�����������˽�����Ϣ�ı���
	static JTextArea jTextArea1 = new JTextArea();
	boolean bool = false, start = false;
	//����ServerThread�߳������
	ServerThread serverThread;
	Thread thread;

	//�������캯��
	public Server()
	{
		super("Server");
		//����������岼�ַ�ʽ
		getContentPane().setLayout(borderLayout1);
		//��ʼ����ť���
		jButton1.setText("����������");
		jButton1.addActionListener(this);
		jButton2.setText("�رշ�����");
		jButton2.addActionListener(this);
		//��ʼ��jPanel1������
		this.getContentPane().add(jPanel1,java.awt.BorderLayout.NORTH);
		jPanel1.add(jButton1);
		jPanel1.add(jButton2);

		//��ʼ��jPanel2�����󣬲������м������
		jTextArea1.setText("");
		jPanel2.setLayout(borderLayout2);
		jPanel2.add(jScrollPane1,java.awt.BorderLayout.CENTER);
		jScrollPane1.getViewport().add(jTextArea1);
		this.getContentPane().add(jPanel2,java.awt.BorderLayout.CENTER);
		this.setSize(400,400);
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		Server server = new Server();
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����������Ϊ�ɹر�
	}

	//�����������а�ť�¼�����
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == jButton1)
		{
			//����ServerThread�߳�
			serverThread = new ServerThread();
			serverThread.start();
		}else if(e.getSource() == jButton2)
		{
			bool = false;
			start = false;
			serverThread.finalize();
			this.setVisible(false);
		}
	}
}