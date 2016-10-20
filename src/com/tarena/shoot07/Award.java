package com.tarena.shoot07;

public interface Award {
	int DOUBLE_FIRE = 0; //双倍火力
	int LIFE = 1;	//1条命
	
	int getType();	//获得奖励类型（上面的0或1）
}
