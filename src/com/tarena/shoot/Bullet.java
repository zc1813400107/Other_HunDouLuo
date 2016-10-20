package com.tarena.shoot;
/** 子弹类:是飞行物 */
public class Bullet extends FlyingObject {
	private int speed = 3; //移动步数
	
	/** 初始化实例变量 */
	public Bullet(int x,int y){
		image = ShootGame.bulletImage;  //图片
		width = image.getWidth();  //宽
		height = image.getHeight();//高
		this.x = x;  //x坐标
		this.y = y;  //y坐标
	}

	/** 重写走步 */
	public void step(){
		y -= speed;
	}

	/** 重写出界 */
	public boolean isOutOfBounds(){
		return y<-height; //y小于-的高时出界
	}
}







