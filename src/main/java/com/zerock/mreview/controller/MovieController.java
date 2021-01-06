package com.zerock.mreview.controller;

import com.zerock.mreview.dto.MovieDTO;
import com.zerock.mreview.dto.PageRequestDTO;
import com.zerock.mreview.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {

        log.info("movieDTO : " + movieDTO);

        Long mno = movieService.register(movieDTO);

        redirectAttributes.addFlashAttribute("msg", mno);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("pageRequestDTO : " + pageRequestDTO);

        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("mno : " + mno);

        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, MovieDTO movieDTO, RedirectAttributes redirectAttributes) {

        movieService.remove(movieDTO);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());

        return "redirect:/movie/list";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, MovieDTO movieDTO, RedirectAttributes redirectAttributes) {

        movieService.modify(movieDTO);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("mno", movieDTO.getMno());

        return "redirect:/movie/read";
    }
}
