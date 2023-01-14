package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import devholic22.board.repository.memory.MemoryBoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class MemoryBoardRepositoryTest {

    public BoardRepository boardRepository = new MemoryBoardRepository();

    @BeforeEach
    void before() {
        boardRepository.clearStore();
    }

    @Test
    @DisplayName("글 저장 테스트")
    void save() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");
        Board board2 = new Board("익명", "두 번째 글", "두 번째 글입니다.");
        Board board3 = new Board("익명", "세 번째 글", "세 번째 글입니다.");

        // when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);

        // then
        assertThat(boardRepository.findAll().size()).isEqualTo(3);
        assertThat(boardRepository.findAll().containsValue(board1)).isTrue();
        assertThat(boardRepository.findAll().containsValue(board2)).isTrue();
        assertThat(boardRepository.findAll().containsValue(board3)).isTrue();
    }

    @Test
    @DisplayName("글 id 조회 테스트 O")
    void exist_board_search() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        boardRepository.save(board1);

        Board findBoard = boardRepository.findById(board1.getId());

        // then
        assertThat(findBoard).isEqualTo(board1);
    }

    @Test
    @DisplayName("글 id 조회 테스트 X")
    void no_exist_board_search() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        boardRepository.save(board1);

        // then
        assertThat(boardRepository.findById(2)).isNull();
    }

    @Test
    @DisplayName("글 수정 테스트 O")
    void exist_board_edit() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        boardRepository.save(board1);

        String newTitle = "첫 번째 글 (수정)";
        String newContent = "첫 번째 글 (수정) 입니다.";
        BoardDto boardDto = new BoardDto(newTitle, newContent);

        boardRepository.update(board1.getId(), boardDto);

        Board updateBoard = boardRepository.findById(board1.getId());

        // then
        assertThat(board1.getTitle()).isEqualTo(newTitle);
        assertThat(board1.getContent()).isEqualTo(newContent);
        assertThat(board1).isEqualTo(updateBoard);
    }

    @Test
    @DisplayName("글 수정 테스트 X")
    void no_exist_board_edit() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        boardRepository.save(board1);

        String newTitle = "첫 번째 글 (수정)";
        String newContent = "첫 번째 글 (수정) 입니다.";
        BoardDto boardDto = new BoardDto(newTitle, newContent);

        // then
        // 조회 테스트 X는 가져오는 것만 실행하기 때문에 isNull로 테스트
        // 수정 테스트 X는 가져오는 것과 수정하는 것을 실행하기 때문에 assertThatNullPointerException으로 테스트
        assertThatNullPointerException().isThrownBy(() -> boardRepository.update(2, boardDto));
    }

    @Test
    @DisplayName("글 삭제 테스트 O")
    void exist_board_delete() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");
        Board board2 = new Board("익명", "두 번째 글", "두 번째 글입니다.");

        // when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.delete(board1.getId());

        // then
        assertThat(boardRepository.findAll()).doesNotContainValue(board1);
        assertThat(boardRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("글 삭제 테스트 X")
    void no_exist_board_delete() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        boardRepository.save(board1);

        // then
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> boardRepository.delete(2));
    }

    @Test
    @DisplayName("제목으로만 검색")
    void find_by_title() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");
        Board board2 = new Board("익명", "두 번째 글", "첫 번째 글입니다.");
        Board board3 = new Board("익명", "아무거나", "세 번째 글입니다.");

        // when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);

        Map<Integer, Board> boards = boardRepository.findByTitle("글");

        // then
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards.containsValue(board1)).isTrue();
        assertThat(boards.containsValue(board2)).isTrue();
    }

    @Test
    @DisplayName("페이지로만 검색")
    void find_by_page() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");
        Board board2 = new Board("익명", "두 번째 글", "첫 번째 글입니다.");
        Board board3 = new Board("익명", "세 번째 글", "세 번째 글입니다.");
        Board board4 = new Board("익명", "네 번째 글", "네 번째 글입니다.");

        // when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);

        SearchCond searchCond = new SearchCond("", 2);
        Map<Integer, Board> boards = boardRepository.findByCond(searchCond);

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.containsValue(board4)).isTrue();
    }

    @Test
    @DisplayName("제목과 페이지로 검색")
    void find_by_title_and_page() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");
        Board board2 = new Board("익명", "두 번째 글", "첫 번째 글입니다.");
        Board board3 = new Board("익명", "세 번째 글", "세 번째 글입니다.");
        Board board4 = new Board("익명", "네 번째 글", "네 번째 글입니다.");
        Board board5 = new Board("익명", "eng post 1", "this is english post 1");
        Board board6 = new Board("익명", "eng post 2", "this is english post 2");
        Board board7 = new Board("익명", "eng post 3", "this is english post 3");
        Board board8 = new Board("익명", "eng post 4", "this is english post 4");
        Board board9 = new Board("익명", "eng post 5", "this is english post 5");

        // when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);
        boardRepository.save(board5);
        boardRepository.save(board6);
        boardRepository.save(board7);
        boardRepository.save(board8);
        boardRepository.save(board9);

        SearchCond searchCond = new SearchCond("eng", 2);

        Map<Integer, Board> boards = boardRepository.findByCond(searchCond);

        // then
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards.containsValue(board8)).isTrue();
        assertThat(boards.containsValue(board9)).isTrue();
    }
}
