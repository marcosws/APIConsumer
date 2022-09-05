package com.github.marcosws.api;

public class Message {
	
	private final static String GREEN_OK = "\u001B[92m";
	private final static String RED_NOK = "\u001B[91m";
	private final static String CYAN_MSG = "\u001B[96m";
	private final static String RESET = "\u001B[0m";
	
	public static void message(String message) {
		System.out.println(CYAN_MSG + message + RESET);
	}
	public static void messageOK(String message) {
		System.out.println(GREEN_OK + message + RESET);
	}
	
	public static void messageNOK(String message) {
		System.out.println(RED_NOK + message +  RESET);
	}

}
