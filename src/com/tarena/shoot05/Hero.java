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
		//ʵ����ͼƬ�ĸ������ж���Ч��
	}
	
	/** ���shoot������ʵ�ַ����ӵ� 
	 * ��Ӣ�ۻ��Ŀ�ȷֳ����ķ֣�
	 * ��1/2���������ӵ��ǵ��������ķ���㣻
	 * ��1/4��3/4���������ӵ���˫�������ķ����*/
	public Bullet[] shoot(){ //�����ӵ�
		int xStep = width/4;
		int yStep = 20;
		if( doubleFireNums>0 ){
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet( x+xStep, y-yStep );
			bullets[1] = new Bullet( x+3*xStep, y-yStep );
			doubleFireNums -= 2;
			return bullets;
		} else {	//����
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet( x+2*xStep, y-yStep);
			// y-yStep �ӵ����ɻ���λ��
			return bullets;
		}
	}
	

//************** add01*********************
	/**
	 * Ҫʵ��Ӣ�ۻ��������ƶ����ƶ�����ôӢ�ۻ��������㷨���£�
	 * 	hero.x=����x����-width/2��
	 * 	hero.y=����y����-height/2
	 */
	public void moveTo( int x, int y ){
		this.x = x-width/2;
		this.y = y-width/2;
	}
	// goto ShootGame����action��������������¼��Ĵ���
}
