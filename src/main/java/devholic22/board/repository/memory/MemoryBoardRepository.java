package devholic22.board.repository.memory;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;

import java.util.*;

public class MemoryBoardRepository implements BoardRepository {

    private static final Map<Integer, Board> store = new HashMap<>();
    private Integer sequence = 0;

    @Override
    public void save(Board board) {
        board.setId(++sequence);
        board.setWriter("anon");
        store.put(board.getId(), board);
    }

    @Override
    public Board findById(Integer id) {
        return store.get(id);
    }

    @Override
    public Map<Integer, Board> findAll() {
        return store;
    }

    @Override
    public Map<Integer, Board> findByTitle(String title) {
        Map<Integer, Board> temp = new HashMap<>();
        int pos = 0;
        for (Board board : store.values()) {
            if (board.getTitle().contains(title)) {
                temp.put(pos++, board);
            }
        }
        return temp;
    }

    @Override
    public Map<Integer, Board> findByCond(SearchCond searchCond) {
        Map<Integer, Board> temp;
        Map<Integer, Board> result = new HashMap<>();

        String title = searchCond.getSearch() != null ? searchCond.getSearch() : "";

        temp = findByTitle(title);

        int page = searchCond.getPage() != null ? searchCond.getPage() : 1;
        int EACH_PAGE = 3;
        int start = page > 0 ? (page - 1) * EACH_PAGE : 0;
        int end = Math.min(start + EACH_PAGE, temp.size());

        for (int j = start; j < end; j++) {
            result.put(j, temp.get(j));
        }

        return result;
    }

    @Override
    public void update(Integer id, BoardDto boardDto) {
        Board findBoard = store.get(id);
        findBoard.setTitle(boardDto.getTitle());
        findBoard.setContent(boardDto.getContent());
    }

    @Override
    public void delete(Integer id) throws NoSuchElementException {
        if (!store.containsKey(id)) {
            throw new NoSuchElementException();
        }
        store.remove(id);
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}
