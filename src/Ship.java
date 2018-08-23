

public class Ship {
private boolean alive; 
private String name; 
private int xorg;
private int yorg;
private boolean isx;
private int size;
private int health; 



public Ship(boolean alive2, String namer, int x, int y, boolean isx2, int size2, int health2) {
	 this.setAlive(alive);
	    this.setName(namer);
	    this.setXorg(xorg);
	    this.setYorg(yorg);
	    this.setIsx(isx);
	    this.size = size;
	    this.health = health;
}

public Ship() {
	// TODO Auto-generated constructor stub
}

public int getHealth() {
	System.out.print("health is: " + health);
    return health;
}

public int getSize() {
    return size;
}

public String getName(int namenum) {
	String[] boatnames = { "Aircraft Carrier", "Destroyer","Battleship", "Submarine", "Cruiser" };
    String name = boatnames[namenum];
	return name;
}

//public boolean alive() {
//    return alive;
//}

//public void setAlive(boolean living) {
//    alive = living;
//}


	
public void hit() {
	health = health - 1;
	if (health == 0) {
		setAlive(false);
		}
}

public boolean isIsx() {
	return isx;
}

public void setIsx(boolean isx) {
	this.isx = isx;
}

public int getYorg() {
	return yorg;
}

public void setYorg(int yorg) {
	this.yorg = yorg;
}

public int getXorg() {
	return xorg;
}

public void setXorg(int xorg) {
	this.xorg = xorg;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public boolean isAlive() {
	return alive;
}

public void setAlive(boolean alive) {
	this.alive = alive;
}
}