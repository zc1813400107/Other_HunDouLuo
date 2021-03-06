package com.tarena.shoot03;

import java.util.Random;

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
	public int getType() {
		return 0;
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
}
