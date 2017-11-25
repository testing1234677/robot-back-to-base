package org.robotbacktobase.board;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.robotbacktobase.sprite.service.Sprite;

public class Tile extends JButton {
	
	Sprite sprite;
	
	public Tile(Sprite sprite){
	
		this.sprite = sprite;

		setSpriteIcon(sprite.getSpriteIcon());
		
		//make button flat
		setFocusPainted(false);
		setContentAreaFilled(false);
		
	}

	private void setSpriteIcon(ImageIcon spriteIcon){
		
		if(sprite.getSpriteIcon() != null){
			
			Image img = sprite.getSpriteIcon().getImage();
			Image newimg = img.getScaledInstance(90, 110,  java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);
			
			super.setIcon(newIcon);
			
		}else{
			
			super.setIcon(null);
			
		}
		
	}
	
	public void setSprite(Sprite sprite) {
		
		this.sprite = sprite;
		
		setSpriteIcon(sprite.getSpriteIcon());
		
	}

	
	public Sprite getSprite() {
		return sprite;
	}
	
	
}
