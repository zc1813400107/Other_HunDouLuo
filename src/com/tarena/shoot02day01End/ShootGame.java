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
	


	//*************** add01 **************************
	public Hero hero = new Hero(); // 英雄机对象
	public Bullet[] bullets = {}; // 子弹数组
	public FlyingObject[] flyings = {}; // 敌机和小蜜蜂

	//*************** add03 只用于测试，后期删除  ***************
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
