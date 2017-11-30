package org.robotbacktobase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import org.robotbacktobase.ai.factory.AiFactory;
import org.robotbacktobase.ai.service.Ai;
import org.robotbacktobase.board.Board;
import org.robotbacktobase.board.Tile;
import org.robotbacktobase.sprite.enums.RobotMovement;
import org.robotbacktobase.sprite.service.Sprite;
import org.robotbacktobase.sprite.service.imp.Base;
import org.robotbacktobase.sprite.service.imp.Ground;
import org.robotbacktobase.sprite.service.imp.Robot;

public class Application extends JFrame implements ActionListener{

	private final int TIMER_DELAY = 1000;
	
	//variable to access all robot moves value inside list
	private int moveIndex = 0;
	
	private int robotRowPos;
	private int robotColPos;
	
	private List<RobotMovement> robotMoves;
	
	private Sprite robot = new Robot();
	private Sprite base = new Base();
	private Sprite ground = new Ground();
	private Sprite spriteToPut;
	
	private Ai ai =  AiFactory.getAi("SuccessAi");

	private boolean enableMenu;
	private boolean isAnimationPlay;
	
	private JLabel statusLbl = new JLabel();
	
	public static void main(String[] args) {

		new Board();
		new Application().loadWindow();
		
	}

	private void loadWindow(){
		
		setTitle("Robot Back To Base");
		
		JPanel jp = new JPanel(new GridLayout(Board.MAX_ROW,Board.MAX_COLUMN));
		
		//Add listener to all tiles
		for(int i=0 ; i < Board.MAX_ROW; i++){
			
			for(int j=0 ; j < Board.MAX_COLUMN; j++){
			
				Board.tiles[i][j] = new Tile(ground);
				
				Board.tiles[i][j].addActionListener(this);
				
				jp.add(Board.tiles[i][j]);
				
			}
		
		}
		
		//create status bar
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    statusBar.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY),
        new EmptyBorder(4, 4, 4, 4)));
	    statusBar.add(statusLbl);
	    statusLbl.setText(" ");
	    
		getContentPane().add(jp);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		loadMenuBar();
		
		setSize(700,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
			
	}
	
	//Create menu
	private void loadMenuBar(){
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu boardMnu = new JMenu("Board");
		JMenu aiMnu = new JMenu("AI");
		JMenu runMnu = new JMenu("Play");
		JMenu helpMnu = new JMenu("Help");

		JMenuItem boardMnuItem1 = new JMenuItem("Put Robot");
		JMenuItem boardMnuItem2 = new JMenuItem("Put Base");

		ButtonGroup aiMnuItemGroup = new ButtonGroup();
		JRadioButtonMenuItem aiMnuItem1 = new JRadioButtonMenuItem("Success AI");
		JRadioButtonMenuItem aiMnuItem2 = new JRadioButtonMenuItem("Broken AI (does nothing)");
		
		JMenuItem runMnuItem1 = new JMenuItem("AI Action");
		
		JMenuItem helpMnuItem1 = new JMenuItem("Tutorial");
		JMenuItem helpMnuItem2 = new JMenuItem("About");
		
		boardMnu.add(boardMnuItem1);
		boardMnu.add(boardMnuItem2);
		boardMnuItem1.setActionCommand("PR");
		boardMnuItem1.addActionListener(this);
		boardMnuItem2.setActionCommand("PB");
		boardMnuItem2.addActionListener(this);
		
		aiMnu.add(aiMnuItem1);
		aiMnu.add(aiMnuItem2);
		aiMnuItem1.setActionCommand("SA");
		aiMnuItem1.addActionListener(this);
		aiMnuItem2.setActionCommand("BA");
		aiMnuItem2.addActionListener(this);
		
		aiMnuItemGroup.add(aiMnuItem1);
		aiMnuItemGroup.add(aiMnuItem2);
		aiMnuItem1.setSelected(true);
		
		runMnu.add(runMnuItem1);
		runMnuItem1.setActionCommand("AA");
		runMnuItem1.addActionListener(this);
		
		helpMnu.add(helpMnuItem1);
		helpMnu.add(helpMnuItem2);
		helpMnuItem1.setActionCommand("TR");
		helpMnuItem1.addActionListener(this);
		helpMnuItem2.setActionCommand("AB");
		helpMnuItem2.addActionListener(this);
		
		menuBar.add(boardMnu);
		menuBar.add(aiMnu);
		menuBar.add(runMnu);
		menuBar.add(helpMnu);
		
		setJMenuBar(menuBar);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
				
			//catch action command from every menu
			if(e.getActionCommand() == "PR"){ //Put Robot Menu
				
				boardMenuAction(robot,"Place robot in tile");
				
				Board.isRobotPut = true;

			}else if(e.getActionCommand() == "PB"){ //Put Base Menu
				
				boardMenuAction(base,"Place base in tile");
							
				Board.isBasePut = true;
				
			}else if(e.getActionCommand() == "SA"){ //Smarter Ai Menu
				
				ai =  AiFactory.getAi("SuccessAi");
				
			}else if(e.getActionCommand() == "BA"){ //Fail Ai Menu
				
				ai =  AiFactory.getAi("BrokenAi");
				
			}else if(e.getActionCommand() == "AA"){ //Run Ai Logic Menu
				
				if(Board.isRobotPut && Board.isBasePut && !isAnimationPlay){
				
					ai.runAction();
					
					robotRowPos = ai.getRobotRowPos();
					robotColPos = ai.getRoboColPos();
					robotMoves = ai.getRobotGeneratedMoves();
		
					isAnimationPlay = true;
					
					setEnableAllMenu(false);
					
					animateAiAction();
				
				}else{
					
					JOptionPane.showMessageDialog(null, "Unable to play AI action!", "Error", JOptionPane.ERROR_MESSAGE); 
					
				}
					
			}else if(e.getActionCommand() == "TR"){ //Run Ai Logic Menu
				
				JOptionPane.showMessageDialog(null, "1. Click Board > Put Robot and place robot in desire tile\n"+
													"2. Click Board > Put Base and place base in desire tile\n"+
													"3. Click AI and select desire AI\n"+
													"4. Click Play > AI Action, to see AI in action"
						
						, "Tutorial", JOptionPane.PLAIN_MESSAGE);
			
			}else if(e.getActionCommand() == "AB"){ //Run Ai Logic Menu
			
				JOptionPane.showMessageDialog(null, "An Application based on one of the Hackkerank AI question\n"+
													"If you have the source code, feel free to play with it\n"
						, "About", JOptionPane.PLAIN_MESSAGE);
				
			}	
			
			
			//If put robot menu or put base menu clicked
			//disable all menu and make user can put robot and base in the board
			//after user put robot or base all menu will be enable again
			if(!enableMenu){
				
				Tile clickedTile = (Tile)e.getSource();
				
				//if clicked tile is not empty
				if(clickedTile.getSprite() instanceof Ground){
				
					clickedTile.setSprite(spriteToPut);
					
					enableMenu = true;
					
					setEnableAllMenu(enableMenu);
					
					statusLbl.setText(" ");
				
				}else{  
					
					
					JOptionPane.showMessageDialog(null, "Tile has ocuppied!"
							, "Error", JOptionPane.ERROR_MESSAGE);
							
				}
				
				
			}	
		
		} catch (Exception ex) {}
		
				
	}
	
	public void animateAiAction(){

		Timer timer = new Timer(TIMER_DELAY, new ActionListener() {
			
		    public void actionPerformed(ActionEvent evt) {
		    	
		    	if(moveIndex < robotMoves.size()){
		    		
		    		statusLbl.setText("AI MOVE ROBOT TO "
		    							+robotMoves.get(moveIndex).toString());
		
		    		//move robot in tile based on generate AI move sets
		    		switch(robotMoves.get(moveIndex)){
		    		
			    		case UP    : Board.swapTileIcon(Board.tiles[robotRowPos][robotColPos], Board.tiles[robotRowPos-1][robotColPos]);
			    					 robotRowPos--;
			    					 break;
			    		case DOWN    : Board.swapTileIcon(Board.tiles[robotRowPos][robotColPos], Board.tiles[robotRowPos+1][robotColPos]);
				   					 robotRowPos++;
				   					 break;
			    		case LEFT    : Board.swapTileIcon(Board.tiles[robotRowPos][robotColPos], Board.tiles[robotRowPos][robotColPos-1]);
				   					 robotColPos--;
				   					 break;
			    		case RIGHT   : Board.swapTileIcon(Board.tiles[robotRowPos][robotColPos], Board.tiles[robotRowPos][robotColPos+1]);
				   					 robotColPos++;
				   					 break;
	  				  	
		    		}    		
		    		
		    	}else{
		    		
		    		((Timer)evt.getSource()).stop();

		    		//Check if robot successfully back to the base or not
		    		if(Board.isRobotReachBase){
		    			
		    			JOptionPane.showMessageDialog(null, "Mission Complete :)", "Nice!", JOptionPane.PLAIN_MESSAGE); 
		    			
		    		}else{
		    			
		    			JOptionPane.showMessageDialog(null, "Mission Fail :(", "Oh no!", JOptionPane.ERROR_MESSAGE); 
		    			
		    		}
		    		
		    		resetValue();
		    		
		    	}
		   
		    	moveIndex++;
		    }    
		});
		
		timer.start();
		
	}
	
	private void boardMenuAction(Sprite sprite, String statusLblTxt){
		
		spriteToPut = sprite;
		
		//prevent duplicate sprite
		removeSpriteFromTile(spriteToPut);
		
		statusLbl.setText(statusLblTxt);
		
		enableMenu = false;
		
		setEnableAllMenu(enableMenu);		
		
	}
	
	private void setEnableAllMenu(boolean enable){
		
		for(int i=0; i < getJMenuBar().getComponentCount(); i++){
			
			getJMenuBar().getMenu(i).setEnabled(enable);
			
		}
		
	}
	
	private void removeSpriteFromTile(Sprite sprite){
		
		for(int i=0 ; i < Board.MAX_ROW; i++){
			
			for(int j=0 ; j < Board.MAX_COLUMN; j++){
			
				Object spriteInTile = Board.tiles[i][j].getSprite().getClass();
				
				if(spriteInTile.equals(sprite.getClass()))
					Board.tiles[i][j].setSprite(ground);

			}
		
		}
		
	}
	
	private void resetValue(){
		
			Board.isRobotReachBase = false;
		
			Board.isBasePut = false;
			Board.isRobotPut = false;
			isAnimationPlay = false;
			
			removeSpriteFromTile(robot);
			removeSpriteFromTile(base);
			
			statusLbl.setText(" ");
			
			robotMoves.clear();
			
			moveIndex = -1;
			
			setEnableAllMenu(true);
	}

}
