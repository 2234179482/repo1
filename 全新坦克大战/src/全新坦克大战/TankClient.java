package ȫ��̹�˴�ս;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class TankClient extends Frame {
	public static final int GAME_WINTH=1000;//���ڵĴ�С������
	public static final int GAME_HEIGHT=800;

	Image offScreenImage=null;

	List<Missile> missile=new ArrayList<Missile>();//̹�˶���ӵ�����ķ���
	List<Explode>explode=new ArrayList<Explode>();//��ը�ķ���
	List<Tank>tanks=new ArrayList<Tank>();//��������̹�˴���

	Tank myTank=new Tank(50,50,true,this,Direction.stop);//��̹����nuw�����������幹�캯��
	Explode e=new Explode(150,150,this);//��ը��Ĺ��캯��
	Wall w1=new Wall(250,300,50,270,this);
	Wall w2=new Wall(550,300,50,270,this);
	Blood b1=new Blood(15,15);
	Blood b2=new Blood(15,15,this);

	public static void main(String[] args) {//������
		TankClient a=new TankClient();
		a.lunchFrame();
	}

	public void update(Graphics g){//��paint֮ǰ���еķ���,������ʹ�������������ڱ���һ��ͼһ������ʾ
		if(offScreenImage==null) {
			offScreenImage=this.createImage(1000,800);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, 1000, 800);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage,0,0,this);
	}

	public void paint(Graphics g) {//������ʾ��
		super.paint(g);
		if(tanks.size()==0) {
			for(int i=0;i<5;i++) {//����̹��������ӵ���̹��
				tanks.add(new Tank(50+40*(i+1),50,false,this,Direction.x));
			}
		}
		g.drawString("�ӵ���"+missile.size(), 870, 740);
		g.drawString("��ը��"+explode.size(), 870, 760);
		g.drawString("̹�ˣ�"+tanks.size(),870, 720);
		g.drawString("Ѫ����"+myTank.getLife(), 870, 700);

		//		Color c=g.getColor();   �Լ���Ѫ������
		//		g.setColor(Color.RED);
		//		g.fillRect(myTank.x, myTank.y-15, myTank.getLife(), 10);
		//		g.setColor(c);

		//		if(myTank.getLive()==true) {
		//		g.setColor(Color.RED);
		//		g.drawRect(myTank.x, myTank.y-15, 32, 10);
		//		g.setColor(c);
		//		}
		myTank.draw(g);
		w1.draw(g);
		w2.draw(g);
		b1.draw(g);
		myTank.eat(b1);
		b2.draw1(g);
		myTank.eat(b2);
		for(int i=0;i<missile.size();i++) {
			Missile m=missile.get(i);
			if(m.getLive()==false) {
				missile.remove(m);//����ӵ����˰��ӵ��Ƴ�
//				m.scdt();//�жϵ���̹���Ƿ����⣬�������¼��룬����д���ӵ���
			}else {
				m.hitWall(w1);
				m.hitWall(w2);
				m.hitTanks(tanks);
				m.hitTank(myTank);
				m.draw(g);
			}
		}

		for(int i=0;i<explode.size();i++) {//�ѱ�ը��ӽ�ȥ
			Explode e=explode.get(i);
			e.draw(g);
		}

		for(int i=0;i<tanks.size();i++) {//��̹����ӽ�ȥ
			Tank t=tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTank(tanks);
			t.draw(g);
		}
	}

	public void lunchFrame () {//����
		for(int i=0;i<10;i++) {//��ʾ����̹��
			tanks.add(new Tank(50+40*(i+1),50,false,this,Direction.x));
		}
		
		this.setTitle("̹�˴�ս");
		this.setSize(GAME_WINTH,GAME_HEIGHT);
		this.setLocation(400,100);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
//		this.getContentPane().setVisible(false);
//		�������������JFrame����Backgroung�����ɹ�����������ContentPane�ģ�Ĭ�ϱ�����
//		��Ϊ������������Ĭ�ϱ����Ϳ��������ǵ�Backgroung���õı���
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("̹�˴�սͼƬ/1111.png").getImage());
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		this.setBackground(Color.GREEN);

		//		new Thread(new PaintThread()).start();
		PaintThread a=new PaintThread();//����PaintThread�����run�����������Ǽ�෽ʽ���߳�
		Thread b=new Thread(a);
		b.start();
	}

	private class PaintThread implements Runnable{//�̸߳�һ��ˢ��һ�½���
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(60);
				}catch(Exception e) {

				}
			}
		}
	}

	private class KeyMonitor implements KeyListener{//̹����·����Tank��ļ��̼�������
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		public void keyTyped(KeyEvent e) {

		}
	}


}