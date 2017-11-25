package org.simpleai.ai.service;

import java.util.List;

import org.simpleai.sprite.enums.RobotMovement;

public interface Ai {

	public void runAction();
	
	public int getRobotRowPos();
	
	public int getRoboColPos();
	
	public List<RobotMovement> getRobotGeneratedMoves();
	
	
}
