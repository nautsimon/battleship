
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GUI implements ActionListener, MouseListener, MouseMotionListener {

	JFrame window = new JFrame ("BATTLESHIP");
	JFrame aiwindow = new JFrame ("AI");
	JPanel mainpanel = new JPanel (new GridLayout (1,2,50,50));
	JMenuBar menubar = new JMenuBar ();
	JPanel right = new JPanel (new GridLayout (2,2,2,2));
	JPanel display = new JPanel (new GridLayout (10,10,5,5));
	JPanel controlpanel = new JPanel (new GridLayout (2,1,2,2));
	JPanel buttoncombo = new JPanel (new GridLayout (2,1,2,2));
	JPanel aiholder  = new JPanel (new GridLayout (1,2,2,2));
	JPanel statspanel  = new JPanel (new GridLayout (3,1,3,3));
	JMenu file = new JMenu ("File");
	JMenuItem easy = new JMenuItem ("Easy AI");
	JMenuItem hard = new JMenuItem ("Hard AI");
	JMenuItem newgam = new JMenuItem ("New");
	JMenuItem load = new JMenuItem ("Load");
	JMenuItem save = new JMenuItem ("Save");
	JMenuItem quit = new JMenuItem ("Quit");
	JMenu about = new JMenu ("About");
	JLabel hits = new JLabel ("Hits: 0");
	JLabel misses = new JLabel ("Misses: 0");
	
	JMenuItem donate = new JMenuItem ("Donate");
	JLabel [][] playergrid = new JLabel[10][10];
	JLabel [] boats =  new JLabel[5];
	JLabel [][] aigrid = new JLabel[10][10];
	JPanel aipanel = new JPanel(new GridLayout (10,10,2,2));
	ImageIcon water = new ImageIcon("/battleship/src/water.jpg"); 
	JButton lateral = new JButton ("X is true");
	String[] boatnames = { "aircraft carrier", "destroyer","battleship", "submarine", "cruiser" };
	String[] boatnamez = { "aircraft carrier", "destroyer","battleship", "submarine", "cruiser" };
	JComboBox<String> boatlist = new JComboBox<String>(boatnames);
	boolean [] placed = {false, false,false, false, false};
	int [][] aiall = new int [10][10];
	int [][] playerall = new int [10][10];
	Random rand = new Random();
	Random randy = new Random();
	Random randlat = new Random();
	Random aishootx = new Random();
	Random aishooty = new Random(); 
	int [][] shotsmade = new int[10][10];
	int aiplaysmade = 1;
	
	

//CONSTRUCTOR
public GUI() {
	mainpanel.add(display);
	mainpanel.add(controlpanel);
	
	window.setJMenuBar(menubar);
	//FILE
	
	menubar.add(file);
	newgam.addActionListener(this);
	file.add(newgam);
	load.addActionListener(this);
	file.add(load);
	save.addActionListener(this);
	file.add(save);
	easy.addActionListener(this);
	file.add(easy);
	hard.addActionListener(this);
	file.add(hard);
	quit.addActionListener(this);
	file.add(quit);
	
	
	//About
	
	
	menubar.add(about);
	about.add(donate);
	donate.addActionListener(this);
	
	//select/place
	boatlist.setSelectedIndex(0);
	boatlist.addActionListener(this);
	buttoncombo.add(BorderLayout.CENTER, boatlist);
	buttoncombo.add(lateral);
	controlpanel.add(buttoncombo);
	lateral.addActionListener(this);
	controlpanel.add(aiholder);
	aiholder.add(aipanel);
	aiholder.add(statspanel);
	statspanel.add(hits);
	statspanel.add(misses);
	
	
	for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			aiall[x][y] = 1;
			playerall[x][y] = 1;}}
	for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			playergrid[x][y] = new JLabel();
			playergrid[x][y].setBackground(Color.cyan);
			playergrid[x][y].addMouseListener(this);
			playergrid[x][y].setHorizontalAlignment(SwingConstants.CENTER);
			playergrid[x][y].addMouseMotionListener(this);
			display.add(playergrid[x][y]);
			playergrid[x][y].setOpaque(true);

			
		}
	
	}
	for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			aigrid[x][y] = new JLabel();
			aigrid[x][y].setBackground(Color.cyan);
			aigrid[x][y].addMouseListener(this);
			aigrid[x][y].setHorizontalAlignment(SwingConstants.CENTER);
			aigrid[x][y].addMouseMotionListener(this);
			aipanel.add(aigrid[x][y]);
			aigrid[x][y].setOpaque(true);
			aigrid[x][y].setText(letters[x] + Integer.toString(y));
			aigrid[x][y].setText(letters[x] + Integer.toString(y));
		}
	
	}
	
	display.addMouseListener(this);
	for(int i = 0; i <5; i++) {
		
	}
	
	window.add(mainpanel);
	window.setVisible (true);
	window.setSize (1500, 800);
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//for (int i = 0; i <4; i++) {
		placeaiboats();

}
int originx = 0;
int originy = 0;
int boatselect = 0;
boolean isx = true;
String [] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
int [] boatsize = {5, 4, 3, 3, 2};
boolean play = true;
boolean [][] placeded = new boolean[10][10];
int count = 0;
int boatercoater = 0;
boolean latrand = true;
int xx = 0;
int yyy =0;
boolean work = true;
int boatsizecheck = 0;
int playerokplace = 0;
int numofboatsplaced = 0;
boolean playersturn = true;
boolean canshoot = false;
int deadaiboats = 0;
int deadplayerboats = 0;
boolean gamecontinue = true;
int playershits = 0;
int playersmisses= 0;
int aihits = 0;
boolean used = false;
int [][]spots = new int [5][5];
String user = "admin";
String password = "ok";


