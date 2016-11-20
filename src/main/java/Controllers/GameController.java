package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Entities.Stone;
import Services.BoardService;

@RestController
public class GameController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(path = "/stones", method = RequestMethod.GET)
    public List<Stone> stones() {
		return boardService.getStones();
    }
	
	@RequestMapping(path = "/reset", method = RequestMethod.GET)
    public boolean reset() {
		boardService.resetBoard();
		return true;
    }
	
	@RequestMapping(path = "/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Stone stone) {
		return boardService.setMove(stone);
    }
	
	@RequestMapping(path = "/remove", method = RequestMethod.DELETE)
    public boolean remove(@RequestBody Stone stone) {
		return boardService.removeMove(stone);
    }
	
	@RequestMapping(path = "/setturn", method = RequestMethod.POST)
    public boolean setTurn(@RequestBody int turn) {
		return boardService.setTurn(turn);
    }
	
	@RequestMapping(path = "/getturn", method = RequestMethod.GET)
    public int getTurn() {
		return boardService.getTurn();
    }
	
	@RequestMapping(path = "/whites", method = RequestMethod.GET)
    public int getWhites() {
		return boardService.getStoneCount(0);
    }
	
	@RequestMapping(path = "/blacks", method = RequestMethod.GET)
    public int getBlacks() {
		return boardService.getStoneCount(1);
    }
	
	@RequestMapping(path = "/skips", method = RequestMethod.GET)
    public int getSkips() {
		return boardService.getSkipCount();
    }
	
	@RequestMapping(path = "/skips/add", method = RequestMethod.GET)
    public boolean addSkip() {
		boardService.addSkip();
		return true;
    }
	
	@RequestMapping(path = "/skips/reset", method = RequestMethod.GET)
    public boolean resetSkipCount() {
		boardService.resetSkipCount();
		return true;
    }
}
