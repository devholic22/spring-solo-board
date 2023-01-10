package devholic22.board.service;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(Board board) {
        boardRepository.save(board);
    }

    public Board findOne(Long id) {
        return boardRepository.findById(id);
    }

    public Map<Long, Board> findAll() {
        return boardRepository.findAll();
    }

    public Map<Long, Board> search(SearchDto searchDto) {
        return boardRepository.findByTitle(searchDto);
    }

    public void fix(Long id, BoardDto boardDto) {
        boardRepository.update(id, boardDto);
    }

    public void remove(Long id) {
        boardRepository.delete(id);
    }
}
