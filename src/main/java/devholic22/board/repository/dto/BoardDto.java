package devholic22.board.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {

    private String title;
    private String content;

    public BoardDto() {
    }

    public BoardDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
