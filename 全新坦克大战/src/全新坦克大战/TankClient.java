package 全新坦克大战;

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
	public static final int GAME_WINTH=1000;//窗口的大小，常量
	public static final int GAME_HEIGHT=800;

	Image offScreenImage=null;

	List<Missile> missile=new ArrayList<Missile>();//坦克多课子弹储存的泛型
	List<Explode>explode=new ArrayList<Explode>();//爆炸的泛型
	List<Tank>tanks=new ArrayList<Tank>();//多辆敌人坦克储存

	Tank myTank=new Tank(50,50,true,this,Direction.stop);//把坦克类nuw出来给他定义构造函数
	Explode e=new Explode(150,150,this);//爆炸类的构造函数
	Wall w1=new Wall(250,300,50,270,this);
	Wall w2=new Wall(550,300,50,270,this);
	Blood b1=new Blood(15,15);
	Blood b2=new Blood(15,15,this);

	public static void main(String[] args) {//主函数
		TankClient a=new TankClient();
		a.lunchFrame();
	}

	public void update(Graphics g){//先paint之前运行的方法,作用是使画面变得流畅，在背后画一张图一次性显示
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

	public void paint(Graphics g) {//画板显示器
		super.paint(g);
		if(tanks.size()==0) {
			for(int i=0;i<5;i++) {//敌人坦克死亡添加敌人坦克
				tanks.add(new Tank(50+40*(i+1),50,false,this,Direction.x));
			}
		}
		g.drawString("子弹："+missile.size(), 870, 740);
		g.drawString("爆炸："+explode.size(), 870, 760);
		g.drawString("坦克："+tanks.size(),870, 720);
		g.drawString("血量："+myTank.getLife(), 870, 700);

		//		Color c=g.getColor();   自己的血条代码
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
				missile.remove(m);//如果子弹死了把子弹移除
//				m.scdt();//判断敌人坦克是否死光，死光重新加入，方法写在子弹类
			}else {
				m.hitWall(w1);
				m.hitWall(w2);
				m.hitTanks(tanks);
				m.hitTank(myTank);
				m.draw(g);
			}
		}

		for(int i=0;i<explode.size();i++) {//把爆炸添加进去
			Explode e=explode.get(i);
			e.draw(g);
		}

		for(int i=0;i<tanks.size();i++) {//把坦克添加进去
			Tank t=tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTank(tanks);
			t.draw(g);
		}
	}

	public void lunchFrame () {//窗口
		for(int i=0;i<10;i++) {//显示几辆坦克
			tanks.add(new Tank(50+40*(i+1),50,false,this,Direction.x));
		}
		
		this.setTitle("坦克大战");
		this.setSize(GAME_WINTH,GAME_HEIGHT);
		this.setLocation(400,100);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
//		this.getContentPane().setVisible(false);
//		这个方法可以让JFrame设置Backgroung方法成功作用是屏蔽ContentPane的，默认背景，
//		因为如果不屏蔽这个默认背景就看不到我们的Backgroung设置的背景
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("坦克大战图片/1111.png").getImage());
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		this.setBackground(Color.GREEN);

		//		new Thread(new PaintThread()).start();
		PaintThread a=new PaintThread();//调用PaintThread里面的run方法，上面是简洁方式，线程
		Thread b=new Thread(a);
		b.start();
	}

	private class PaintThread implements Runnable{//线程隔一会刷新一下界面
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

	private class KeyMonitor implements KeyListener{//坦克走路调用Tank里的键盘监听方法
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