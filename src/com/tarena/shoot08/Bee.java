package com.tarena.shoot08;

import java.util.Random;

import com.tarena.shoot.ShootGame;

public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1;		//x坐标移动速度
	private int ySpeed = 2;		//y坐标移动速度
	private int awardType;		//奖励类型
	
	public Bee(){
		this.image = ShootGame.beeImage;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		
		Random rd = new Random();
		x = rd.nextInt( ShootGame.WIDTH-width );
		awardType = rd.nextInt(2);
	}
	
	@Override
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if( x>ShootGame.WIDTH-width){
			xSpeed = -1;
		}
		if( x<0 ){
			xSpeed = -1;
		}
	}
	
	/** 获取奖励 */
	public int getType(){
		return awardType;
	}

	
//***** add******	
	@Override
	public boolean outOfBounds() {
		return y>ShootGame.HEIGHT; //y大于面板的高时出界	}
	}
	
}
