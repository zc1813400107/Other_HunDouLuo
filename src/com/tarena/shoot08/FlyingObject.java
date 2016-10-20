package com.tarena.shoot08;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int x;		//x����
	protected int y;		//y����
	protected int width;		//��
	protected int height;	//��
	
	protected BufferedImage image;	//ͼƬ

	/** �������ƶ�һ�� */
	public abstract void step();

	/** ����(�л����۷�)���ӵ��� */
	public boolean isShootedBy(Bullet b){
		int x = b.x; //�ӵ���x
		int y = b.y; //�ӵ���y

		//�ӵ�x���۷�x���۷�x�ӿ�֮��
		//����
		//�ӵ�y���۷�y���۷�y�Ӹ�֮��
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
	/** ����Ƿ���� */
	public abstract boolean outOfBounds();

	//goto �۷䡢�л����ӵ�
}
