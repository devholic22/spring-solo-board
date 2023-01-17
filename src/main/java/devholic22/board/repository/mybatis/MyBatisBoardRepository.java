package devholic22.board.repository.mybatis;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBatisBoardRepository implements BoardRepository {

    private final BoardMapper boardMapper;

    @Override
    public void save(Board board) {
        boardMapper.save(board);
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
    public void update(Integer id, BoardDto boardDto) {
        boardMapper.update(id, boardDto);
    }

    @Override
    public void delete(Integer id) {
        boardMapper.delete(id);
    }

    @Override
    public void clearStore() {

    }
}
