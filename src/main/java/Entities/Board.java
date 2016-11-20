package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {
	private	UUID id;
	private int turn; // 0 = white, 1 = black
	private int skips;
	private List<Stone> stones = new ArrayList<Stone>();
	private List<Group> groups = new ArrayList<Group>();
	
	public UUID getId() {
		return id;
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

	public List<Stone> getStones() {
		return stones;
	}

	public void setStones(List<Stone> stones) {
		this.stones = stones;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Board(UUID boardId) {
		super();
		this.id = boardId;
		this.turn = 1;
		this.skips = 0;
		this.stones = new ArrayList<>();
		this.groups = new ArrayList<>();
	}

	public void addSkip()
	{
		this.skips++;
	}
}
