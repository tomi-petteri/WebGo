package Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import Entities.Board;
import Entities.Group;
import Entities.Stone;

@Repository
@Qualifier("memoryData")
public class MemoryBoardDao implements BoardDao {
	private List<Board> boards = new ArrayList<Board>();

	@Override
	public void registerBoard(UUID boardId) {
		Board board = new Board(boardId);
		boards.add(board);
	}
	
	@Override
	public Board getBoard(UUID boardId)
	{
		Board board = null;
		
		List<Board> bs = boards.stream().filter(b -> b.getId().equals(boardId)).collect(Collectors.toList());
		
		if(bs.size() != 0)
			board = bs.get(0);
		
		return board;
	}
	
	@Override
	public int getTurn(UUID boardId) {
		return getBoard(boardId).getTurn();
	}

	@Override
	public void setTurn(UUID boardId, int turn) {
		getBoard(boardId).setTurn(turn);
	}

	@Override
	public int getSkips(UUID boardId) {
		return getBoard(boardId).getSkips();
	}

	@Override
	public void setSkips(UUID boardId,int skips) {
		getBoard(boardId).setSkips(skips);
	}
	
	@Override
	public void addSkip(UUID boardId) {
		getBoard(boardId).addSkip();	
	}

	@Override
	public List<Stone> getStones(UUID boardId) {
		Board board = getBoard(boardId);
		
		List<Stone> stones = null;
		
		if(board != null)
			stones = board.getStones();
		
		return stones;
	}

	@Override
	public void clearStones(UUID boardId) {
		getBoard(boardId).getStones().clear();
	}

	@Override
	public Stone getStone(UUID boardId, int x, int y) {
		Stone stone = null;
		try
		{
			List<Stone> l = getBoard(boardId).getStones().stream().filter(s -> s.getX() == x && s.getY() == y).collect(Collectors.toList());
			stone = l.get(0);
		}
		catch(Exception e)
		{
			
		}
		
		return stone;
	}

	@Override
	public void addStone(UUID boardId, Stone stone) {
		getBoard(boardId).getStones().add(stone);
	}

	@Override
	public void removeStone(UUID boardId, Stone s) {
		Board b = getBoard(boardId);
		Group g = null;
		
		for(Group group : b.getGroups()) { 
		   if(group.getStones().contains(s)) { 
			   g = group;
			   break;
		   }
		}
		
		if(g != null)
			g.removeStone(s);
			
		b.getStones().remove(s);
	}

	@Override
	public int getStoneCountByColor(UUID boardId, int color) {
		return getBoard(boardId).getStones().stream().filter(s -> s.getColor() == color).collect(Collectors.toList()).size();
	}

	@Override
	public void addStone(UUID boardId, UUID groupId, Stone stone) {
		for(Group g : getBoard(boardId).getGroups()) { 
		   if(g.getId().equals(groupId)) { 
		       g.addStone(stone);
		   }
		}
	}

	@Override
	public void addStones(UUID boardId, UUID groupId, List<Stone> stones) {
		for(Group g : getBoard(boardId).getGroups()) { 
			   if(g.getId().equals(groupId)) { 
				   for(Stone s : stones)
					   g.addStone(s);
			   }
			}
	}

	@Override
	public void removeStones(UUID boardId, List<Stone> s) {
		getBoard(boardId).getStones().removeAll(s);
	}

	@Override
	public void createGroup(UUID boardId, Stone stone) {
		List<Stone> s = new ArrayList<>();
		s.add(stone);
		Group g = new Group(s, stone.getColor());
		getBoard(boardId).getGroups().add(g);
	}
	
	@Override
	public List<Group> getGroups(UUID boardId) {
		return getBoard(boardId).getGroups();
	}
	
	@Override
	public List<Group> getGroupsByColor(UUID boardId, int color) {
		return getBoard(boardId).getGroups().stream().filter(a -> a.getColor() == color).collect(Collectors.toList());
	}
	
	@Override
	public void removeGroup(UUID boardId, Group gg) {
		Group group = null;
		
		for(Group g : getBoard(boardId).getGroups()) { 
		   if(g.getId().equals(gg.getId())) { 
		       group = g;
		   }
		}
		
		if(group != null)
			getBoard(boardId).getGroups().remove(group);
	}
	
	@Override
	public void clearGroups(UUID boardId) {
		getBoard(boardId).getGroups().clear();
	}
}
