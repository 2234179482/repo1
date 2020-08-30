package ȫ��̹�˴�ս;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static final int ZDSD=35;//�ӵ��ٶȳ���

	public static final int WIDTH=10;
	public static final int HEIGHT=10;

	int x,y;
	Direction Dir;//̹�˵ķ���

	private TankClient tc;

	private boolean live=true;//�ж��ӵ��Ƿ�����

	private boolean good;//̹�˵ĺû�

	public boolean getLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Missile(int x,int y,Direction Dir) {//���캯����ʼ���ӵ���λ�úͷ���
		this.x=x;
		this.y=y;
		this.Dir=Dir;
	} 

	public Missile(int x,int y,Direction Dir,TankClient tc,boolean good) {
		this.x=x;
		this.y=y;
		this.Dir=Dir;
		this.tc=tc;
		this.good=good;
	} 
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image Missileimage[]=null;
	private static Map<String,Image> image=new HashMap<String,Image>();
	
	static {
		Image Missileimage[]=new Image[]{
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileLD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("Image/missileU.gif"))
		}; 
		image.put("x",Missileimage[0]);
		image.put("z",Missileimage[1]);
		image.put("zx",Missileimage[2]);
		image.put("zs",Missileimage[3]);
		image.put("y",Missileimage[4]);
		image.put("yx",Missileimage[5]);
		image.put("ys",Missileimage[6]);
		image.put("s",Missileimage[7]);
		
	}

	public void draw(Graphics g) {//�ӵ��Ļ���
		if(Dir==Direction.z) {//�ӵ����ƶ�
			g.drawImage(image.get("z"),x,y,null);
			x-=ZDSD;
		}else if(Dir==Direction.zs) {
			g.drawImage(image.get("zs"),x,y,null);
			x-=ZDSD;
			y-=ZDSD;
		}else if(Dir==Direction.zx) {
			g.drawImage(image.get("zx"),x,y,null);
			x-=ZDSD;
			y+=ZDSD;
		}else if(Dir==Direction.y) {
			g.drawImage(image.get("y"),x,y,null);
			x+=ZDSD;
		}else if(Dir==Direction.ys) {
			g.drawImage(image.get("ys"),x,y,null);
			x+=ZDSD;
			y-=ZDSD;
		}else if(Dir==Direction.yx) {
			g.drawImage(image.get("yx"),x,y,null);
			x+=ZDSD;
			y+=ZDSD;
		}else if(Dir==Direction.s) {
			g.drawImage(image.get("s"),x,y,null);
			y-=ZDSD;
		}else if(Dir==Direction.x) {
			g.drawImage(image.get("x"),x,y,null);
			y+=ZDSD;
		}
		if(x<0 || y<0 || x>TankClient.GAME_WINTH || y>TankClient.GAME_HEIGHT) {
			setLive(false);
		}
	}


	public Rectangle getRect() {//ͼ�α߿�Ĵ���
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}

	public boolean hitTank(Tank t) {//�ж��Ƿ����
		if(this.live==true && this.getRect().intersects(t.getRect()) && t.getLive()==true && this.good != t.getGood()) {
			if(t.getGood()==true) {
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0) {
					Explode e=new Explode(x,y,tc);
					tc.explode.add(e);
					t.setLive(false);//̹������
					this.setLive(false);
				}else {
					this.setLive(false);//�ӵ�����
					return true;
				}
			}else {
				Explode e=new Explode(x,y,tc);
				tc.explode.add(e);
				t.setLive(false);
				this.setLive(false);
			}
		}
		return false;
	}


	public boolean hitTanks(List<Tank> tanks) {//���ж���̹��
		for(int i=0;i<tanks.size();i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	public boolean hitWall(Wall w) {//�ӵ����ܴ�ǽ
		if(this.live==true && this.getRect().intersects(w.getRect())) {
			live=false;
			return true;
		}
		return false;
	}

//	public boolean sctk(List<Tank> tanks) {//�Լ���������̹�˵��ж�
//		if(tc.tanks.size()==0) {
//			return true;	
//		}
//		return false;
//	}
//	
//	public void scdt() {//�Լ��������ɵ���̹�˵ķ���
//		if(sctk(tc.tanks)==true) {
//			for(int i=0;i<3;i++) {
//				tc.tanks.add(new Tank(50+40*(i+1),50,false,tc,Tank.Direction.x));
//			}
//		}
//	}
}
