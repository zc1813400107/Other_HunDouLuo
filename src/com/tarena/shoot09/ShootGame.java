package com.tarena.shoot09;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


// goto 步骤15
public class ShootGame extends JPanel{
	public static final int WIDTH = 400;//面板宽
	public static final int HEIGHT = 654;//面板高
	
	public static BufferedImage startImage;
	public static BufferedImage backgroundImage;
	public static BufferedImage hero0Image;
	public static BufferedImage hero1Image;
	public static BufferedImage bulletImage;
	public static BufferedImage airplaneImage;
	public static BufferedImage beeImage;
	public static BufferedImage pauseImage;
	public static BufferedImage gameoverImage;

	
	static{
		
		try {
			startImage = ImageIO.read( ShootGame.class.getResource("start.png"));
			backgroundImage = 
				ImageIO.read( ShootGame.class.getResource("background.png"));
			hero0Image = ImageIO.read( ShootGame.class.getResource("hero0.png"));
			hero1Image = ImageIO.read( ShootGame.class.getResource("hero1.png"));
			bulletImage = ImageIO.read( ShootGame.class.getResource("bullet.png"));
			airplaneImage = ImageIO.read( ShootGame.class.getResource("airplane.png"));
			beeImage = ImageIO.read( ShootGame.class.getResource("bee.png"));
			pauseImage = ImageIO.read( ShootGame.class.getResource("pause.png"));
			gameoverImage = ImageIO.read( ShootGame.class.getResource("gameover.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Hero hero = new Hero(); // 英雄机对象
	public Bullet[] bullets = {}; // 子弹数组
	public FlyingObject[] flyings = {}; // 敌机和小蜜蜂

	public ShootGame(){
		/*
		AirPlane ap = new AirPlane();
		ap.y = 0;
		Bee bee = new Bee();
		bee.y = 0;	
		flyings = new FlyingObject[]{
					ap, bee };
		bullets = new Bullet[]{
				new Bullet(195, 270),
				new Bullet(195, 310),	
				new Bullet(195, 350),
				new Bullet(195, 390)
			};
			*/
	}
	/** 重写绘制方法 */
	public void paint(Graphics g) { // g为画笔
//		g.drawImage(backgroundImage, 0, 0, null); // 画图片
		paintHero(g); // 画英雄机
		paintBullets(g); // 画子弹
		paintFlyingObjects(g); // 画敌人(敌机和小蜜蜂)
		paintScore(g); // 画分数
//		paintGameState(g); // 画游戏状态
	}
	
	/** 画英雄机 */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}

	/** 画子弹 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i]; // 每个子弹对象
			g.drawImage(b.image, b.x, b.y, null);
		}
	}

	/** 画敌人 */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];// 每一个敌人
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	
	
	/** 随机生成一个飞行物 */
	public static FlyingObject nextOne(){
		Random rd = new Random();
		int type = rd.nextInt(20); //[0,19)
		if( type==0 ){
			return new Bee();
		} else {
			return new AirPlane();
		}
		//从代码中可以发现，产生蜜蜂的几率会小一些，只有当产生的随机数为0时，才产生蜜蜂。
	}
	
	
	/** 添加enterAction方法，该方法用于实现每调用40次
	 * 该方法，将随机生成的一个蜜蜂或是敌飞机放入flying数组中*/
	int flyEnteredIndex = 0; //飞行物入场计数
	/** 飞行物入场 */
	public void enterAction(){
		flyEnteredIndex++;
		if( flyEnteredIndex%40 == 0){ //400ms -- 10*40
			FlyingObject obj = nextOne();	//随机生成一个飞行物
			flyings = Arrays.copyOf(flyings, flyings.length+1);//扩容
			flyings[ flyings.length-1 ] = obj;  //放到最后一位
		}
	}
	
	
	/** 添加stepAction方法，该方法用于实现所有飞行物的移动 */
	public void stepAction(){
		/** 飞行物走一步 */
		for( int i=0; i<flyings.length; i++ ){
			FlyingObject f = flyings[i];
			f.step();
		}
		
		/** 子弹走一步 */
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			b.step();
		}
		
		hero.step();
	}
	
	private Timer timer;	//定时器
	private int intervel = 1000/100;	//时间间隔为10ms
	/** 添加action方法，该方法使用Timer实现每隔10毫秒入场一个飞机或是蜜蜂，
	 * 并使所有飞行物移动一步，最后重绘页面 */
	public void action(){	//启动执行代码
		//鼠标事件适配器
		MouseAdapter l = new MouseAdapter(){
			/** 重写鼠标移动方法
			当鼠标移动的时候改变英雄机的x,y坐标 */
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();//得到鼠标x
				int y = e.getY();//得到鼠标y
				hero.moveTo(x, y);//英雄机移动
			}
		};
		// 给当前面板添加鼠标滑动侦听
		this.addMouseMotionListener(l);

		
		timer = new Timer();	//主流程控制
		timer.schedule( new TimerTask(){
			public void run() {
				enterAction();	//飞行物入场
				stepAction();	//走一步
				shootAction(); // 射击(子弹入场)
				bangAction(); // 子弹打敌人
				outOfBoundsAction(); // 删除越界飞行物
				repaint();		//重绘，调用paint()方法
			}
		}, intervel, intervel);
	}

	
	
