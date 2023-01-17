package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;

import java.util.List;

public interface BoardRepository {

    Board save(Board board);

    Board findById(Integer id);

    List<Board> findAll();

    List<Board> findByTitle(String title);

    List<Board> findByCond(SearchCond searchCond);

    void update(Integer id, BoardDto boardDto);

    void delete(Integer id);

    void clearStore();
}
