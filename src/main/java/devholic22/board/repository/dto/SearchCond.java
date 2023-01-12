package devholic22.board.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCond {

    private String search;
    private Integer page;

    public SearchCond() {
    }

    public SearchCond(String search, int page) {
        this.search = search;
        this.page = page;
    }
}
