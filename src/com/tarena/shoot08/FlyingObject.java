package com.tarena.shoot08;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int x;		//x坐标
	protected int y;		//y坐标
	protected int width;		//宽
	protected int height;	//高
	
	protected BufferedImage image;	//图片

	/** 飞行物移动一步 */
	public abstract void step();

	/** 敌人(敌机和蜜蜂)被子弹打 */
	public boolean isShootedBy(Bullet b){
		int x = b.x; //子弹的x
		int y = b.y; //子弹的y

		//子弹x在蜜蜂x和蜜蜂x加宽之间
		//并且
		//子弹y在蜜蜂y和蜜蜂y加高之间
		return x>this.x && x<this.x+width
		       &&
		       y>this.y && y<this.y+height;
	}
	// goto Hero.addDoubleFire()
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
//>>>>>>>>>>>>>>>>>> add01 
	/** 检测是否出界 */
	public abstract boolean outOfBounds();

	//goto 蜜蜂、敌机、子弹
}
