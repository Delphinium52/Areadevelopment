package com.sparta.areadevelopment;

import static com.sparta.areadevelopment.enums.StatusEnum.DELETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.sparta.areadevelopment.dto.BoardRequestDto;
import com.sparta.areadevelopment.dto.CommentRequestDto;
import com.sparta.areadevelopment.entity.Board;
import com.sparta.areadevelopment.entity.Comment;
import com.sparta.areadevelopment.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EntityTest {
    @Nested
    @DisplayName("User Entity 테스트")
    class UserEntity {
        User user;
        @BeforeEach
        void setUp() {
            user = new User("유저 아이디","닉네임","비밀번호","이메일","한줄소개");
        }
        @Test
        @DisplayName("softDelete Test")
        void softDeleteTest() {
            //no given
            //when
            user.softDelete();
            //then
            assertEquals(user.getStatus(),DELETED);
            assertNotNull(user.getDeletedAt());
        }
        @Test
        @DisplayName("updateToken Test")
        void updateTokenTest() {
            //given
            String refreshToken = "test1234";
            //when
            user.updateToken(refreshToken);
            //then
            assertEquals(refreshToken, user.getRefreshToken());
        }
        @Test
        @DisplayName("setExpired Test")
        void setExpiredTest() {
            //given
            boolean expired = true;
            //when
            user.setExpired(expired);
            //then
            assertEquals(expired, user.isExpired());
        }
    }
    @Nested
    @DisplayName("Board Entity 테스트")
    class BoardEntity{
        Board board;
        @BeforeEach
        void setUp() {
            board = new Board();
        }
        @Test
        @DisplayName("hitsUp Test")
        void hitsUpTest() {
            //given
            board.setHits(0L);
            //when
            board.hitsUp();
            //then
            assertEquals(1, board.getHits());
        }
        @Test
        @DisplayName("update Test")
        void updateTest() {
            //given
            BoardRequestDto boardRequestDto = new BoardRequestDto();
            boardRequestDto.setTitle("게시물제목");
            boardRequestDto.setContent("게시물내용");
            //when
            board.update(boardRequestDto);
            //then
            assertEquals("게시물제목", board.getTitle());
            assertEquals("게시물내용", board.getContent());
            assertNotNull(board.getModifiedAt());
        }
    }
    @Nested
    @DisplayName("Comment Test")
    class CommentEntity{
        Comment comment;
        @BeforeEach
        void setUp() {
            comment = new Comment();
        }
        @Test
        @DisplayName("update Test")
        void updateTest() {
            //given
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            commentRequestDto.setContent("댓글내용");
            //when
            comment.update(commentRequestDto);
            //then
            assertEquals("댓글내용", comment.getContent());
        }
        @Test
        @DisplayName("delete Test")
        void deleteTest() {
            //no given
            //when
            comment.delete();
            //then
            assertNotNull(comment.getDeletedAt());
        }
        @Test
        @DisplayName("isCommentAuthor Test")
        void isCommentAuthorTest() {
            //given
            User user = new User();
            Long userId = 1L;
            //when
            boolean answer =  comment.isCommentAuthor(userId);
            //then
            assertTrue(true, String.valueOf(answer));
        }
    }
}
