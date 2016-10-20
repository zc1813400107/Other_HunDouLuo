package com.tarena.shoot01;

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
}
