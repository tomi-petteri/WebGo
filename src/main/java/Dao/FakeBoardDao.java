package Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import Entities.Area;
import Entities.Stone;

@Repository
public class FakeBoardDao {
	private int turn = 1; // 0 = white, 1 = black
	private int skips = 0;
	private List<Stone> stones = new ArrayList<Stone>();
	private List<Area> areas = new ArrayList<Area>();
	
	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getSkips() {
		return skips;
	}

	public void setSkips(int skips) {
		this.skips = skips;
	}
	
	public void addSkip() {
		this.skips++;	
	}

	public List<Stone> getStones() {
		return stones;
	}

	public void setStones(List<Stone> stones) {
		this.stones = stones;
	}

	public void clearStones() {
		stones.clear();
	}

	public Stone getStone(int x, int y) {
		Stone stone = null;
		try
		{
			List<Stone> l = stones.stream().filter(s -> s.getX() == x && s.getY() == y).collect(Collectors.toList());
			stone = l.get(0);
		}
		catch(Exception e)
		{
			
		}
		
		return stone;
	}

	public void addStone(Stone stone) {
		stones.add(stone);
	}

	public void removeStone(Stone s) {
		stones.remove(s);
	}

	public int getStoneCountByColor(int color) {
		return stones.stream().filter(s -> s.getColor() == color).collect(Collectors.toList()).size();
	}
}
