package ȫ��̹�˴�ս;

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

	//	int diameter[]= {4,8,16,24,36,41,49,31,18,8,2};//��ը��ͼ�δ�С�仯
	int step=0;

	public Explode(int x,int y,TankClient tc) {//��ը�๹�캯��
		this.x=x;
		this.y=y;
		this.tc=tc;
	}

	private static Toolkit tk=Toolkit.getDefaultToolkit();
	//Toolkit�ǹ��߰���Ҫ�õ����߰���ҪToolkiy.getDefaultToolkit();
	//tk������߰������и���getImage();����������������urlһ����string��int�ĵڶ�����string��int��ָ��
	//�ļ������֣������ַ�ʽ��һ�������·��һ���Ǿ���·�������·������ڵ�ǰ���class�ļ����ڵ�Ŀ¼��
	//���class�ļ������Ƿ�װ�ڰ�����ģ�Ҫȡ��������鷳�����·����һ�������⡣����·��û���⣬����
	//���Ƶ����˻����ϣ����˻�����û���Ǹ�·�����ֲ����ã�������url

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
	//getClassLoader�ǰ��ڴ���������classȡ��������getResource�����class��bin���������ͼ


	public void draw(Graphics g) {//����
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
