package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchDto;

import java.util.*;

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
    public Map<Long, Board> findAll(Integer page) {
        int max = store.size();
        int start = (page - 1) * 3;
        if (start < 0) {
            return null;
        }
        int end = start + 2;

        Collection<Board> collection = store.values();
        List<Board> mapToArray = new ArrayList<>(collection);

        if (start >= max) {
            return null;
        } else {
            Map<Long, Board> result = new HashMap<>();
            if (end >= max) {
                for (int i = start; i < max; i++) {
                    result.put(mapToArray.get(i).getId(), mapToArray.get(i));
                }
            } else {
                for (int i = start; i < end; i++) {
                    result.put(mapToArray.get(i).getId(), mapToArray.get(i));
                }
            }
            return result;
        }
    }

    @Override
    public Map<Long, Board> findByTitle(Integer page, SearchDto searchDto) {
        Map<Long, Board> temp = new HashMap<>();

        for (Board board : store.values()) {
            if (board.getTitle().contains(searchDto.getSearch())) {
                temp.put(board.getId(), board);
            }
        }

        int max = temp.size();
        int start = (page - 1) * 3;
        if (start < 0) {
            return null;
        }
        int end = start + 2;

        Collection<Board> collection = store.values();
        List<Board> mapToArray = new ArrayList<>(collection);

        if (start >= max) {
            return null;
        } else {
            Map<Long, Board> result = new HashMap<>();
            if (end >= max) {
                for (int i = start; i < max; i++) {
                    result.put(mapToArray.get(i).getId(), mapToArray.get(i));
                }
            } else {
                for (int i = start; i < end; i++) {
                    result.put(mapToArray.get(i).getId(), mapToArray.get(i));
                }
            }
            return result;
        }
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
