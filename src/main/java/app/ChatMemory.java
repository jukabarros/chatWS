package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe main responsavel por armazenar todas 
 * as informacoes do chat (usuarios onlines)
 * @author juccelino
 *
 */
public class ChatMemory {
	
	public static List<String> allOnlines = new ArrayList<String>();
	
	public void addUserOnline(String nickname){
		allOnlines.add(nickname);
		System.out.println("Add user: "+nickname);
	}
	
	public void removeUserOnline(String nickname){
		allOnlines.remove(nickname);
		System.out.println("Remove user: "+nickname);
	}
	
}
