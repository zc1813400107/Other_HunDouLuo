package com.tarena.shoot02day01End;
// 子弹类
public class Bullet extends FlyingObject{
	private int speed = 3; //移动的速度
	public Bullet( int x, int y){
		this.x = x;
		this.y = y;
		this.image = ShootGame.bulletImage;
	}
}
