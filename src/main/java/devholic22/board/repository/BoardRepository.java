package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    void save(Board board);

    Optional<Board> findById(Integer id);

    List<Board> findAll();

    List<Board> findByTitle(String title);

    List<Board> findByCond(SearchCond searchCond);

    void update(Integer id, BoardDto boardDto);

    void delete(Integer id);

    void clearStore();
}
