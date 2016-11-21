package Services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import Dao.BoardDao;
import Entities.Group;
import Entities.Stone;

@Service
public class BoardService {

	@Autowired
	@Qualifier("mongoData")
	private BoardDao boardDao;
	
	public void registerBoard(UUID boardId) {
		boardDao.registerBoard(boardId);	
	}
	
	public void resetBoard(UUID boardId)
	{
		boardDao.setTurn(boardId, 1);
		boardDao.setSkips(boardId, 0);
		boardDao.clearStones(boardId);
		boardDao.clearGroups(boardId);
	}
	
	public boolean setMove(UUID boardId, Stone stone)
	{
		boolean success = true;
		
		Stone s = boardDao.getStone(boardId, stone.getX(), stone.getY()); 
				
		if(s == null)
		{
			List<Stone> allStones = boardDao.getStones(boardId);
			List<Group> groups = boardDao.getGroupsByColor(boardId, stone.getColor()); 
			List<Group> neighbourGroups = groups.stream().filter(g -> g.isNeighbour(stone) == true).collect(Collectors.toList());
			
			if(neighbourGroups.size() == 0) // 
			{
				boardDao.addStone(boardId, stone);
				//allStones.add(stone);
				boardDao.createGroup(boardId, stone);	
			}
			else
			{
				Group mergedGroup = neighbourGroups.get(0);

				for(int i = 1; i < neighbourGroups.size(); i++)
				{
					Group g = neighbourGroups.get(i);
					boardDao.addStones(boardId, mergedGroup.getId(), g.getStones());
					boardDao.removeGroup(boardId, g);
				}

				boardDao.addStone(boardId, stone);
				boardDao.addStone(boardId, mergedGroup.getId(), stone);
			}
			
			allStones = boardDao.getStones(boardId);
			RemoveSurroundedGroups(boardId, allStones, boardDao.getGroups(boardId));
		}
		else
		{
			success = false;
		}
			
		return success;
	}
	
	private void RemoveSurroundedGroups(UUID boardId, List<Stone> allStones, List<Group> groups) {
		for(int i = 0; i < groups.size(); i++)
		{
			Group group = groups.get(i);
			if(GroupSurroundedByOpponent(allStones, group))
			{
				boardDao.removeStones(boardId, group.getStones());
				boardDao.removeGroup(boardId, group);
			}
		}
	}

	private boolean GroupSurroundedByOpponent(List<Stone> allStones, Group g) {
		boolean surrounded = true;
		
		for(int i = 0; i < g.getStones().size(); i++)
		{
			// Jos vieressä on tyhjä paikka, ei kiviryhmä voi olla saarrettu
			if(hasFreeNeighbour(allStones, g.getStones().get(i)))
			{
				surrounded = false;
				break;
			}
		}
				
		return surrounded;
	}

	private boolean hasFreeNeighbour(List<Stone> allStones, Stone s) {
		boolean hasFreeNeighbour = false;
		
		List<Stone> neighbourStones = getNeighbours(allStones, s);

		if(neighbourStones.size() < s.getNeighbourCount()) // mahdolliset naapurit. laudan reunassa vähemmän
			hasFreeNeighbour = true;
		
		return hasFreeNeighbour;
	}

	private List<Stone> getNeighbours(List<Stone> allStones, Stone s) {
		return allStones.stream().filter(stone -> 
		(stone.getX() == s.getNx1() && stone.getY() == s.getY()) || 
		(stone.getX() == s.getNx2() && stone.getY() == s.getY()) ||
		(stone.getX() == s.getX() && stone.getY() == s.getNy1()) ||
		(stone.getX() == s.getX() && stone.getY() == s.getNy2())).collect(Collectors.toList());
	}

	public boolean removeMove(UUID boardId, Stone stone)
	{
		boolean success = true;
		
		Stone s = boardDao.getStone(boardId, stone.getX(), stone.getY()); 
		
		if(s != null)
			boardDao.removeStone(boardId, s);
		else
			success = false;
		
		return success;
	}
	
	public boolean setTurn(UUID boardId, int turn)
	{
		boardDao.setTurn(boardId, turn);
		return true;
	}
	
	public int getTurn(UUID boardId)
	{
		return boardDao.getTurn(boardId);
	}

	public int getStoneCount(UUID boardId, int color) 
	{
		return boardDao.getStoneCountByColor(boardId, color);
	}

	public List<Stone> getStones(UUID boardId) {
		return boardDao.getStones(boardId);
	}

	public int getSkipCount(UUID boardId) {
		return boardDao.getSkips(boardId);
	}

	public void addSkip(UUID boardId) {
		boardDao.addSkip(boardId);
	}

	public void resetSkipCount(UUID boardId) {
		boardDao.setSkips(boardId, 0);
	}


}