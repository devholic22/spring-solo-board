package devholic22.board.repository.mybatis;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class MyBatisBoardRepository implements BoardRepository {

    private final BoardMapper boardMapper;

    @Override
    public Board save(Board board) {
        boardMapper.save(board);
        return board;
    }

    @Override
    public Board findById(Integer id) {
        return boardMapper.findById(id);
    }

    @Override
    public List<Board> findAll() {
        return boardMapper.findAll();
    }

    @Override
    public List<Board> findByTitle(String title) {
        return boardMapper.findByTitle(title);
    }

    @Override
    public List<Board> findByCond(SearchCond searchCond) {
        return boardMapper.findByCond(searchCond);
    }

    @Override
    public void update(Integer id, BoardDto boardDto) throws NullPointerException {
        if (boardMapper.findById(id) == null) {
            throw new NullPointerException();
        }
        boardMapper.update(id, boardDto);
    }

    @Override
    public void delete(Integer id) throws NoSuchElementException {
        if (boardMapper.findById(id) == null) {
            throw new NoSuchElementException();
        }
        boardMapper.delete(id);
    }

    @Override
    public void clearStore() {

    }
}
