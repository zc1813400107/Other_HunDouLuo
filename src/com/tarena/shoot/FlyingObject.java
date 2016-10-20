package com.tarena.shoot;
import java.awt.image.BufferedImage;
/** �������� */
public abstract class FlyingObject {
	protected int width;  //��
	protected int height; //��
	protected int x;      //x����
	protected int y;      //y����
	protected BufferedImage image; //ͼƬ
	
	/** �߲� */
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

	/** ����Ƿ���� */
	public abstract boolean isOutOfBounds();
	
}





