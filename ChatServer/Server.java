import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;

public class Server extends JFrame implements ActionListener
{
	/**
	服务器端主程序负责界面，以及启动ServerThread服务线程
	*/

	//边框容器
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	//创建面板
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	//创建按钮
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	//创建可滑动面板
	JScrollPane jScrollPane1 = new JScrollPane();
	//创建服务器端接收信息文本框
	static JTextArea jTextArea1 = new JTextArea();
	boolean bool = false, start = false;
	//声明ServerThread线程类对象
	ServerThread serverThread;
	Thread thread;

	//创建构造函数
	public Server()
	{
		super("Server");
		//设置内容面板布局方式
		getContentPane().setLayout(borderLayout1);
		//初始化按钮组件
		jButton1.setText("启动服务器");
		jButton1.addActionListener(this);
		jButton2.setText("关闭服务器");
		jButton2.addActionListener(this);
		//初始化jPanel1面板对象
		this.getContentPane().add(jPanel1,java.awt.BorderLayout.NORTH);
		jPanel1.add(jButton1);
		jPanel1.add(jButton2);

		//初始化jPanel2面板对象，并向其中加入组件
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
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//将窗口设置为可关闭
	}

	//服务器界面中按钮事件处理
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == jButton1)
		{
			//开启ServerThread线程
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