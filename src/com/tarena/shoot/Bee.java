package com.tarena.shoot;
import java.util.Random;
/** �۷���:�Ƿ����Ҳ�ǽ��� */
public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1; //x�����߲�
	private int ySpeed = 2; //y�����߲�
	private int awardType;  //��������
	
	/** ��ʼ��ʵ������ */
	public Bee(){
		image = ShootGame.beeImage;  //ͼƬ
		width = image.getWidth(); //��
		height = image.getHeight(); //��
		y = -height;  //y����
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH-width);  //x����
		awardType = rand.nextInt(2); //��������
	}
	
	/** ��д�߲� */
	public void step(){
		x += xSpeed;
		y += ySpeed;
		if(x<0){
			xSpeed = 1;   //+1����
		}
		if(x>ShootGame.WIDTH - width){
			xSpeed = -1;  //+(-1)����
		}
	}
	
	/** ��ȡ���� */
	public int getType(){
		return awardType;
	}

	/** ��д���� */
	public boolean isOutOfBounds(){
		return y>ShootGame.HEIGHT; //y�������ĸ�ʱ����
	}
	
}





