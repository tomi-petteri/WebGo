package Entities;

import java.util.Date;

public class Stone {
	private int x;
	private int y;
	private int nx1;
	private int nx2;
	private int ny1;
	private int ny2;
	private int color;
	private Date timestamp;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		nx1 = x - 45;
		nx2 = x + 45;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		ny1 = y - 45;
		ny2 = y + 45;
	}

	public int getColor() {
		return color;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public int getNx1() {
		return nx1;
	}

	public int getNx2() {
		return nx2;
	}

	public int getNy1() {
		return ny1;
	}

	public int getNy2() {
		return ny2;
	}

	public Stone(int x, int y, int color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.timestamp = new Date();
	}

	public Stone() {
		this.timestamp = new Date();
	}

	public boolean isNeighbour(Stone s) {
		boolean neighbour = false;

		if((nx1 == s.getX() && y == s.getY()) ||
			(nx2 == s.getX() && y == s.getY()) ||
			(x == s.getX() && ny1 == s.getY()) ||
			(x == s.getX() && ny2 == s.getY()))
			neighbour = true;

		return neighbour;
	}
	
	public int getNeighbourCount()
	{
		int count = 0;
		
		if(nx1 >= 0 && nx1 <= 840)
			count++;
		if(nx2 >= 0 && nx2 <= 840)
			count++;
		if(ny1 >= 0 && ny1 <= 840)
			count++;
		if(ny2 >= 0 && ny2 <= 840)
			count++;
		
		return count;
	}
}
