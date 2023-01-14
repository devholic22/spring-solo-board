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
    public List<Board> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public List<Board> findByTitle(String title) {
        List<Board> temp = new ArrayList<>();
        for (Board board : store.values()) {
            if (board.getTitle().contains(title)) {
                temp.add(board);
            }
        }
        return temp;
    }

    @Override
    public List<Board> findByCond(SearchCond searchCond) {
        List<Board> temp;
        List<Board> result = new ArrayList<>();

        String title = searchCond.getSearch() != null ? searchCond.getSearch() : "";

        temp = findByTitle(title);

        int page = searchCond.getPage() != null ? searchCond.getPage() : 1;
        int EACH_PAGE = 3;
        int start = page > 0 ? (page - 1) * EACH_PAGE : 0;
        int end = Math.min(start + EACH_PAGE, temp.size());

        for (int j = start; j < end; j++) {
            result.add(temp.get(j));
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
