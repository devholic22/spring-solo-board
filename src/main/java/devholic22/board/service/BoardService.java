package devholic22.board.service;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(Board board) {
        boardRepository.save(board);
    }

    public Board findOne(Integer id) {
        return boardRepository.findById(id);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public int findByTitleCount(String title) {
        return boardRepository.findByTitle(title).size();
    }

    public List<Board> search(SearchCond searchCond) {
        return boardRepository.findByCond(searchCond);
    }

    public void fix(Integer id, BoardDto boardDto) {
        boardRepository.update(id, boardDto);
    }

    public void remove(Integer id) {
        boardRepository.delete(id);
    }
}
