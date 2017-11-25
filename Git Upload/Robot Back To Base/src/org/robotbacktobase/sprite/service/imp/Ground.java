package org.robotbacktobase.sprite.service.imp;

import javax.swing.ImageIcon;

import org.robotbacktobase.sprite.service.Sprite;

public class Ground implements Sprite {
	
	private ImageIcon img = null;
	
	@Override
	public ImageIcon getSpriteIcon() {
		
		return img;
	}
	
}
