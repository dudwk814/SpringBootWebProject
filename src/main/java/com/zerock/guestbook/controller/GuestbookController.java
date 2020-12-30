package com.zerock.guestbook.controller;

import com.zerock.guestbook.dto.GuestbookDTO;
import com.zerock.guestbook.dto.PageRequestDTO;
import com.zerock.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String list() {

        log.info("list....");

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list...." + pageRequestDTO);

        model.addAttribute("result", guestbookService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("gno : " + gno);

        log.info("page : " + requestDTO.getPage());

        GuestbookDTO dto = guestbookService.read(gno);

        model.addAttribute("dto", dto);
    }

    @GetMapping("/register")
    public void register() {
        log.info("register..");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes rttr) {

        log.info("dto... : " + dto);

        Long gno = guestbookService.register(dto);

        rttr.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/remove")
    public String remove(Long gno, RedirectAttributes rttr) {

        log.info("gno : " + gno);

        guestbookService.remove(gno);

        rttr.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {

        log.info("post modify");
        log.info("dto : " + dto);

        guestbookService.modify(dto);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
        redirectAttributes.addAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read?gno=" + dto.getGno();
    }
}
