package org.simpleai.sprite.service.imp;

import javax.swing.ImageIcon;

import org.simpleai.sprite.service.Sprite;

public class Ground implements Sprite {
	
	private ImageIcon img = null;
	
	@Override
	public ImageIcon getSpriteIcon() {
		
		return img;
	}
	
}
