package com.tarena.shoot;

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

/** ��Ϸ������ */
public class ShootGame extends JPanel {
	public static final int WIDTH = 400; // ��
	public static final int HEIGHT = 654;// ��

	public static BufferedImage backgroundImage;
	public static BufferedImage startImage;
	public static BufferedImage gameoverImage;
	public static BufferedImage pauseImage;
	public static BufferedImage airplaneImage;
	public static BufferedImage beeImage;
	public static BufferedImage bulletImage;
	public static BufferedImage hero0Image;
	public static BufferedImage hero1Image;

	public Hero hero = new Hero(); // Ӣ�ۻ�����
	public Bullet[] bullets = {}; // �ӵ�����
	public FlyingObject[] flyings = {}; // �л���С�۷�

	private int score = 0; // ��¼����

	private int gamestate; // ״̬
	public static final int GAME_START = 0;
	public static final int GAME_RUNNING = 1;
	public static final int GAME_PAUSE = 2;
	public static final int GAME_OVER = 3;

	static { // ����ͼƬ��Դ
		try {
			backgroundImage = ImageIO.read(ShootGame.class.getResource("background.png"));
			startImage = ImageIO.read(ShootGame.class.getResource("start.png"));
			gameoverImage = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			pauseImage = ImageIO.read(ShootGame.class.getResource("pause.png"));
			airplaneImage = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			beeImage = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bulletImage = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0Image = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1Image = ImageIO.read(ShootGame.class.getResource("hero1.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** ��д���Ʒ��� */
	public void paint(Graphics g) { // gΪ����
		g.drawImage(backgroundImage, 0, 0, null); // ��ͼƬ
		paintHero(g); // ��Ӣ�ۻ�
		paintBullets(g); // ���ӵ�
		paintFlyingObjects(g); // ������(�л���С�۷�)
		paintScore(g); // ������
		paintGameState(g); // ����Ϸ״̬
	}

	/** ����Ϸ״̬ */
	public void paintGameState(Graphics g) {
		switch (gamestate) {
		case GAME_START: // ����ͼƬ
			g.drawImage(startImage, 0, 0, null);
			break;
		case GAME_PAUSE: // ��ͣͼƬ
			g.drawImage(pauseImage, 0, 0, null);
			break;
		case GAME_OVER: // ֹͣͼƬ
			g.drawImage(gameoverImage, 0, 0, null);
			break;
		}
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

	/** ������ */
	public void paintScore(Graphics g) {
		int x = 10; // x����
		int y = 25; // y����
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));// ��������
		g.setColor(new Color(0xFF0000)); // ������ɫ
		g.drawString("SCORE:" + score, x, y); // ���÷�
		g.drawString("LIFE:" + hero.getLife(), x, y + 20); // ����
	}

	
	private Timer timer; // ��ʱ��
	private int interval = 10; // ʱ����(����)

	/** ����ִ�ж����� */
	public void action() {
		// ����¼�������
		MouseAdapter l = new MouseAdapter() {
			/** ��дmouseMoved(����ƶ�)���� */
			// ������ƶ���ʱ���޸�Ӣ�ۻ���x,y����
			public void mouseMoved(MouseEvent e) {
				if (gamestate == GAME_RUNNING) {
					int x = e.getX(); // �õ�����x
					int y = e.getY(); // �õ�����y
					hero.moveTo(x, y); // Ӣ�ۻ��ƶ�
				}
			}

			/** ��дmouseClicked(�����)���� */
			public void mouseClicked(MouseEvent e) {
				switch (gamestate) {
				case GAME_START:
					gamestate = GAME_RUNNING;
					break;
				case GAME_OVER: // ��Ϸ��������
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					score = 0;
					gamestate = GAME_START;
					break;

				}
			}

			/** ��дmouseExited(����Ƴ�)���� */
			public void mouseExited(MouseEvent e) {
				if (gamestate != GAME_OVER) {
					gamestate = GAME_PAUSE;
				}
			}

			/** ��дmouseEntered(�������)���� */
			public void mouseEntered(MouseEvent e) {
				if (gamestate == GAME_PAUSE) {
					gamestate = GAME_RUNNING;
				}
			}
		};
		// ����ǰ���������������
		this.addMouseListener(l);
		// ����ǰ��������껬������
		this.addMouseMotionListener(l);

		timer = new Timer(); // ������ʱ������
		// ���´��룬ÿ��10����ִ��һ��run()����
		timer.schedule(new TimerTask() { // ��ʱ����
					// ��дrun����
					public void run() { // ��ʱִ�еķ���
						if (gamestate == GAME_RUNNING) {
							enterAction(); // �������볡---new����
							stepAction();// �������߲�
							shootAction(); // ���(�ӵ��볡)
							bangAction(); // �ӵ������
							outOfBoundsAction(); // ɾ��Խ�������
							checkGameOverAction(); // �ж��Ƿ���Ϸ����
						}
						repaint(); // �ػ�(����paint����)
					}
				}, interval, interval);
	}

	/** �����Ϸ�Ƿ���� */
	public void checkGameOverAction() {
		if (isGameOver()) { // �ж��Ƿ����
			gamestate = GAME_OVER; //�ı�״̬Ϊ����
		}
	}

	/** �ж���Ϸ�Ƿ���� */
	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) { // �������з�����
			int index = -1;// ��¼ײ�Ϸ���������
			FlyingObject obj = flyings[i]; // ÿ��������\
			if (hero.hit(obj)) { // ײ��
				hero.subtractLife(); // ����
				hero.setDoubleFire(0); // ���û���
				index = i; // ��¼��ײ������
			}
			if (index != -1) { // ��ײ�ϵ�
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length - 1);
			}
		}
		return hero.getLife() <= 0; // �����Ƿ�����
	}

