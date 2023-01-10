package devholic22.board.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {

    private String search;

    public SearchDto() {
    }

    public SearchDto(String search) {
        this.search = search;
    }
}
