package Dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import Entities.Board;
import Entities.Group;
import Entities.Stone;

@Repository
@Qualifier("mongoData")
public class MongoBoardDao implements BoardDao {

	@Override
	public void registerBoard(UUID boardId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Board getBoard(UUID boardId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTurn(UUID boardId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTurn(UUID boardId, int turn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSkips(UUID boardId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSkips(UUID boardId, int skips) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSkip(UUID boardId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Stone> getStones(UUID boardId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearStones(UUID boardId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stone getStone(UUID boardId, int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStone(UUID boardId, Stone stone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStone(UUID boardId, Stone s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStoneCountByColor(UUID boardId, int color) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addStone(UUID boardId, UUID groupId, Stone stone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStones(UUID boardId, UUID groupId, List<Stone> stones) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStones(UUID boardId, List<Stone> s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createGroup(UUID boardId, Stone stone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Group> getGroups(UUID boardId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getGroupsByColor(UUID boardId, int color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeGroup(UUID boardId, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearGroups(UUID boardId) {
		// TODO Auto-generated method stub
		
	}

}
