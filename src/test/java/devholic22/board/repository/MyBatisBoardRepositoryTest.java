package devholic22.board.repository;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import devholic22.board.repository.mybatis.BoardMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MyBatisBoardRepositoryTest {

    private final BoardRepository boardRepository;

    @Autowired
    public MyBatisBoardRepositoryTest(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Test
    @DisplayName("글 저장 테스트")
    void save() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");
        Board board2 = new Board("익명", "두 번째 글", "두 번째 글입니다.");
        Board board3 = new Board("익명", "세 번째 글", "세 번째 글입니다.");

        // when
        Board savedBoard1 = boardRepository.save(board1);
        Board savedBoard2 = boardRepository.save(board2);
        Board savedBoard3 = boardRepository.save(board3);

        List<Board> result = boardRepository.findAll();
        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.contains(savedBoard1)).isTrue();
        assertThat(result.contains(savedBoard2)).isTrue();
        assertThat(result.contains(savedBoard3)).isTrue();
    }

    @Test
    @DisplayName("글 id 조회 테스트 O")
    void exist_board_search() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        Board savedBoard1 = boardRepository.save(board1);
        Board findBoard = boardRepository.findById(board1.getId());

        // then
        assertThat(findBoard).isEqualTo(savedBoard1);
    }

    @Test
    @DisplayName("글 id 조회 테스트 X")
    void no_exist_board_search() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        Board savedBoard1 = boardRepository.save(board1);

        // then
        assertThat(boardRepository.findById(2)).isNull();
    }

    @Test
    @DisplayName("글 수정 테스트 O")
    void exist_board_edit() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        Board savedBoard1 = boardRepository.save(board1);

        System.out.println(savedBoard1.getId());
        String newTitle = "첫 번째 글 (수정)";
        String newContent = "첫 번째 글 (수정) 입니다.";
        BoardDto boardDto = new BoardDto(newTitle, newContent);

        boardRepository.update(savedBoard1.getId(), boardDto);

        Board updateBoard = boardRepository.findById(savedBoard1.getId());

        // then
        assertThat(updateBoard.getTitle()).isEqualTo(newTitle);
        assertThat(updateBoard.getContent()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("글 수정 테스트 X")
    void no_exist_board_edit() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        Board savedBoard1 = boardRepository.save(board1);

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
        Board savedBoard1 = boardRepository.save(board1);
        Board savedBoard2 = boardRepository.save(board2);
        boardRepository.delete(savedBoard1.getId());

        List<Board> result = boardRepository.findAll();

        // then
        assertThat(result).doesNotContain(savedBoard1);
        assertThat(result).size().isEqualTo(1);
    }

    @Test
    @DisplayName("글 삭제 테스트 X")
    void no_exist_board_delete() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");

        // when
        Board savedBoard1 = boardRepository.save(board1);

        // then
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> boardRepository.delete(200000));
    }

    @Test
    @DisplayName("제목으로만 검색")
    void find_by_title() {
        // given
        Board board1 = new Board("익명", "첫 번째 글", "첫 번째 글입니다.");
        Board board2 = new Board("익명", "두 번째 글", "첫 번째 글입니다.");
        Board board3 = new Board("익명", "아무거나", "세 번째 글입니다.");

        // when
        Board savedBoard1 = boardRepository.save(board1);
        Board savedBoard2 = boardRepository.save(board2);
        Board savedBoard3 = boardRepository.save(board3);

        List<Board> boards = boardRepository.findByTitle("글");

        // then
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards.contains(savedBoard1)).isTrue();
        assertThat(boards.contains(savedBoard2)).isTrue();
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
        Board savedBoard1 = boardRepository.save(board1);
        Board savedBoard2 = boardRepository.save(board2);
        Board savedBoard3 = boardRepository.save(board3);
        Board savedBoard4 = boardRepository.save(board4);

        SearchCond searchCond = new SearchCond("", 2);
        List<Board> boards = boardRepository.findByCond(searchCond);

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.contains(savedBoard4)).isTrue();
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
        Board savedBoard1 = boardRepository.save(board1);
        Board savedBoard2 = boardRepository.save(board2);
        Board savedBoard3 = boardRepository.save(board3);
        Board savedBoard4 = boardRepository.save(board4);
        Board savedBoard5 = boardRepository.save(board5);
        Board savedBoard6 = boardRepository.save(board6);
        Board savedBoard7 = boardRepository.save(board7);
        Board savedBoard8 = boardRepository.save(board8);
        Board savedBoard9 = boardRepository.save(board9);

        SearchCond searchCond = new SearchCond("eng", 2);

        List<Board> boards = boardRepository.findByCond(searchCond);

        // then
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards.contains(savedBoard8)).isTrue();
        assertThat(boards.contains(savedBoard9)).isTrue();
    }
}
