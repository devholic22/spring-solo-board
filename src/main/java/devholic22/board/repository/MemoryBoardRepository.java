package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchDto;

import java.util.HashMap;
import java.util.Map;

public class MemoryBoardRepository implements BoardRepository {

    private static final Map<Long, Board> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public void save(Board board) {
        board.setId(++sequence);
        board.setWriter("anon");
        store.put(board.getId(), board);
    }

    @Override
    public Board findById(Long id) {
        return store.get(id);
    }

    @Override
    public Map<Long, Board> findAll() {
        return store;
    }

    @Override
    public Map<Long, Board> findByTitle(SearchDto searchDto) {
        Map<Long, Board> temp = new HashMap<>();

        for (Board board : store.values()) {
            if (board.getTitle().contains(searchDto.getSearch())) {
                temp.put(board.getId(), board);
            }
        }

        return temp;
    }

    @Override
    public void update(Long id, BoardDto boardDto) {
        Board findBoard = store.get(id);
        findBoard.setTitle(boardDto.getTitle());
        findBoard.setContent(boardDto.getContent());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }
}
