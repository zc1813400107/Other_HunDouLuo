package com.tarena.shoot09;

import com.tarena.shoot.ShootGame;

public class AirPlane extends FlyingObject implements Enemy{
	private int speed = 2;	//�ƶ����ٶ�
	
	public AirPlane(){
		this.image = ShootGame.airplaneImage;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		x = (int)( Math.random()*(ShootGame.WIDTH-width) );
	}

	
	@Override
	public void step() {
		y += speed;
	}
	
	/** ��ȡ����5 */
	public int getScore(){
		return 5;
	}


	@Override
	public boolean outOfBounds() {
		return y>ShootGame.HEIGHT; //y�������ĸ�ʱ����
	}
}
