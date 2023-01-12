package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;

import java.util.Map;

public interface BoardRepository {

    void save(Board board);

    Board findById(Integer id);

    Map<Integer, Board> findAll();

    Map<Integer, Board> findByTitle(String title);

    Map<Integer, Board> findByCond(SearchCond searchCond);

    void update(Integer id, BoardDto boardDto);

    void delete(Integer id);
}
