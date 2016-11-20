package Entities;

import java.util.List;
import java.util.UUID;

public class Group {
	private	UUID id;
	private List<Stone> stones;
	private int color;

	public UUID getId() 
	{
		return id;
	}
	
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
	
	public boolean isNeighbour(Stone stone)
	{
		return stones.stream().anyMatch(s -> s.isNeighbour(stone) == true);
	}

	public Group(List<Stone> stones, int color) {
		super();
		this.id = UUID.randomUUID();
		this.stones = stones;
		this.color = color;
	}

	public void addStone(Stone stone) {
		stones.add(stone);
	}
	
	public void removeStone(Stone stone) {
		stones.remove(stone);
	}
}
