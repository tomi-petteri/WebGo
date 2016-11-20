package Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dao.FakeBoardDao;
import Entities.Stone;

@Service
public class BoardService {

	@Autowired
	private FakeBoardDao board;
	
	public void resetBoard()
	{
		board.setTurn(1);
		board.setSkips(0);
		board.clearStones();
	}
	
	public boolean setMove(Stone stone)
	{
		boolean success = true;
		
		Stone s = board.getStone(stone.getX(), stone.getY()); 
				
		if(s == null)
		{
			board.addStone(stone);
		}
		else
		{
			success = false;
		}
			
		return success;
	}
	
	public boolean removeMove(Stone stone)
	{
		boolean success = true;
		
		Stone s = board.getStone(stone.getX(), stone.getY()); 
		
		if(s != null)
			board.removeStone(s);
		else
			success = false;
		
		return success;
	}
	
	public boolean setTurn(int turn)
	{
		board.setTurn(turn);
		return true;
	}
	
	public int getTurn()
	{
		return board.getTurn();
	}

	public int getStoneCount(int color) 
	{
		return board.getStoneCountByColor(color);
	}

	public List<Stone> getStones() {
		return board.getStones();
	}

	public int getSkipCount() {
		return board.getSkips();
	}

	public void addSkip() {
		board.addSkip();
	}

	public void resetSkipCount() {
		board.setSkips(0);
	}
}