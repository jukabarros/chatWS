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
	
	// Lista com todos os onlines que os novos usuarios recebem
	public static List<String> allOnlines = new ArrayList<String>();

	// Armazena ultimo usuario que entrou no chat
	// Usado para add o atributo na sessao do WS
	public static String lastUserOnline = null;
	
}
