package 全新坦克大战;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static final int ZDSD=35;//子弹速度常量

	public static final int WIDTH=10;
	public static final int HEIGHT=10;

	int x,y;
	Direction Dir;//坦克的方向

	private TankClient tc;

	private boolean live=true;//判断子弹是否死亡

	private boolean good;//坦克的好坏

	public boolean getLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Missile(int x,int y,Direction Dir) {//构造函数初始化子弹的位置和方向
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

	public void draw(Graphics g) {//子弹的画板
		if(Dir==Direction.z) {//子弹的移动
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


	public Rectangle getRect() {//图形边框的触碰
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}

	public boolean hitTank(Tank t) {//判断是否击中
		if(this.live==true && this.getRect().intersects(t.getRect()) && t.getLive()==true && this.good != t.getGood()) {
			if(t.getGood()==true) {
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0) {
					Explode e=new Explode(x,y,tc);
					tc.explode.add(e);
					t.setLive(false);//坦克死亡
					this.setLive(false);
				}else {
					this.setLive(false);//子弹死亡
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


	public boolean hitTanks(List<Tank> tanks) {//击中多辆坦克
		for(int i=0;i<tanks.size();i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	public boolean hitWall(Wall w) {//子弹不能穿墙
		if(this.live==true && this.getRect().intersects(w.getRect())) {
			live=false;
			return true;
		}
		return false;
	}

//	public boolean sctk(List<Tank> tanks) {//自己做的生成坦克的判断
//		if(tc.tanks.size()==0) {
//			return true;	
//		}
//		return false;
//	}
//	
//	public void scdt() {//自己做的生成敌人坦克的方法
//		if(sctk(tc.tanks)==true) {
//			for(int i=0;i<3;i++) {
//				tc.tanks.add(new Tank(50+40*(i+1),50,false,tc,Tank.Direction.x));
//			}
//		}
//	}
}
