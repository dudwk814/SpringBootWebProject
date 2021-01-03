package com.zerock.board.service;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDTO;
import com.zerock.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService service;

    @Test
    public void testRegister() {

        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")  // 데이터베이스에 존재하는 회원 이메일을 사용해야 함
                .build();

        service.register(dto);
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = service.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {

        Long bno = 100L;

        BoardDTO boardDTO = service.get(bno);

        System.out.println(boardDTO);
    }

    @Test
    public void testRemove() {

        Long bno = 1L;

        service.removeWithReplies(bno);
    }

    @Test
    public void testModify() {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        service.modify(boardDTO);
    }
}
