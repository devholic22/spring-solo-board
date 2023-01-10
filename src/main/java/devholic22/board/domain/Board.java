package devholic22.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Board {

    private Long id;

    private String writer;
    private String title;
    private String content;
    private String createdAt;

    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = timeFormat();
    }

    public String timeFormat() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
