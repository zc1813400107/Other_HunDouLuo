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

// goto ����15
public class ShootGame extends JPanel{
	public static final int WIDTH = 400;//����
	public static final int HEIGHT = 654;//����
	
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
	
	public Hero hero = new Hero(); // Ӣ�ۻ�����
	public Bullet[] bullets = {}; // �ӵ�����
	public FlyingObject[] flyings = {}; // �л���С�۷�

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
	/** ��д���Ʒ��� */
	public void paint(Graphics g) { // gΪ����
//		g.drawImage(backgroundImage, 0, 0, null); // ��ͼƬ
		paintHero(g); // ��Ӣ�ۻ�
		paintBullets(g); // ���ӵ�
		paintFlyingObjects(g); // ������(�л���С�۷�)
//		paintScore(g); // ������
//		paintGameState(g); // ����Ϸ״̬
	}
	
	/** ��Ӣ�ۻ� */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}

	/** ���ӵ� */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i]; // ÿ���ӵ�����
			g.drawImage(b.image, b.x, b.y, null);
		}
	}

	/** ������ */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];// ÿһ������
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	
	
	/** �������һ�������� */
	public static FlyingObject nextOne(){
		Random rd = new Random();
		int type = rd.nextInt(20); //[0,19)
		if( type==0 ){
			return new Bee();
		} else {
			return new AirPlane();
		}
		//�Ӵ����п��Է��֣������۷�ļ��ʻ�СһЩ��ֻ�е������������Ϊ0ʱ���Ų����۷䡣
	}
	
	
	/** ���enterAction�������÷�������ʵ��ÿ����40��
	 * �÷�������������ɵ�һ���۷���ǵзɻ�����flying������*/
	int flyEnteredIndex = 0; //�������볡����
	/** �������볡 */
	public void enterAction(){
		flyEnteredIndex++;
		if( flyEnteredIndex%40 == 0){ //400ms -- 10*40
			FlyingObject obj = nextOne();	//�������һ��������
			flyings = Arrays.copyOf(flyings, flyings.length+1);//����
			flyings[ flyings.length-1 ] = obj;  //�ŵ����һλ
		}
	}
	
	
	/** ���stepAction�������÷�������ʵ�����з�������ƶ� */
	public void stepAction(){
		/** ��������һ�� */
		for( int i=0; i<flyings.length; i++ ){
			FlyingObject f = flyings[i];
			f.step();
		}
		
		/** �ӵ���һ�� */
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			b.step();
		}
		
		hero.step();
	}
	
	private Timer timer;	//��ʱ��
	private int intervel = 1000/100;	//ʱ����Ϊ10ms
	/** ���action�������÷���ʹ��Timerʵ��ÿ��10�����볡һ���ɻ������۷䣬
	 * ��ʹ���з������ƶ�һ��������ػ�ҳ�� */
	public void action(){	//����ִ�д���
//**************** add02************
		//����¼�������
		MouseAdapter l = new MouseAdapter(){
			/** ��д����ƶ�����
			������ƶ���ʱ��ı�Ӣ�ۻ���x,y���� */
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();//�õ����x
				int y = e.getY();//�õ����y
				hero.moveTo(x, y);//Ӣ�ۻ��ƶ�
			}
		};
		// ����ǰ��������껬������
		this.addMouseMotionListener(l);
		
		timer = new Timer();	//�����̿���
		timer.schedule( new TimerTask(){
			public void run() {
				enterAction();	//�������볡
				stepAction();	//��һ��
				shootAction(); // ���(�ӵ��볡)
				repaint();		//�ػ棬����paint()����
			}
		}, intervel, intervel);
	}

	
	
	/** ���shootAction������ʵ��ÿ����30�θ÷�������һ���ӵ���
	 * ����������ӵ��洢��bullets������*/
	int shootIndex = 0; //�������
	/** ��� */
	public void shootAction(){
		shootIndex++;
		if( shootIndex%30 == 0 ){	//100ms��һ��
			Bullet[] bs = hero.shoot();//Ӣ�۴���ӵ�
			bullets = Arrays.copyOf( bullets, 
					bullets.length + bs.length );//����
			System.arraycopy(bs, 0, 
					bullets, bullets.length-bs.length, bs.length);//׷������
		}
	}
	// ��ShootGame���е�action��������shootAction����
	
	
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
