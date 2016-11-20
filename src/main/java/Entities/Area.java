package Entities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class Area {
	@Autowired
	private List<Stone> stones;
	private int color;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public List<Stone> getStones() {
		return stones;
	}

	public void setStones(List<Stone> stones) {
		this.stones = stones;
	}
	
	public boolean isNeighbour(Stone s)
	{
		boolean neighbour = false;
		
		
		
		return neighbour;
	}
}
