package com.tarena.shoot;
import java.awt.image.BufferedImage;
/** 飞行物类 */
public abstract class FlyingObject {
	protected int width;  //宽
	protected int height; //高
	protected int x;      //x坐标
	protected int y;      //y坐标
	protected BufferedImage image; //图片
	
	/** 走步 */
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

	/** 检测是否出界 */
	public abstract boolean isOutOfBounds();
	
}