	/** ɾ��Խ������� */
	public void outOfBoundsAction() {
		// ɾ�����г��������
		int index = 0; // �±�
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // ������ķ�����
		for (int i = 0; i < flyings.length; i++) { // �������е���
			FlyingObject f = flyings[i]; // �õ�����
			if (!f.isOutOfBounds()) { // ��������
				flyingLives[index++] = f;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);

		// ɾ�����г�����ӵ�
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length]; // ��������ӵ�
		for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
			Bullet b = bullets[i]; // �õ�ÿ���ӵ�
			if (!b.isOutOfBounds()) { // ��������
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);

	}

	/** �ӵ������ */
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
			Bullet b = bullets[i];// ÿһ���ӵ�
			bang(b); // �ӵ��ͷ�������
		}
	}

	/** �ӵ������ b:�ӵ� */
	public void bang(Bullet b) {
		int index = -1; // ���еķ��е�����
		for (int i = 0; i < flyings.length; i++) { // �������е���
			FlyingObject obj = flyings[i]; // �õ�ÿһ������
			if (obj.isShootedBy(b)) { // �жϵ������ӵ��Ƿ�����
				index = i; // ��¼�����еķɻ����±�
				break;
			}
		}
		if (index != -1) { // �л��еĵ���
			FlyingObject one = flyings[index]; // ���еĵ���

			// ɾ�������еķ�����
			FlyingObject t = flyings[index];
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = t;
			// ɾ�����������һ��Ԫ��
			flyings = Arrays.copyOf(flyings, flyings.length - 1);

			// �÷ֻ�ý���
			if (one instanceof Enemy) { // ��Ϊ����
				Enemy e = (Enemy) one; // ������ǿתΪ����
				score += e.getScore(); // ���üӷ�
			} else if (one instanceof Award) { // ��Ϊ����
				Award a = (Award) one; // ������ǿתΪ����
				int type = a.getType(); // �õ���������
				switch (type) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire(); // ˫������
					break;
				case Award.LIFE:
					hero.addLife(); // ����
					break;
				}
			}
		}
	}

	int shootIndex = 0; // �������Ƶ��

	/** ���---�ӵ��볡 */
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) { // 300���뷢һ��
			Bullet[] bs = hero.shoot(); // Ӣ�ۻ����������
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);// ����
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// ׷��
		}
	}

	int flyEnteredIndex = 0; // �������볡����

	/** ������(�л���С�۷�)�볡 */
	// ���´��룬ÿ400��������һ���л���С�۷�
	// ��װ��������
	public void enterAction() { // ÿ10������һ��
		flyEnteredIndex++; // ��һ��index��1
		if (flyEnteredIndex % 40 == 0) {// ��40�Σ�%40Ϊ0----400����
			FlyingObject obj = nextOne();// �������һ������
			flyings = Arrays.copyOf(flyings, flyings.length + 1); // ����
			flyings[flyings.length - 1] = obj; // ������װ���������һλ
		}
	}

	/** ������ɵ���(�л����۷�) */
	// ��������:��������һ��Ϊstatic��
	public static FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);// [0,19]
		if (type == 0) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

	/** �������߲� */
	public void stepAction() {
		// ����(�л���С�۷�)�߲�
		for (int i = 0; i < flyings.length; i++) {
			flyings[i].step();
		}
		// �ӵ��߲�
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
		// �ɻ��߲�----��ͼƬ
		hero.step();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly"); // ����--����
		ShootGame game = new ShootGame(); // ������
		frame.add(game); // �����ӵ�������

		frame.setSize(WIDTH, HEIGHT); // ��С
		frame.setAlwaysOnTop(true);// ��������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Ĭ�Ϲر�
		frame.setLocationRelativeTo(null); // ��ʼλ��

		frame.setVisible(true);// ��ʾ-�������paint����

		game.action(); // ���涯����
	}

}
