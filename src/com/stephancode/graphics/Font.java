package com.stephancode.graphics;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOP" +
				   "QRSTUVWXYZ      " +
				   "0123456789:,.-%!";
	
	public static void write(Screen screen, String msg, int xa, int ya, int color){
		msg = msg.toUpperCase();
		for(int i = 0; i < msg.length(); i++){
			int index = chars.indexOf(msg.charAt(i));
			
			screen.render(Art.font, index, xa + i * 9, ya, color, (byte)0x00, 8);
		}
		
	}
	
}
