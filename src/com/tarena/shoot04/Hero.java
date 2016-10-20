package com.tarena.shoot04;

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
	
//************ add 01 **********
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
	// goto ShootGame
	// 添加shootAction方法，实现每调用30次该方法发射一次子弹，
	//  并将发射的子弹存储到bullets数组中
	
}
