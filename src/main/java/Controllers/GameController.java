package Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Entities.Stone;
import Services.BoardService;

@RestController
public class GameController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(path = "/register", method = RequestMethod.POST)
    public boolean register(@RequestBody UUID boardId) {
		boardService.registerBoard(boardId);
		return true;
    }
	
	@RequestMapping(path = "/stones", method = RequestMethod.GET)
    public List<Stone> stones(@RequestHeader("boardId") UUID boardId) {
		return boardService.getStones(boardId);
    }
	
	@RequestMapping(path = "/reset", method = RequestMethod.GET)
    public boolean reset(@RequestHeader("boardId") UUID boardId) {
		boardService.resetBoard(boardId);
		return true;
    }
	
	@RequestMapping(path = "/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Stone stone, @RequestHeader("boardId") UUID boardId) {
		return boardService.setMove(boardId, stone);
    }
	
	@RequestMapping(path = "/remove", method = RequestMethod.DELETE)
    public boolean remove(@RequestBody Stone stone, @RequestHeader("boardId") UUID boardId) {
		return boardService.removeMove(boardId, stone);
    }
	
	@RequestMapping(path = "/setturn", method = RequestMethod.POST)
    public boolean setTurn(@RequestBody int turn, @RequestHeader("boardId") UUID boardId) {
		return boardService.setTurn(boardId, turn);
    }
	
	@RequestMapping(path = "/getturn", method = RequestMethod.GET)
    public int getTurn(@RequestHeader("boardId") UUID boardId) {
		return boardService.getTurn(boardId);
    }
	
	@RequestMapping(path = "/whites", method = RequestMethod.GET)
    public int getWhites(@RequestHeader("boardId") UUID boardId) {
		return boardService.getStoneCount(boardId, 0);
    }
	
	@RequestMapping(path = "/blacks", method = RequestMethod.GET)
    public int getBlacks(@RequestHeader("boardId") UUID boardId) {
		return boardService.getStoneCount(boardId, 1);
    }
	
	@RequestMapping(path = "/skips", method = RequestMethod.GET)
    public int getSkips(@RequestHeader("boardId") UUID boardId) {
		return boardService.getSkipCount(boardId);
    }
	
	@RequestMapping(path = "/skips/add", method = RequestMethod.GET)
    public boolean addSkip(@RequestHeader("boardId") UUID boardId) {
		boardService.addSkip(boardId);
		return true;
    }
	
	@RequestMapping(path = "/skips/reset", method = RequestMethod.GET)
    public boolean resetSkipCount(@RequestHeader("boardId") UUID boardId) {
		boardService.resetSkipCount(boardId);
		return true;
    }
}
