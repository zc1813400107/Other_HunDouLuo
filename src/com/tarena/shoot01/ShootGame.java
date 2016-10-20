package com.tarena.shoot01;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tarena.shoot01.Bullet;
import com.tarena.shoot01.FlyingObject;
import com.tarena.shoot01.Hero;
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