@Override
public void actionPerformed(ActionEvent e) {
	
//this stuff happens when something is clicked
if (e.getSource().equals(easy)) {
		//System.out.print(playerall[0][0]);
		//System.out.print(playerall[9][9]);
		
		
		System.out.println("Easy AI activated");
	}
if (e.getSource().equals(newgam)) {
	playershits = 0;
	misses.setText("Misses: 0");
	hits.setText("Hits: 0");
	aihits = 0;
	numofboatsplaced = 0;
	canshoot = false;
	boatercoater = 0;
	for(int i = 0; i < 5; i++) {
		placed [i] = false;
	}
	for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			aiall[x][y] = 1;
			playerall[x][y] = 1;
			aigrid[x][y].setBackground(Color.CYAN);
			playergrid[x][y].setBackground(Color.CYAN);
			}
		}
	placeaiboats();
	}
if (e.getSource().equals(quit)) {
	
	System.exit(0);
}
if (e.getSource().equals(load)) {
	Scanner r = new Scanner(System.in);  
	System.out.println("Enter username: ");
	String userss = r.next();
	System.out.println("Enter password: ");
	String passwordss = r.next();
	r.close(); 
	if (user.equals(userss) && password.equals(passwordss)) {
		System.out.println("Game Loaded");
	int loadingup = 0;
    FileReader fr;
    try {
      fr = new FileReader (new File("C:\\\\\\\\Users\\\\\\\\Simon\\\\\\\\eclipse-workspace\\\\\\\\battleship\\\\\\\\src\\\\\\\\save.txt"));
      BufferedReader br = new BufferedReader (fr);
      String line = br.readLine();
      while (line != null) {
          //System.out.println(line);
          for(int i = 0; i <10; i++) {
        	  aiall[i][loadingup] =  Character.getNumericValue(line.charAt(i));
        	  System.out.println( aiall[i][loadingup]);
        	 
          }
          
          line = br.readLine();
          loadingup++;
      }
      
      br.close();
      
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
	loadingup = 0;
    try {
      fr = new FileReader (new File("C:\\\\\\\\Users\\\\\\\\Simon\\\\\\\\eclipse-workspace\\\\\\\\battleship\\\\\\\\src\\\\\\\\savefile.txt"));
      BufferedReader br = new BufferedReader (fr);
      String line = br.readLine();
      while (line != null) {
          //System.out.println(line);
          for(int i = 0; i <10; i++) {
        	  playerall[i][loadingup] =  Character.getNumericValue(line.charAt(i));
        	  System.out.println( playerall[i][loadingup]);
        	 
          }
          
          line = br.readLine();
          loadingup++;
      }
      
      br.close();
      
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    loadingup = 0;
    try {
      fr = new FileReader (new File("C:\\\\\\\\Users\\\\\\\\Simon\\\\\\\\eclipse-workspace\\\\\\\\battleship\\\\\\\\src\\\\\\\\player hits.txt"));
      BufferedReader br = new BufferedReader (fr);
      String line = br.readLine();
      while (line != null) {
          playershits = Character.getNumericValue(line.charAt(0));
          playersmisses = Character.getNumericValue(line.charAt(1));
          line = br.readLine();
     
      }
      
      br.close();
      
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
	canshoot = true;
	boatercoater = 5;
	numofboatsplaced = 5;
	System.out.println("players hits: " + playershits);
	for(int i = 0; i < 5; i++) {
		placed [i] = true;
	}
	misses.setText("Misses: " + playersmisses);
	hits.setText("Hits: " + playershits);
    for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			if (playerall[x][y] == 1) {
				playergrid[x][y].setBackground(Color.CYAN);
			}
			if (playerall[x][y] == 2) {
				playergrid[x][y].setBackground(Color.GRAY);
			}
			if (playerall[x][y] == 4) {
				playergrid[x][y].setBackground(Color.RED);
			}
			if (playerall[x][y] == 3) {
				playergrid[x][y].setBackground(Color.lightGray);
			}
			if (aiall[x][y] == 1 || aiall[x][y] == 2) {
				aigrid[x][y].setBackground(Color.CYAN);
			}
			if (aiall[x][y] == 4) {
				aigrid[x][y].setBackground(Color.RED);
			}
			if (aiall[x][y] == 3) {
				aigrid[x][y].setBackground(Color.lightGray);
			}
		}
		}

	}
	else {
		System.out.println("WRONG USERNAME/PASSWORD");
	}
	
	}
if (e.getSource().equals(save)) {
	Scanner reader = new Scanner(System.in);  
	System.out.println("Enter username: ");
	String users = reader.next();
	System.out.println("Enter password: ");
	String passwords = reader.next();
	reader.close(); 
	if (user.equals(users) && password.equals(passwords)) {
		
	System.out.println("Game Saved");
	try {
		PrintWriter pw = new PrintWriter("C:\\\\Users\\\\Simon\\\\eclipse-workspace\\\\battleship\\\\src\\\\savefile.txt");
		pw.close();
        FileWriter writer = new FileWriter("C:\\Users\\Simon\\eclipse-workspace\\battleship\\src\\savefile.txt", true);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
            	writer.write("");
                  writer.write(playerall[x][y]+ "");
            }
            writer.write("\n");   
        }
        writer.close();
    } 
	catch (IOException e1) {
        e1.printStackTrace();
    }
	try {
		PrintWriter pw = new PrintWriter("C:\\Users\\Simon\\eclipse-workspace\\battleship\\src\\save.txt");
		pw.close();
        FileWriter writer = new FileWriter("C:\\Users\\Simon\\eclipse-workspace\\battleship\\src\\save.txt", true);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
            	writer.write("");
                  writer.write(aiall[x][y]+ "");
                  
            }
            writer.write("\n");   
        }
        writer.close();
    } 
	catch (IOException e1) {
        e1.printStackTrace();
    }
	try {
		PrintWriter pw = new PrintWriter("C:\\Users\\Simon\\eclipse-workspace\\battleship\\src\\player hits.txt");
		pw.close();
        FileWriter writer = new FileWriter("C:\\Users\\Simon\\eclipse-workspace\\battleship\\src\\player hits.txt", true);
        writer.write("");
            	writer.write(playershits+"");
            	writer.write(playersmisses+"");

        writer.close();
        
	}
	
	catch (IOException e1) {
        e1.printStackTrace();
    }
	}
	else {
		System.out.println("WRONG USERNAME/PASSWORD");
	}
}

