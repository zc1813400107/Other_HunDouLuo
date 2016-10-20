package com.tarena.shoot03;

public class AirPlane extends FlyingObject implements Enemy{
	private int speed = 2;	//移动的速度
	
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
	
	@Override
	public void step() {
		y += speed;
	}
}
