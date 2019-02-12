import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JComponent;

public class Player{

	public BufferedImage image;
	public URL resource = getClass().getResource("run0.png");

	// circle's position
	public int x = 300;
	public int y = 300;
	public int height = 0;
	public int width = 0;

	// animation states
	public int state = 0;

	public Draw comp;
	
	public Player(Draw comp){
		this.comp = comp;
		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		height = image.getHeight();
		width = image.getWidth();
	}

	public void reloadImage(){
		state++;

		if(state == 0){
			resource = getClass().getResource("run0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("run1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("run2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("run3.png");
		}
		else if(state == 4){
			resource = getClass().getResource("run4.png");
		}
		else if(state == 5){
			resource = getClass().getResource("run5.png");
			state = 0;
		}

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void attack(){
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 5; ctr++){
					try {
						if(ctr==4){
							resource = getClass().getResource("run0.png");
						}
						else{
							resource = getClass().getResource("attack"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        comp.repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				comp.checkContact();
			}
		});
		thread1.start();
	}

	public void moveUp(){
		y = y - 5;
		reloadImage();
		comp.repaint();
		comp.checkCollision();
	}

	public void moveDown(){
		y = y + 5;
		reloadImage();
		comp.repaint();
		comp.checkCollision();
	}

	public void moveLeft(){
		x = x - 5;
		reloadImage();
		comp.repaint();
		comp.checkCollision();
	}

	public void moveRight(){
		x = x + 5;
		reloadImage();
		comp.repaint();
		comp.checkCollision();
	}
}