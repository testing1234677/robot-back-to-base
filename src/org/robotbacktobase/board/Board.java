package org.robotbacktobase.board;

import javax.swing.Icon;

import org.robotbacktobase.sprite.service.Sprite;
import org.robotbacktobase.sprite.service.imp.Base;
import org.robotbacktobase.sprite.service.imp.Ground;

public class Board {

	public static boolean isRobotReachBase;
	public static boolean isRobotPut;
	public static boolean isBasePut;
	
	public static final int MAX_ROW = 4;
	public static final int MAX_COLUMN = 4;
	
	public final static Tile  [][] tiles = new Tile[MAX_ROW][MAX_COLUMN];
	
	//Function to move robot tile by tile
	public static void swapTileIcon(Tile tile, Tile tileToSwap){
		
		Icon tileIcon = tile.getIcon();
		Icon tileToSwapIcon = tileToSwap.getIcon();
		
		//if tile to swap type is base, this code will only change robot icon to null (ground)
		//to make the robot as if it go inside the base
		//this condition define that ai has successfully reach the goal
		if(tileToSwap.getSprite() instanceof Base){
			
			Sprite ground = new Ground();
			tile.setSprite(ground);
			
			isRobotReachBase = true;
	
		}else{
			
			tile.setIcon(tileToSwapIcon);		
			tileToSwap.setIcon(tileIcon);
		
		
		}
	
	}
		
}
