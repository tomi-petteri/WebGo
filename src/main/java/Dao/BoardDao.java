package Dao;

import java.util.List;
import java.util.UUID;

import Entities.Board;
import Entities.Group;
import Entities.Stone;

public interface BoardDao {

	void registerBoard(UUID boardId);

	Board getBoard(UUID boardId);

	int getTurn(UUID boardId);

	void setTurn(UUID boardId, int turn);

	int getSkips(UUID boardId);

	void setSkips(UUID boardId, int skips);

	void addSkip(UUID boardId);

	List<Stone> getStones(UUID boardId);

	void clearStones(UUID boardId);

	Stone getStone(UUID boardId, int x, int y);

	void addStone(UUID boardId, Stone stone);

	void removeStone(UUID boardId, Stone s);

	int getStoneCountByColor(UUID boardId, int color);

	void addStone(UUID boardId, UUID groupId, Stone stone);

	void addStones(UUID boardId, UUID groupId, List<Stone> stones);

	void removeStones(UUID boardId, List<Stone> s);

	void createGroup(UUID boardId, Stone stone);

	List<Group> getGroups(UUID boardId);

	List<Group> getGroupsByColor(UUID boardId, int color);

	void removeGroup(UUID boardId, Group group);

	void clearGroups(UUID boardId);

}