package devholic22.board.controller;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.SearchDto;
import devholic22.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final BoardService boardService;

    // 검색 조회 (필터링)
    @GetMapping()
    public String Search(@RequestParam(required = false) String search, @RequestParam(defaultValue = "1") Integer page, @ModelAttribute SearchDto searchDto, Model model) {

        Map<Long, Board> allBoard = null;

        if (search != null && searchDto != null) {
            allBoard = boardService.search(page, searchDto);
        } else {
            allBoard = boardService.findAll(page);
        }

        Collection<Board> boardValues = allBoard == null ? null : allBoard.values();
        List<Board> boards = allBoard == null ? null : new ArrayList<>(boardValues);

        model.addAttribute("boards", boards);
        model.addAttribute("page", page);

        return "home";
    }
}
