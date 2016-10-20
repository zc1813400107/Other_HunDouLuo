package com.tarena.shoot02day01End;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tarena.shoot02day01End.Bullet;
import com.tarena.shoot02day01End.FlyingObject;
import com.tarena.shoot02day01End.Hero;
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
	


	//*************** add01 **************************
	public Hero hero = new Hero(); // Ӣ�ۻ�����
	public Bullet[] bullets = {}; // �ӵ�����
	public FlyingObject[] flyings = {}; // �л���С�۷�

	//*************** add03 ֻ���ڲ��ԣ�����ɾ��  ***************
	public ShootGame(){
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
	}
	//*************** add02 **************************	
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
	
	

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Shoot");
		ShootGame game = new ShootGame();
		frame.add(game);
		frame.setSize( WIDTH, HEIGHT );
		frame.setAlwaysOnTop( true );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
