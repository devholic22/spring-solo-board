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
        int EACH = 3;
        int MAX_PAGE;
        int ALL_SIZE;

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
        return "home";
    }
}
