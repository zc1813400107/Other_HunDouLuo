package com.tarena.shoot05;

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
//		paintScore(g); // 画分数
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
//**************** add02************
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
}
