package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchDto;

import java.util.Map;

public interface BoardRepository {

    void save(Board board);
    Board findById(Long id);

    Map<Long, Board> findAll(Integer page);

    Map<Long, Board> findByTitle(Integer page, SearchDto searchDto);

    void update(Long id, BoardDto boardDto);
    void delete(Long id);
}
