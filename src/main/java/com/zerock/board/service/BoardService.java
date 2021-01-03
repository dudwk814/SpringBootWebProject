package com.zerock.board.service;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDTO;
import com.zerock.board.dto.PageResultDTO;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO); // 목록 처리

    BoardDTO get(Long bno); // 게시글 단일 조회

    void removeWithReplies(Long bno);   // 게시글 삭제

    void modify(BoardDTO boardDTO); // 게시글 수정

    default Board dtoToEntity(BoardDTO dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).name(dto.getWriterName()).build();

        Board board = Board.builder()
                .writer(member)
                .content(dto.getContent())
                .title(dto.getTitle())
                .bno(dto.getBno())
                .build();

        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerName(member.getName())
                .writerEmail(member.getEmail())
                .replyCount(replyCount.intValue()) // int 형으로 캐스팅
                .build();

        return boardDTO;
    }
}
