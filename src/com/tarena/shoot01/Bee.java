package com.tarena.shoot01;

import java.util.Random;

public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1;		//x�����ƶ��ٶ�
	private int ySpeed = 2;		//y�����ƶ��ٶ�
	private int awardType;		//��������
	
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
}
