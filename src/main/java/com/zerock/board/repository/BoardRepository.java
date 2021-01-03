package com.zerock.board.repository;

import com.zerock.board.entity.Board;
import com.zerock.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(Long bno);

    @Query("select b, r from Board b left join Reply  r ON r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "SELECT b, w, count(r) " +
                    " FROM Board b " +
                    " LEFT JOIN b.writer w " +
                    " LEFT JOIN Reply r ON r.board = b " +
                    " GROUP BY b",
    countQuery = "SELECT COUNT (b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);   // 목록 화면에 필요한 데이터

    @Query("SELECT b, w, count (r) " +
            " FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b " +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

}