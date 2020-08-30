package 全新坦克大战;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	int x,y,w,h;
	TankClient tc;

	int step=0;

	private boolean live=true;

	public boolean getLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int pos[][]= {
			{150,100},{150,110},{150,120},{150,130},{160,130},{170,130},{180,130},{180,140},{180,130},{170,130}
	};
	
	private int pos1[][]= {
			{650,400},{650,390},{650,380},{650,370},{650,360},{640,360},{630,360},{620,360},{610,360},{650,370}
	};

	public Blood(int w,int h) {
		x=pos[0][0];
		y=pos[0][1];
		this.w=w;
		this.h=h;
	}
	public Blood(int w,int h,TankClient tc) {
		x=pos[0][0];
		y=pos[0][1];
		this.w=w;
		this.h=h;
		this.tc=tc;
	}

	public void draw(Graphics g) {
		if(live==true) {
			Color c=g.getColor();
			g.setColor(Color.MAGENTA);
			g.fillRect(x, y, w, h);
			g.setColor(c);

			move();	
		}
	}
	public void draw1(Graphics g) {
		if(live==true) {
			Color c=g.getColor();
			g.setColor(Color.MAGENTA);
			g.fillRect(x, y, w, h);
			g.setColor(c);

			move1();	
		}
	}

	public void move() {
		step++;
		if(step==pos.length) {
			step=0;
		}else {
			x=pos[step][0];
			y=pos[step][1];
		}
	}
	public void move1() {
		step++;
		if(step==pos.length) {
			step=0;
		}else {
			x=pos1[step][0];
			y=pos1[step][1];
		}
	}

	public Rectangle getRect() {//图形边框的触碰
		return new Rectangle(x,y,w,h);
	}
}
