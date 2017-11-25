package org.robotbacktobase.ai.service;

import java.util.List;

import org.robotbacktobase.sprite.enums.RobotMovement;

public interface Ai {

	public void runAction();
	
	public int getRobotRowPos();
	
	public int getRoboColPos();
	
	public List<RobotMovement> getRobotGeneratedMoves();
	
	
}
