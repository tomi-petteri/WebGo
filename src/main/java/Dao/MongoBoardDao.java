package Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import Entities.Board;
import Entities.Group;
import Entities.Stone;

@Repository
@Qualifier("mongoData")
public class MongoBoardDao implements BoardDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void registerBoard(UUID boardId) {
		Board board = new Board(boardId);
		mongoTemplate.save(board);
	}

	@Override
	public Board getBoard(UUID boardId) {
		Board board = null;
		
		Query query = new Query();
		query.addCriteria(Criteria.where("boardId").is(boardId));
		
		List<Board> bs = mongoTemplate.find(query, Board.class);
		
		if(bs.size() != 0)
			board = bs.get(0);
		
		return board;
	}

	@Override
	public int getTurn(UUID boardId) {
		Board b = getBoard(boardId);
		return b.getTurn();
	}

	@Override
	public void setTurn(UUID boardId, int turn) {
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().set("turn", turn);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public int getSkips(UUID boardId) {
		Board b = getBoard(boardId);
		return b.getSkips();
	}

	@Override
	public void setSkips(UUID boardId, int skips) {
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().set("skips", skips);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public void addSkip(UUID boardId) {
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().inc("skips", 1);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public List<Stone> getStones(UUID boardId) {
		Board b = getBoard(boardId);
		return b.getStones();
	}

	@Override
	public void clearStones(UUID boardId) {
		List<Stone> emptyList = new ArrayList<Stone>();
		
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().set("stones", emptyList);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public Stone getStone(UUID boardId, int x, int y) {
		Board b = getBoard(boardId);
		Stone stone = null;
		
		List<Stone> l = b.getStones().stream().filter(s -> s.getX() == x && s.getY() == y).collect(Collectors.toList());
		
		if(l.size() > 0)
			stone = l.get(0);
		
		return stone;
	}

	@Override
	public void addStone(UUID boardId, Stone stone) {
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().push("stones", stone);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public void removeStone(UUID boardId, Stone s) {
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().pull("stones", s);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public int getStoneCountByColor(UUID boardId, int color) {
		return getBoard(boardId).getStones().stream().filter(s -> s.getColor() == color).collect(Collectors.toList()).size();
	}

	@Override
	public void addStone(UUID boardId, UUID groupId, Stone stone) {
		Query q = new Query(Criteria.where("groups.groupId").is(groupId));
		Update u = new Update().push("groups.$.stones", stone);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public void addStones(UUID boardId, UUID groupId, List<Stone> stones) {
		Query q = new Query(Criteria.where("groups.groupId").is(groupId));
		Update u = new Update().pushAll("groups.$.stones", stones.toArray());
		mongoTemplate.updateFirst(q, u, Board.class);
	}

	@Override
	public void removeStones(UUID boardId, List<Stone> s) {
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().pullAll("stones", s.toArray());
		mongoTemplate.updateFirst(q, u, Board.class);
	}

	@Override
	public void createGroup(UUID boardId, Stone stone) {
		List<Stone> stones = new ArrayList<Stone>();
		stones.add(stone);
		Group g = new Group(stones, stone.getColor());
		
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().push("groups", g);
		mongoTemplate.updateFirst(q,u,Board.class);
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
	public void removeGroup(UUID boardId, Group group) {	
		Query q = new Query(Criteria.where("groups.groupId").is(group.getId()));
		Update u = new Update().pull("groups", group);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

	@Override
	public void clearGroups(UUID boardId) {
		List<Group> emptyList = new ArrayList<Group>();
		
		Query q=new Query(Criteria.where("boardId").is(boardId));
		Update u=new Update().set("groups", emptyList);
		mongoTemplate.updateFirst(q,u,Board.class);
	}

}
