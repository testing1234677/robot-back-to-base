package org.simpleai.ai.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.simpleai.ai.service.Ai;
import org.simpleai.ai.service.IAiActionOne;
import org.simpleai.board.Board;
import org.simpleai.sprite.enums.RobotMovement;
import org.simpleai.sprite.service.imp.Base;
import org.simpleai.sprite.service.imp.Robot;

public class SuccessAi  implements Ai, IAiActionOne{

	private int robotRowPos;
	private int robotColPos;
	
	private int baseRowPos;
	private int baseColPos;
	
	private List<RobotMovement> robotGeneratedMoves = new ArrayList<RobotMovement>();
	
	@Override
	public void scanBoard() {
	
		for(int i=0; i < Board.MAX_ROW; i++){
			
			for(int j=0; j < Board.MAX_COLUMN; j++){

				if(Board.tiles[i][j].getSprite() instanceof Robot ){
					
					//now Ai knows where is the robot position on board
					robotRowPos = i;
					robotColPos = j;
					
				}
				
				if(Board.tiles[i][j].getSprite() instanceof Base){
					
					//now Ai knows where is the base position on board
					baseRowPos = i;
					baseColPos = j;
					
				}
				
			}
			
		}
		
	}

	@Override
	public void generateStep() {

		//count row difference between robot and base
		int tempRobotRowPos = robotRowPos;
		int tempRobotColPos = robotColPos;
		
		int tempBaseRowPos = baseRowPos;
		int tempBaseColPos = baseColPos;
		
		int rowDistanceDifference = tempRobotRowPos - tempBaseRowPos; 
		
		while(rowDistanceDifference != 0){
			
			if(rowDistanceDifference < 0){
				
				tempRobotRowPos++;
				
				robotGeneratedMoves.add(RobotMovement.DOWN);
				
			}else{
				
				tempRobotRowPos--;
				
				robotGeneratedMoves.add(RobotMovement.UP);
				
			}
			
			rowDistanceDifference = tempRobotRowPos - tempBaseRowPos; 
			
		}
		
		//count column difference between robot and base
		int colDistanceDifference = tempRobotColPos - tempBaseColPos;
		
		while(colDistanceDifference != 0){
			
			if(colDistanceDifference < 0){
				
				tempRobotColPos++;
				
				robotGeneratedMoves.add(RobotMovement.RIGHT);
				
			}else{
				
				tempRobotColPos--;
				
				robotGeneratedMoves.add(RobotMovement.LEFT);
				
			}
			
			colDistanceDifference = tempRobotColPos - tempBaseColPos;
			
		}
		
		
	}

	@Override
	public void runAction() {
	
		scanBoard();
		generateStep();
		
	}

	@Override
	public List<RobotMovement> getRobotGeneratedMoves() {
	
		return robotGeneratedMoves;
		
	}

	@Override
	public int getRobotRowPos() {
	
		return robotRowPos;
	}

	@Override
	public int getRoboColPos() {

		return robotColPos;
	}

}
