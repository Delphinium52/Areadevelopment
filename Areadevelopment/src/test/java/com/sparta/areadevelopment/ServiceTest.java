package com.sparta.areadevelopment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sparta.areadevelopment.dto.BoardRequestDto;
import com.sparta.areadevelopment.dto.BoardResponseDto;
import com.sparta.areadevelopment.entity.Board;
import com.sparta.areadevelopment.entity.User;
import com.sparta.areadevelopment.repository.BoardRepository;
import com.sparta.areadevelopment.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @InjectMocks
    private BoardService boardService;
    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("createBoard Test")
    void testCreateBoard() {
        //given

        User user  =new User();

        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setTitle("some title");
        boardRequestDto.setContent("some content");

        when(boardRepository.save(any())).thenReturn(new Board(user,boardRequestDto));
        //when
        BoardResponseDto boardResponseDto = boardService.createBoard(user, boardRequestDto);

        //then
        assertEquals(boardRequestDto.getTitle(), boardResponseDto.getTitle());
        assertEquals(boardRequestDto.getContent(), boardResponseDto.getContent());
    }
}
