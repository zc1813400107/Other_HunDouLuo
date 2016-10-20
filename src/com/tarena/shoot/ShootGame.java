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

/** 游戏主界面 */
public class ShootGame extends JPanel {
	public static final int WIDTH = 400; // 宽
	public static final int HEIGHT = 654;// 高

	public static BufferedImage backgroundImage;
	public static BufferedImage startImage;
	public static BufferedImage gameoverImage;
	public static BufferedImage pauseImage;
	public static BufferedImage airplaneImage;
	public static BufferedImage beeImage;
	public static BufferedImage bulletImage;
	public static BufferedImage hero0Image;
	public static BufferedImage hero1Image;

	public Hero hero = new Hero(); // 英雄机对象
	public Bullet[] bullets = {}; // 子弹数组
	public FlyingObject[] flyings = {}; // 敌机和小蜜蜂

	private int score = 0; // 记录分数

	private int gamestate; // 状态
	public static final int GAME_START = 0;
	public static final int GAME_RUNNING = 1;
	public static final int GAME_PAUSE = 2;
	public static final int GAME_OVER = 3;

	static { // 加载图片资源
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

	/** 重写绘制方法 */
	public void paint(Graphics g) { // g为画笔
		g.drawImage(backgroundImage, 0, 0, null); // 画图片
		paintHero(g); // 画英雄机
		paintBullets(g); // 画子弹
		paintFlyingObjects(g); // 画敌人(敌机和小蜜蜂)
		paintScore(g); // 画分数
		paintGameState(g); // 画游戏状态
	}

	/** 画游戏状态 */
	public void paintGameState(Graphics g) {
		switch (gamestate) {
		case GAME_START: // 启动图片
			g.drawImage(startImage, 0, 0, null);
			break;
		case GAME_PAUSE: // 暂停图片
			g.drawImage(pauseImage, 0, 0, null);
			break;
		case GAME_OVER: // 停止图片
			g.drawImage(gameoverImage, 0, 0, null);
			break;
		}
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

	/** 画分数 */
	public void paintScore(Graphics g) {
		int x = 10; // x坐标
		int y = 25; // y坐标
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));// 设置字体
		g.setColor(new Color(0xFF0000)); // 设置颜色
		g.drawString("SCORE:" + score, x, y); // 画得分
		g.drawString("LIFE:" + hero.getLife(), x, y + 20); // 画命
	}

	
	private Timer timer; // 定时器
	private int interval = 10; // 时间间隔(毫秒)

	/** 启动执行动操作 */
	public void action() {
		// 鼠标事件适配器
		MouseAdapter l = new MouseAdapter() {
			/** 重写mouseMoved(鼠标移动)方法 */
			// 当鼠标移动的时候修改英雄机的x,y坐标
			public void mouseMoved(MouseEvent e) {
				if (gamestate == GAME_RUNNING) {
					int x = e.getX(); // 得到鼠标的x
					int y = e.getY(); // 得到鼠标的y
					hero.moveTo(x, y); // 英雄机移动
				}
			}

			/** 重写mouseClicked(鼠标点击)方法 */
			public void mouseClicked(MouseEvent e) {
				switch (gamestate) {
				case GAME_START:
					gamestate = GAME_RUNNING;
					break;
				case GAME_OVER: // 游戏结束归零
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					score = 0;
					gamestate = GAME_START;
					break;

				}
			}

			/** 重写mouseExited(鼠标移出)方法 */
			public void mouseExited(MouseEvent e) {
				if (gamestate != GAME_OVER) {
					gamestate = GAME_PAUSE;
				}
			}

			/** 重写mouseEntered(鼠标移入)方法 */
			public void mouseEntered(MouseEvent e) {
				if (gamestate == GAME_PAUSE) {
					gamestate = GAME_RUNNING;
				}
			}
		};
		// 给当前面板添加鼠标点击侦听
		this.addMouseListener(l);
		// 给当前面板添加鼠标滑动侦听
		this.addMouseMotionListener(l);

		timer = new Timer(); // 创建定时器对象
		// 如下代码，每隔10毫秒执行一次run()方法
		timer.schedule(new TimerTask() { // 定时触发
					// 重写run方法
					public void run() { // 定时执行的方法
						if (gamestate == GAME_RUNNING) {
							enterAction(); // 飞行物入场---new对象
							stepAction();// 飞行物走步
							shootAction(); // 射击(子弹入场)
							bangAction(); // 子弹打敌人
							outOfBoundsAction(); // 删除越界飞行物
							checkGameOverAction(); // 判断是否游戏结束
						}
						repaint(); // 重绘(调用paint方法)
					}
				}, interval, interval);
	}

	/** 检查游戏是否结束 */
	public void checkGameOverAction() {
		if (isGameOver()) { // 判断是否结束
			gamestate = GAME_OVER; //改变状态为结束
		}
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

	/** 删除越界飞行物 */
	public void outOfBoundsAction() {
		// 删除所有出界飞行物
		int index = 0; // 下标
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 不出界的飞行物
		for (int i = 0; i < flyings.length; i++) { // 遍历所有敌人
			FlyingObject f = flyings[i]; // 得到敌人
			if (!f.isOutOfBounds()) { // 若不出界
				flyingLives[index++] = f;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);

		// 删除所有出界的子弹
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length]; // 不出界的子弹
		for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
			Bullet b = bullets[i]; // 得到每个子弹
			if (!b.isOutOfBounds()) { // 若不出界
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);

	}

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

	int shootIndex = 0; // 控制射击频率

	/** 射击---子弹入场 */
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) { // 300毫秒发一次
			Bullet[] bs = hero.shoot(); // 英雄机发射出来的
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);// 扩容
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// 追加
		}
	}

	int flyEnteredIndex = 0; // 飞行物入场计数

	/** 飞行物(敌机和小蜜蜂)入场 */
	// 如下代码，每400毫秒生成一个敌机或小蜜蜂
	// 并装到数组中
	public void enterAction() { // 每10毫秒走一步
		flyEnteredIndex++; // 走一步index增1
		if (flyEnteredIndex % 40 == 0) {// 走40次，%40为0----400毫秒
			FlyingObject obj = nextOne();// 随机生成一个对象
			flyings = Arrays.copyOf(flyings, flyings.length + 1); // 扩容
			flyings[flyings.length - 1] = obj; // 将敌人装到数组最后一位
		}
	}

	/** 随机生成敌人(敌机，蜜蜂) */
	// 工厂方法:生产对象，一般为static的
	public static FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);// [0,19]
		if (type == 0) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

	/** 飞行物走步 */
	public void stepAction() {
		// 敌人(敌机和小蜜蜂)走步
		for (int i = 0; i < flyings.length; i++) {
			flyings[i].step();
		}
		// 子弹走步
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
		// 飞机走步----换图片
		hero.step();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly"); // 窗口--画框
		ShootGame game = new ShootGame(); // 面板对象
		frame.add(game); // 将面板加到窗口中

		frame.setSize(WIDTH, HEIGHT); // 大小
		frame.setAlwaysOnTop(true);// 总在最上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭
		frame.setLocationRelativeTo(null); // 初始位置

		frame.setVisible(true);// 显示-尽快调用paint方法

		game.action(); // 界面动起来
	}

}
