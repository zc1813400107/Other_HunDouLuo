package com.tarena.shoot08;
// 子弹类
public class Bullet extends FlyingObject{
	private int speed = 3; //移动的速度
	public Bullet( int x, int y){
		this.x = x;
		this.y = y;
		this.image = ShootGame.bulletImage;
	}
	
	@Override
	public void step() {
		y -= speed;
	}

	@Override
	public boolean outOfBounds() {
		return y<-height; //y小于-的高时出界
	}
}
