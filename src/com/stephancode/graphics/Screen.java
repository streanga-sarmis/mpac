package com.stephancode.graphics;

public class Screen {

	private int w;
	private int h;
	
	public int[] pixels;
	
	public Screen(int w, int h){
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
	}
	
	public void render(int tile, int x0, int y0, int color, byte mirror){
		render(Art.tileset, tile, x0, y0, color, mirror, 16);
	}
	
	public void render(Art art, int tile, int x0, int y0, int color, byte mirror, int size){
		
		int xp = tile % (art.w / size); // change 128 if the file size changes
		int yp = tile / (art.w / size); 
		
		int tileOffset = (xp * size) + (yp * size) * 128;
		
		for(int y = 0; y < size; y++){
			int yt = y + y0;
			// @TODO add mirror
			if(((mirror & 0x01) >> 0) == 0x01)
				yt = 15 - y + y0;

			for(int x = 0; x < size; x++){
				int xt = x + x0;
				// @TODO add mirror
				if(((mirror & 0x10) >> 4) == 0x01)
					xt = 15 - x + x0;
				
				if(xt < 0 || xt >= w || yt < 0 || yt >= h) continue;
				int col = art.pixels[x + y * art.w + tileOffset]; 
				if(col != 0xffff00ff){
					if(col == 0xff00ff00)
						pixels[xt + yt * w] = color;
					else
						pixels[xt + yt * w] = col;
				}
			}
		}
		
	}

	public void clear(int color) {
		for(int i = 0; i < w * h; pixels[i] = color, i++);
	}
	
}
