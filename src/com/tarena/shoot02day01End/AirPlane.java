package com.tarena.shoot02day01End;

public class AirPlane extends FlyingObject implements Enemy{
	private int speed = 2;	//�ƶ����ٶ�
	
	public AirPlane(){
		this.image = ShootGame.airplaneImage;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		x = (int)( Math.random()*(ShootGame.WIDTH-width) );
	}
	public int getScore(){
		return 0;
	}
}
