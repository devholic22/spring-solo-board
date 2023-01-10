package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class MemoryBoardRepositoryTest {

    BoardRepository boardRepository = new MemoryBoardRepository();

    @Test
    @DisplayName("회원 저장 테스트 - 모든 값 조회 테스트도 포함됨")
    void save() {
        // given
        Board test1 = new Board("anon", "test1", "this is test 1");
        Board test2 = new Board("anon", "test2", "this is test 2");

        // when
        boardRepository.save(test1);
        boardRepository.save(test2);

        // then
        assertThat(boardRepository.findAll().size()).isEqualTo(2);
        assertThat(boardRepository.findAll().containsValue(test1)).isTrue();
        assertThat(boardRepository.findAll().containsValue(test2)).isTrue();
    }

    @Test
    @DisplayName("id로 조회")
    void findById() {
        // given
        Board board1 = new Board("anon", "test1", "this is test1");

        // when
        boardRepository.save(board1);
        Board findBoard = boardRepository.findById(board1.getId());

        // then
        assertThat(findBoard.getId()).isEqualTo(board1.getId());
        assertThat(findBoard).isSameAs(board1);
    }

    @Test
    @DisplayName("값 수정 테스트")
    void update() {
        // given
        Board board1 = new Board("anon", "test1", "this is test1");

        // when
        boardRepository.save(board1);
        boardRepository.update(board1.getId(), new BoardDto("test1", "this is modified test1"));

        // then
        assertThat(board1.getContent()).isEqualTo("this is modified test1");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void delete() {
        // given
        Board board1 = new Board("anon", "test1", "this is test1");
        Board board2 = new Board("anon", "test2", "this is test1");

        // when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.delete(board1.getId());

        // then
        assertThat(boardRepository.findById(board1.getId())).isNull();
        assertThat(boardRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("제목으로 검색")
    void findByTitle() {
        // given
        Board board1 = new Board("anon", "test1", "this is test1");
        Board board2 = new Board("anon", "test2", "this is test2");
        Board board3 = new Board("익명", "테스트1", "한국어 버전 테스트1");
        Board board4 = new Board("익명", "테스트2", "한국어 버전 테스트2");

        // when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);

        Map<Long, Board> filteredList = boardRepository.findByTitle(new SearchDto("test"));

        // then
        assertThat(filteredList.containsValue(board1)).isTrue();
        assertThat(filteredList.containsValue(board2)).isTrue();
    }
    
}
