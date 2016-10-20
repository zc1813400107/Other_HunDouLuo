package com.tarena.shoot09;

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
	
	/**
	 * Ҫʵ��Ӣ�ۻ��������ƶ����ƶ�����ôӢ�ۻ��������㷨���£�
	 * 	hero.x=����x����-width/2��
	 * 	hero.y=����y����-height/2
	 */
	public void moveTo( int x, int y ){
		this.x = x-width/2;
		this.y = y-width/2;
	}

	/** ���˫������ */
	public void addDoubleFire(){
		doubleFireNums += 40;
	}
	/** ����� */
	public void addLife(){
		lifeNums++;
	}
	
//******** add01 ******
	/** ��ȡ�� */
	public int getLife(){
		return lifeNums;
	}
	// goto ShootGame.paintScore()

	@Override
	public boolean outOfBounds() {
		return false; //��������
	}




	/** �ж�Ӣ�ۻ�������Ƿ���ײ
	 *  other:����(�л����۷�) */
	public boolean hit(FlyingObject other){
		int x1 = other.x-this.width/2;
		int x2 = other.x+other.width+this.width/2;
		int y1 = other.y-this.height/2;
		int y2 = other.y+other.height+this.height/2;
		int heroX = this.x+this.width/2;
		int heroY = this.y+this.height/2;
		return heroX>x1 && heroX<x2
		       &&
		       heroY>y1 && heroY<y2; //�Ƿ���ײ
	}
	
	/** ���� */
	public void subtractLife(){
		lifeNums--;
	}
	/** ���û��� */
	public void setDoubleFire(int doubleFire){
		this.doubleFireNums = doubleFire;
	}



}