if (e.getSource().equals(hard)) {
System.out.println("Hard AI activated");
}

if (e.getSource().equals(donate)) {
	JOptionPane.showMessageDialog(null, "Vist www.oriondmd.org/donate");

}

if (e.getSource().equals(lateral)) {
	isx = !isx;
	lateral.setText("X is " + isx);
}

//integer.parseInt() --> string to number
//integer.toString() --> number to string


}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			if (e.getSource() == playergrid[x][y]){ 
				originx = x;
				originy = y;
				boatselect = boatlist.getSelectedIndex();
				playergrid[x][y].setText(letters[x] + Integer.toString(y));
					if(isx) {
						for (int i = 1; i < boatsize[boatselect]; i++) {
							
							if(x+i <10) {
							playergrid[x+i][y].setText(letters[x+i] + Integer.toString(y));}}
					}
					
					if (isx  == false) {
						for (int i = 1; i < boatsize[boatselect]; i++) {
							if(y - i>=0) {
							playergrid[x][y-i].setText(letters[x] + Integer.toString(y-i));}}
						
					}
				
			}
				if (e.getSource() == display || e.getSource() == mainpanel|| e.getSource() == controlpanel) {
					for(int yy = 0; yy <10; yy++) {
						for(int xx = 0; xx <10; xx++) {
							playergrid[x][y].setText(" ");}
					}
				}
		}
	}
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
	
	for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			
			
			
			
			if (e.getSource() == playergrid[x][y] && placed[boatlist.getSelectedIndex()] == false){ 
				boatselect = boatlist.getSelectedIndex();
				if(isx) {
					for (int i = 0; i < boatsize[boatselect]; i++) {
						if(x + i < 10 && playerall[x+i][y]==1) {
						playerokplace++;
					}
					}
					if (playerokplace == boatsize[boatselect]) {
						playerokplace = 0;
						numofboatsplaced++;
						playergrid[x][y].setBackground(Color.GRAY);
						playerall[x][y]=2;
						placed[boatlist.getSelectedIndex()] = true;
						System.out.println(boatnames[boatselect] + " is placed");
						for (int i = 0; i < boatsize[boatselect]; i++) {
						playergrid[x+i][y].setBackground(Color.GRAY);
						playerall[x+i][y]=2;
					}
						if (boatselect == 0) {
							Ship ship0 = new Ship(true, boatnames[boatselect], x, y, true, 5, 5);
							
						}
						if (boatselect == 1) {
							Ship ship1 = new Ship(true, boatnames[boatselect], x, y, true, 4, 4); 
							
						}
						if (boatselect == 2) {
							Ship ship2 = new Ship(true, boatnames[boatselect], x, y, true, 3, 3); 
							
						}
						if (boatselect == 3) {
							Ship ship3 = new Ship(true, boatnames[boatselect], x, y, true, 3, 3); 
							
						}
						if (boatselect == 4) {
							Ship ship4 = new Ship(true, boatnames[boatselect], x, y, true, 2, 2); 
							
						}
					}
						
					else {
						playerokplace = 0;
						JOptionPane.showMessageDialog(null, "YOU DUM, CHOOSE A COORDINATE THAT ACTUALLY FITS YOUR BOAT");
					}	
				}
				if(isx == false) {
					for (int i = 0; i < boatsize[boatselect]; i++) {
						if(y - i >=0 && playerall[x][y - i]==1) {
							playerokplace++;
						}
		
					}
					if (playerokplace == boatsize[boatselect]) {
						playergrid[x][y].setBackground(Color.GRAY);
						numofboatsplaced++;
						playerall[x][y]=2;
						playerokplace = 0;
						placed[boatlist.getSelectedIndex()] = true;
						System.out.println(boatnames[boatselect] + " is placed");
						for (int i = 0; i < boatsize[boatselect]; i++) {
						playergrid[x][y - i].setBackground(Color.GRAY);
						playerall[x][y - i]=2;
						
						
						if (boatselect == 0) {
							Ship ship1 = new Ship(true, boatnames[boatselect], x, y, false, 5, 5); 
						}
						if (boatselect == 1) {
							Ship ship2 = new Ship(true, boatnames[boatselect], x, y, false, 4, 4); 
						}
						if (boatselect == 2) {
							Ship ship3 = new Ship(true, boatnames[boatselect], x, y, false, 3, 3); 
						}
						if (boatselect == 3) {
							Ship ship4 = new Ship(true, boatnames[boatselect], x, y, false, 3, 3); 
						}
						if (boatselect == 4) {
							Ship ship5 = new Ship(true, boatnames[boatselect], x, y, false, 2, 2); 
						}
						 
					}
				}
						
					else {
						playerokplace = 0;
						JOptionPane.showMessageDialog(null, "YOU DUM, CHOOSE A COORDINATE THAT ACTUALLY FITS YOUR BOAT");
					}	
				}
				if (numofboatsplaced == 5) {
					System.out.println("Initiating game");
					playersturn = true;
					canshoot = true;
					gameplay();}
		}
		}

	}
	
	
	for(int y = 0; y <10; y++) {
		for(int x = 0; x <10; x++) {
			if(e.getSource() == aigrid[x][y] && playersturn && canshoot) {
				//if the player hits
				if (aiall[x][y] == 2) {
					aigrid[x][y].setBackground(Color.red);
					aiall[x][y] = 4;
					playershits++;
					System.out.println("Hit!");
					hits.setText("Hits: " + playershits);
					canshoot = false;
					playersturn = false;
				aishoot();
				try{
				    AudioInputStream audioInputStream =
				        AudioSystem.getAudioInputStream(
				            this.getClass().getResource("hit.wav"));
				    Clip clip = AudioSystem.getClip();
				    clip.open(audioInputStream);
				    clip.start();
				}
				catch(Exception ex)
				{
				}
				}
				//if the player misses
				if (aiall[x][y] == 1) {
					aigrid[x][y].setBackground(Color.LIGHT_GRAY);
					aiall[x][y] = 3;
					playersmisses++;
					System.out.println("Miss!");
					misses.setText("Misses: " + playersmisses);
					canshoot = false;
					playersturn = false;
					aishoot();
					try{
					    AudioInputStream audioInputStream =
					        AudioSystem.getAudioInputStream(
					            this.getClass().getResource("miss.wav"));
					    Clip clip = AudioSystem.getClip();
					    clip.open(audioInputStream);
					    clip.start();
					}
					catch(Exception ex)
					{
					}
				}
			
				if (playershits == 17) {
					JOptionPane.showMessageDialog(null, "YOU WIN!!!");
					System.out.println("YOU WIN!");
					System.out.println("Press new game to play again");
					canshoot = false;
					try{
					    AudioInputStream audioInputStream =
					        AudioSystem.getAudioInputStream(
					            this.getClass().getResource("win.wav"));
					    Clip clip = AudioSystem.getClip();
					    clip.open(audioInputStream);
					    clip.start();
					}
					catch(Exception ex)
					{
					}
				}
				if (aihits == 17) {
					JOptionPane.showMessageDialog(null, "YOU LOST!!!");
					System.out.println("YOU LOSE!");
					System.out.println("Press new game to play again");
					canshoot = false;
					try{
					    AudioInputStream audioInputStream =
					        AudioSystem.getAudioInputStream(
					            this.getClass().getResource("lose.wav"));
					    Clip clip = AudioSystem.getClip();
					    clip.open(audioInputStream);
					    clip.start();
					}
					catch(Exception ex)
					{
					}
				}
			}
		}
	}
}
					
						
		

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseDragged(MouseEvent arg0) {
	// TODO Auto-generated method stub

}

