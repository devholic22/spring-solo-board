package devholic22.board.controller;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.SearchCond;
import devholic22.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final BoardService boardService;

    // 검색 조회 (필터링)
    @GetMapping()
    public String search (@ModelAttribute SearchCond searchCond, Model model) {

        Map<Integer, Board> allBoard;

        String search = searchCond.getSearch() != null ? searchCond.getSearch() : "";
        int page = searchCond.getPage() != null ? searchCond.getPage() : 1;
        int EACH = 4; // 한 페이지에 표현될 수 있는 글의 갯수 (리포지토리에서와 값이 같아야 한다.)
        int MAX_PAGE; // 최대 갈 수 있는 페이지
        int ALL_SIZE; // 필터링 된 전체 사이즈
        int PREV_BUTTONS = 1; // 현재 페이지 이전에 몇 개의 페이지네이션 버튼이 있을 수 있는지
        int NEXT_BUTTONS = 1; // 현재 페이지 다음에 몇 개의 페이지네이션 버튼이 있을 수 있는지

        allBoard = boardService.search(searchCond);
        ALL_SIZE = boardService.findByTitleCount(search);
        MAX_PAGE = ALL_SIZE % EACH == 0 ? ALL_SIZE / EACH : ALL_SIZE / EACH + 1;

        if (page <= 0 || page > MAX_PAGE) {
            return "redirect:/";
        }

        Collection<Board> boardValues = allBoard.values();
        List<Board> boards = new ArrayList<>(boardValues);

        if (boards.size() == 0) {
            return "redirect:/";
        }

        model.addAttribute("boards", boards);
        model.addAttribute("page", page);
        model.addAttribute("max_page", MAX_PAGE);
        model.addAttribute("prev_buttons", PREV_BUTTONS);
        model.addAttribute("next_buttons", NEXT_BUTTONS);

        return "home";
    }
}
