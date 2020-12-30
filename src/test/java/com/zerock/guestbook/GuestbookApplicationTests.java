package com.zerock.guestbook;

import com.zerock.guestbook.dto.GuestbookDTO;
import com.zerock.guestbook.dto.PageRequestDTO;
import com.zerock.guestbook.dto.PageResultDTO;
import com.zerock.guestbook.entity.Guestbook;
import com.zerock.guestbook.service.GuestbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GuestbookApplicationTests {

    @Autowired
    private GuestbookService guestbookService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSearch() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).type("tc").keyword("한글").build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());


        System.out.println("-----------------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("============================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}
