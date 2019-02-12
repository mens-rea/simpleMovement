import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

public class Draw extends JComponent{

	private BufferedImage backgroundImage;
	public URL resource = getClass().getResource("run0.png");

	public Player player;

	// randomizer
	public Random randomizer;

	// enemy
	public int enemyCount;
	Monster[] monsters = new Monster[10];

	public Draw(){
		randomizer = new Random();
		player = new Player(this);
		try{
			backgroundImage = ImageIO.read(getClass().getResource("background.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		spawnEnemy();
		startGame();
	}

	public void startGame(){
		Thread gameThread = new Thread(new Runnable(){
			public void run(){
				while(true){
					try{
						for(int c = 0; c < monsters.length; c++){
							if(monsters[c]!=null){
								monsters[c].moveTo(player.x,player.y);
								repaint();
							}
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
			}
		});
		gameThread.start();
	}

	public void spawnEnemy(){
		if(enemyCount < 10){
			monsters[enemyCount] = new Monster(randomizer.nextInt(500), randomizer.nextInt(500), this);
			enemyCount++;
		}
	}

	public void checkCollision(){
		int xChecker = player.x + player.width;
		int yChecker = player.y;

		for(int x=0; x<monsters.length; x++){
			boolean collideX = false;
			boolean collideY = false;

			if(monsters[x]!=null){
				monsters[x].contact = false;

				if(yChecker > monsters[x].yPos){
					if(yChecker-monsters[x].yPos < monsters[x].height){
						collideY = true;
						System.out.println("collideY");
					}
				}
				else{
					if(monsters[x].yPos - (yChecker+player.height) < monsters[x].height){
						collideY = true;
						System.out.println("collideY");
					}
				}

				if(xChecker > monsters[x].xPos){
					if((xChecker-player.width)-monsters[x].xPos < monsters[x].width){
						collideX = true;
						System.out.println("collideX");
					}
				}
				else{
					if(monsters[x].xPos-xChecker < monsters[x].width){
						collideX = true;
						System.out.println("collideX");
					}
				}
			}

			if(collideX && collideY){
				System.out.println("collision!");
				monsters[x].contact = true;
			}
		}
	}

	public void checkContact(){
		for(int x=0; x<monsters.length; x++){
			if(monsters[x]!=null){
				if(monsters[x].contact){
					monsters[x].life = monsters[x].life - 10;
				}
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);

		// character grid for hero
		// g.setColor(Color.YELLOW);
		// g.fillRect(player.x, player.y, player.width, player.height);
		g.drawImage(player.image, player.x, player.y, this);
		
		for(int c = 0; c < monsters.length; c++){		
			if(monsters[c]!=null){
				// character grid for monsters
				// g.setColor(Color.BLUE);
				// g.fillRect(monsters[c].xPos, monsters[c].yPos+5, monsters[c].width, monsters[c].height);
				g.drawImage(monsters[c].image, monsters[c].xPos, monsters[c].yPos, this);
				g.setColor(Color.GREEN);
				g.fillRect(monsters[c].xPos+7, monsters[c].yPos, monsters[c].life, 2);
			}	
		}
	}

	public void checkDeath(){
		for(int c = 0; c < monsters.length; c++){
			if(monsters[c]!=null){
				if(!monsters[c].alive){
					monsters[c] = null;
				}
			}			
		}
	}
}