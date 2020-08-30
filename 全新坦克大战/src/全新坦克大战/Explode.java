package 全新坦克大战;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {
	int x,y;
	TankClient tc;
	private boolean live=true;

	public boolean getLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private static boolean init=false;

	//	int diameter[]= {4,8,16,24,36,41,49,31,18,8,2};//爆炸的图形大小变化
	int step=0;

	public Explode(int x,int y,TankClient tc) {//爆炸类构造函数
		this.x=x;
		this.y=y;
		this.tc=tc;
	}

	private static Toolkit tk=Toolkit.getDefaultToolkit();
	//Toolkit是工具包，要拿到工具包就要Toolkiy.getDefaultToolkit();
	//tk这个工具包里面有个类getImage();有两种两个参数，url一个和string和int的第二个，string和int是指定
	//文件的名字，有两种方式，一种是相对路径一种是绝对路径，相对路径相对于当前这个class文件所在的目录，
	//这个class文件往往是封装在包里面的，要取最顶层包里很麻烦，相对路径有一定的问题。绝对路径没问题，但是
	//复制到别人机器上，别人机器上没有那个路径，又不能用，所以用url

	private static Image image[]= {
			tk.getImage(Explode.class.getClassLoader().getResource("Image/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("Image/10.gif"))
	};
	//getClassLoader是把内存里面的这个class取出来，用getResource在这个class的bin下面找这个图


	public void draw(Graphics g) {//画笔
		if(init==false) {
			for(int i=0;i<image.length;i++) {
				g.drawImage(image[i], -100, -100, null);
				init=true;
			}
		}
		if(live==false) {
			tc.explode.remove(this);
			return;
		}else {
			g.drawImage(image[step],x,y,null);
			step++;
		}

		if(step==image.length) {
			this.setLive(false);
			step=0;
			return;
		}
	}
}
