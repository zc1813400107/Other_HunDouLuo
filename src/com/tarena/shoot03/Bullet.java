package com.tarena.shoot03;
// �ӵ���
public class Bullet extends FlyingObject{
	private int speed = 3; //�ƶ����ٶ�
	public Bullet( int x, int y){
		this.x = x;
		this.y = y;
		this.image = ShootGame.bulletImage;
	}
	
	@Override
	public void step() {
		y -= speed;
	}
}