package devholic22.board;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestInit {

    private final BoardRepository boardRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("test data init");
        boardRepository.save(new Board("anon", "test board 1", "this is test 1"));
        boardRepository.save(new Board("anon", "test board 2", "this is test 2"));
        boardRepository.save(new Board("anon", "test board 3", "this is test 3"));
        boardRepository.save(new Board("anon", "test board 4", "this is test 4"));
    }
}