	/** 添加shootAction方法，实现每调用30次该方法发射一次子弹，
	 * 并将发射的子弹存储到bullets数组中*/
	int shootIndex = 0; //射击计数
	/** 射击 */
	public void shootAction(){
		shootIndex++;
		if( shootIndex%30 == 0 ){	//100ms发一颗
			Bullet[] bs = hero.shoot();//英雄打出子弹
			bullets = Arrays.copyOf( bullets, 
					bullets.length + bs.length );//扩容
			System.arraycopy(bs, 0, 
					bullets, bullets.length-bs.length, bs.length);//追加数组
		}
	}
	// 在ShootGame类中的action方法调用shootAction方法

	

	
	private int score = 0; // 记录分数

	/** 子弹打敌人 */
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
			Bullet b = bullets[i];// 每一个子弹
			bang(b); // 子弹和飞行物碰
		}
	}

	/** 子弹打敌人 b:子弹 */
	public void bang(Bullet b) {
		int index = -1; // 击中的飞行的索引
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人
			FlyingObject obj = flyings[i]; // 得到每一个敌人
			if (obj.isShootedBy(b)) { // 判断敌人与子弹是否碰上
				index = i; // 记录被击中的飞机的下标
				break;
			}
		}
		if (index != -1) { // 有击中的敌人
			FlyingObject one = flyings[index]; // 击中的敌人

			// 删除被击中的飞行物
			FlyingObject t = flyings[index];
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = t;
			// 删除数组中最后一个元素
			flyings = Arrays.copyOf(flyings, flyings.length - 1);

			// 得分或得奖励
			if (one instanceof Enemy) { // 若为敌人
				Enemy e = (Enemy) one; // 飞行物强转为敌人
				score += e.getScore(); // 设置加分
			} else if (one instanceof Award) { // 若为奖励
				Award a = (Award) one; // 飞行物强转为奖励
				int type = a.getType(); // 得到奖励类型
				switch (type) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire(); // 双倍火力
					break;
				case Award.LIFE:
					hero.addLife(); // 增命
					break;
				}
			}
		}
	}

	
//********* add ***********	
	/** 画分数 */
	public void paintScore(Graphics g) {
		int x = 10; // x坐标
		int y = 25; // y坐标
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));// 设置字体
		g.setColor(new Color(0xFF0000)); // 设置颜色
		g.drawString("SCORE:" + score, x, y); // 画得分
		g.drawString("LIFE:" + hero.getLife(), x, y + 20); // 画命
	}

	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Shoot");
		ShootGame game = new ShootGame();
		frame.add(game);
		frame.setSize( WIDTH, HEIGHT );
		frame.setAlwaysOnTop( true );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.action();
	}
	

	/** 删除越界飞行物 */
	public void outOfBoundsAction() {
		// 删除所有出界飞行物
		int index = 0; // 下标
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 不出界的飞行物
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人
			FlyingObject f = flyings[i]; // 得到敌人
			if (!f.outOfBounds()) { // 若不出界
				flyingLives[index++] = f;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);

		// 删除所有出界的子弹
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length]; // 不出界的子弹
		for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
			Bullet b = bullets[i]; // 得到每个子弹
			if (!b.outOfBounds()) { // 若不出界
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);

	}


	/** 判断游戏是否结束 */
	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) { // 遍历所有飞行物
			int index = -1;// 记录撞上飞行物索引
			FlyingObject obj = flyings[i]; // 每个飞行物\
			if (hero.hit(obj)) { // 撞上
				hero.subtractLife(); // 减命
				hero.setDoubleFire(0); // 设置火力
				index = i; // 记录被撞的索引
			}
			if (index != -1) { // 有撞上的
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length - 1);
			}
		}
		return hero.getLife() <= 0; // 返回是否有命
	}


}
