package com.tarena.shoot05;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	protected BufferedImage[] images = {};
	protected int index = 0;
	
	private int doubleFireNums;
	private int lifeNums;
	
	public Hero(){
		lifeNums = 3;
		doubleFireNums = 0;
		this.image = ShootGame.hero0Image;
		images = new BufferedImage[]{ShootGame.hero0Image, ShootGame.hero1Image};
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
	}
	
	@Override
	public void step() {
		if( images.length>0){
			image = images[ index++/10%images.length ];
		}
		//实现了图片的更换，有动画效果
	}
	
	/** 添加shoot方法，实现发射子弹 
	 * 将英雄机的宽度分成了四分，
	 * 在1/2处发出的子弹是单倍火力的发射点；
	 * 在1/4和3/4处发出的子弹是双倍火力的发射点*/
	public Bullet[] shoot(){ //发射子弹
		int xStep = width/4;
		int yStep = 20;
		if( doubleFireNums>0 ){
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet( x+xStep, y-yStep );
			bullets[1] = new Bullet( x+3*xStep, y-yStep );
			doubleFireNums -= 2;
			return bullets;
		} else {	//单倍
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet( x+2*xStep, y-yStep);
			// y-yStep 子弹到飞机的位置
			return bullets;
		}
	}
	

//************** add01*********************
	/**
	 * 要实现英雄机跟随着移动而移动，那么英雄机的坐标算法如下：
	 * 	hero.x=鼠标的x坐标-width/2；
	 * 	hero.y=鼠标的y坐标-height/2
	 */
	public void moveTo( int x, int y ){
		this.x = x-width/2;
		this.y = y-width/2;
	}
	// goto ShootGame，在action方法中添加鼠标的事件的处理
}
