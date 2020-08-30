package 全新坦克大战;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	int x,y,w,h;
	TankClient tc;
	
	public Wall(int x,int y,int w,int h,TankClient tc) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.tc=tc;
	}
	
	public void draw(Graphics g) {
		Color c=g.getColor();
		g.setColor(Color.PINK);
		g.fill3DRect(x, y, w, h,true);
		g.setColor(c);
	}
	
	public Rectangle getRect() {//图形边框的触碰
		return new Rectangle(x,y,w,h);
	}
}
