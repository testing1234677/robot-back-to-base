package org.simpleai.sprite.service.imp;

import javax.swing.ImageIcon;

import org.simpleai.sprite.service.Sprite;

public class Robot implements Sprite {

	//https://openclipart.org/detail/225860/Robot
	private ImageIcon img = new ImageIcon(getClass().getResource("/robot.png"));

	@Override
	public ImageIcon getSpriteIcon() {
	
		return img;
	}
	
}
