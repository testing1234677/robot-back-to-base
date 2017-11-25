package org.simpleai.ai.factory;

import org.simpleai.ai.service.Ai;
import org.simpleai.ai.service.imp.BrokenAi;
import org.simpleai.ai.service.imp.SuccessAi;

//Using factory design pattern to create AI object
public class AiFactory {

	public static Ai getAi(String type){
		
		if(type.equalsIgnoreCase("SuccessAi")){
			
			return new SuccessAi();
			
		}else if(type.equalsIgnoreCase("BrokenAi")){
			
			return new BrokenAi();
			
		}
		
		return null;
		
	}
	
}