@Override
public void mouseMoved(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}
public void placeaiboats() {
while (boatercoater <5) {
	xx = rand.nextInt(5) + 0;
	yyy = randy.nextInt(5) + 0;
	yyy = yyy + 4;
	System.out.println("x: " + xx);
	System.out.println("y: " + yyy);
	latrand = !latrand;
	if (aiall[xx][yyy] == 1){ 
		if(latrand) {
			System.out.println("x");
		for (int i = 0; i < boatsize[boatercoater]; i++) {
			if(aiall[xx+i][yyy] == 1) {
				boatsizecheck++;

			}
			
		}
		if (boatsizecheck == boatsize[boatercoater]) {
			
			boatsizecheck= 0;
			//aigrid[xx][yyy].setBackground(Color.RED);
			aiall[xx][yyy] = 2;
			for (int i = 0; i < boatsize[boatercoater]; i++) {
				System.out.println("boat size: " +boatsize[boatercoater]);
				//aigrid[xx + i][yyy].setBackground(Color.RED);
				aiall[xx + i][yyy] = 2;
			}
			boatercoater++;
		}
		else {
			boatsizecheck= 0;
			System.out.println("error");
		}
	}
		if(latrand == false) {
			System.out.println("y");
			for (int i = 0; i < boatsize[boatercoater]; i++) {
				if(aiall[xx][yyy-i] == 1) {
					boatsizecheck++;

				}	
			}
			
			if (boatsizecheck == boatsize[boatercoater]) {
				
				boatsizecheck= 0;
				//aigrid[xx][yyy].setBackground(Color.RED);
				aiall[xx][yyy] = 2;
				for (int i = 0; i < boatsize[boatercoater]; i++) {
					System.out.println("boat size: " + boatsize[boatercoater]);
					//aigrid[xx][yyy - i].setBackground(Color.RED);
					aiall[xx][yyy - i] = 2;
				}
				boatercoater++;
			}
			else {
				boatsizecheck= 0;
				System.out.println("error");
			}
		}
	}
	
}
}

