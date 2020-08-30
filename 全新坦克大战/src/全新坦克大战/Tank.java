package ȫ��̹�˴�ս;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Tank {
	public static final int XFANGXIANG=15;//�ҷ�̹���ƶ��ľ��룬���������ó�������
	public static final int YFANGXIANG=15;

	public static final int XXFANGXIANG=5;//�ҷ�̹���ƶ��ľ��룬���������ó�������
	public static final int YYFANGXIANG=5;

	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	private BloodBar bb = new BloodBar();

	private int step=r.nextInt(15)+3;

	private static Random r=new Random();

	private boolean live=true;

	public boolean getLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int life=100;//̹�˵�Ѫ��

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}


	TankClient tc;//���ڴ�պ�

	int x,y;

	private int Xold;
	private int Yold;

	public void stay() {
		x=Xold;
		y=Yold;
	}

	private boolean good=true;//�ж�̹�˵ĺû�

	public boolean getGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}
	
	boolean zuo=false,shang=false,you=false,xia=false;//ö�ٷ���̹�˵ķ���
	private Direction Dir=Direction.stop;

	private Direction ptDir=Direction.x;
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image Tankimage[]=null;//�ڹ��������ﶨ��ĺô�������class¶�ڴ�ʱ��һ���ִ��
	private static Map<String,Image> image =new HashMap<String,Image>();
	
	static {
		Image Tankimage[]=new Image[]{
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankLD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/tankU.gif"))
		}; 
		image.put("x",Tankimage[0]);
		image.put("z",Tankimage[1]);
		image.put("zx",Tankimage[2]);
		image.put("zs",Tankimage[3]);
		image.put("y",Tankimage[4]);
		image.put("yx",Tankimage[5]);
		image.put("ys",Tankimage[6]);
		image.put("s",Tankimage[7]);
		
	}

	public void move() {//̹�˰��������ķ�������ߵľ���
		if(good!=true) {
			Direction dirs[]=Direction.values();
			if(step==0) {
				int rn=r.nextInt(dirs.length);
				step=r.nextInt(15)+3;
				Dir=dirs[rn];
			}else {
				step--;
			}
		}
		if(good==false) {

			this.Xold=x;
			this.Yold=y;

			switch(Dir) {
			case z:
				x -= XXFANGXIANG;
				break;
			case zs:
				x -= XXFANGXIANG;
				y -= YYFANGXIANG;
				break;
			case s:
				y -= YYFANGXIANG;
				break;
			case ys:
				x += XXFANGXIANG;
				y -= YYFANGXIANG;
				break;
			case y:
				x += XXFANGXIANG;
				break;
			case yx:
				x += XXFANGXIANG;
				y += YYFANGXIANG;
				break;
			case x:
				y += YYFANGXIANG;
				break;
			case zx:
				x -= XXFANGXIANG;
				y += YYFANGXIANG;
				break;
			case stop:
				break;
			}
			if(r.nextInt(40)>35) {
				this.fire();
			}
		}else if(good==true) {
			switch(Dir) {
			case z:
				x -= XFANGXIANG;
				break;
			case zs:
				x -= XFANGXIANG;
				y -= YFANGXIANG;
				break;
			case s:
				y -= YFANGXIANG;
				break;
			case ys:
				x += XFANGXIANG;
				y -= YFANGXIANG;
				break;
			case y:
				x += XFANGXIANG;
				break;
			case yx:
				x += XFANGXIANG;
				y += YFANGXIANG;
				break;
			case x:
				y += YFANGXIANG;
				break;
			case zx:
				x -= XFANGXIANG;
				y += YFANGXIANG;
				break;
			case stop:
				break;
			}
		}

		if(this.Dir!=Direction.stop) {//̹����������ڹ��Զ�ָ�����һ������
			ptDir=Dir;
		}
		if(x<0) {//��ֹ̹�˳���
			x=0;
		}
		if(y<30) {
			y=30;
		}
		if(x+Tank.WIDTH>TankClient.GAME_WINTH) {
			x=TankClient.GAME_WINTH-Tank.WIDTH;
		}
		if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT){
			y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		}
	}


	public void locateDirection() {//�жϸ��������true����falseȡ����̹�˸ò���ת
		if(shang==true && zuo!=true && you!=true && xia!=true) {
			Dir=Direction.s;
		}else if(zuo==true && shang!=true && you!=true && xia!=true) {
			Dir=Direction.z;
		}else if(xia==true && shang!=true && you!=true && zuo!=true) {
			Dir=Direction.x;
		}else if(you==true && shang!=true && xia!=true && zuo!=true) {
			Dir=Direction.y;
		}else if(you==true && shang==true && xia!=true && zuo!=true) {
			Dir=Direction.ys;
		}else if(you==true && xia==true && shang!=true && zuo!=true) {
			Dir=Direction.yx;
		}else if(zuo==true && shang==true && xia!=true && you!=true) {
			Dir=Direction.zs;
		}else if(zuo==true && xia==true && shang!=true && you!=true) {
			Dir=Direction.zx;
		}else if(zuo!=true && xia!=true && shang!=true && you!=true) {
			Dir=Direction.stop;
		}

	}

	
	public Tank(int x,int y,boolean good){//̹�˵Ĺ��캯��
		this.x=x;
		this.y=y;
		this.Xold=x;
		this.Yold=y;
		this.good=good;
	}

	public Tank(int x,int y,boolean good,TankClient tc) {//̹�˵Ĺ��캯��������̹�˵ĺû�
		this.x=x;
		this.y=y;
		this.good=good;
		this.tc=tc;
	}

	public Tank(int x,int y,boolean good,TankClient tc,Direction Dir) {//����̹�˵ķ���
		this.x=x;
		this.y=y;
		this.good=good;
		this.tc=tc;
		this.Dir=Dir;
	}

	public void draw(Graphics g) {//̹�˵Ļ���
		if(live==false) {
			if(good==false) {
				tc.tanks.remove(this);
			}
		}
		if(good==true && live==true) {
			bb.draw(g);
		}
		if(this.live==false) {

		}else if(live==true) {
			if(ptDir==Direction.z) {
				g.drawImage(image.get("z"), x, y,null);
			}
			else if(ptDir==Direction.zs) {
				g.drawImage(image.get("zs"), x, y,null);
			}
			else if(ptDir==Direction.zx) {
				g.drawImage(image.get("zx"), x, y,null);
			}
			else if(ptDir==Direction.y) {
				g.drawImage(image.get("y"), x, y,null);
			}
			else if(ptDir==Direction.ys) {
				g.drawImage(image.get("ys"), x, y,null);
			}
			else if(ptDir==Direction.yx) {
				g.drawImage(image.get("yx"), x, y,null);
			}
			else if(ptDir==Direction.s) {
				g.drawImage(image.get("s"), x, y,null);
			}
			else if(ptDir==Direction.x) {
				g.drawImage(image.get("x"), x, y,null);
			}
			move();
		}
	}

	public void keyPressed(KeyEvent e) {//̹������
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			zuo = true;
			break;
		case KeyEvent.VK_UP :
			shang = true;
			break;
		case KeyEvent.VK_RIGHT :
			you = true;
			break;
		case KeyEvent.VK_DOWN :
			xia = true;
			break;
		case KeyEvent.VK_I :
			superFire();
			break;
		case KeyEvent.VK_F2:
			if(live==false) {
				this.live=true;
				this.life=100;
				break;
			}
		}
		locateDirection();
	}
	public void keyReleased(KeyEvent e) {//�ɿ����̴���
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT :
			zuo = false;
			break;
		case KeyEvent.VK_UP :
			shang = false;
			break;
		case KeyEvent.VK_RIGHT :
			you = false;
			break;
		case KeyEvent.VK_DOWN :
			xia = false;
			break;
		}
		locateDirection();
	}
	public Missile fire() {//�ӵ�����ķ����������Ǹ��ӵ��Ĺ��캯����ֵ
		if(live==false) {
			return null;
		}else {
			int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
			int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
			Missile m=new Missile(x,y,ptDir,this.tc,good);
			tc.missile.add(m);//����ӵ�
			return m;
		}
	}

	public Missile fire(Direction dir) {
		if(live==false) {
			return null;
		}else {
			int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
			int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
			Missile m=new Missile(x,y,dir,this.tc,good);
			tc.missile.add(m);//����ӵ�
			return m;
		}
	}

	public void superFire() {//��ǿ����
		Direction dirs[]=Direction.values();
		for(int i=0;i<8;i++) {
			fire(dirs[i]);
		}
	}

	public Rectangle getRect() {//ͼ�α߿�Ĵ���
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}

	public boolean collidesWithWall(Wall w) {//̹�˲��ܴ�ǽ
		if(this.live==true && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesWithTank(java.util.List<Tank> Tanks) {//����̹�˲��ܴ�ǽ
		for(int i=0;i<Tanks.size();i++) {
			Tank t=Tanks.get(i);
			if(this!=t) {
				if(this.live==true && t.live==true && this.getRect().intersects(t.getRect())) {
					this.stay();
					return true;
				}
			}
		}
		return false;
	}

	private class BloodBar{
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH * life/100 ;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}

	public void eat(Blood b) {
		if(this.live==true && b.getLive()==true && this.getRect().intersects(b.getRect())) {
			this.life=100;
			b.setLive(false);
		}
	}
}

