package com.tarena.shoot;

import java.util.Random;

/** 敌机:是飞行物，也是敌人 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3;//; //敌机移动的步数
	
	/** 初始化实例变量 */
	public Airplane(){
		image = ShootGame.airplaneImage;  //图片
		width = image.getWidth();  //宽
		height = image.getHeight();  //高
		y = -height;    //y坐标
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);  //x坐标
	}
	/** 重写走步 */
	public void step(){
		y += speed;
	}
	
	/** 获取分数5 */
	public int getScore(){
		return 5;
	}

	/** 重写出界 */
	public boolean isOutOfBounds(){
		return y>ShootGame.HEIGHT; //y大于面板的高时出界
	}
}