public void gameplay(){
	System.out.println("Game started");
	
}

public void aishoot(){
	int aishotx = aishootx.nextInt(9) + 0;
	int aishoty = aishooty.nextInt(9) + 0;
	System.out.println("x: " + aishotx + "    Y: " + aishoty);
	while(playerall[aishotx][aishoty]==3 || playerall[aishotx][aishoty]==4) {
		aishotx = aishootx.nextInt(9) + 0;
		aishoty = aishooty.nextInt(9) + 0;
		System.out.println("REFORMED: x: " + aishotx + "    Y: " + aishoty);
	}
	if (playerall[aishotx][aishoty]==2 && playerall[aishotx][aishoty]!=3) { //makes the shot
		playerall[aishotx][aishoty]=4;
		playergrid[aishotx][aishoty].setBackground(Color.RED);
		aihits++;
		

		playersturn = true;
		canshoot = true;
	}
	if (playerall[aishotx][aishoty]==1 && playerall[aishotx][aishoty]!=3) { //misses the shot
		playerall[aishotx][aishoty]=3;
		playergrid[aishotx][aishoty].setBackground(Color.lightGray);
		playersturn = true;
		canshoot = true;
	}
	aishotx = aishootx.nextInt(9) + 0;
	aishoty = aishooty.nextInt(9) + 0;
}
	

}
			

