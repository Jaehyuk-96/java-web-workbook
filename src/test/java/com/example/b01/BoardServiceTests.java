package com.example.b01;


import com.example.b01.dto.BoardDTO;
import com.example.b01.dto.PageRequestDTO;
import com.example.b01.dto.PageResponseDTO;
import com.example.b01.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());
        //실제 boardservcie 변수가 가르키는 객체의 클래스명을 출력하는데 실행해 보면 boardserviceimpl이 나오지 않고 스프링에서
        //스프링에서 boardserviceimpl을 감싸서 만든 클래스 정보가 출력됨

        BoardDTO boardDTO = BoardDTO.builder()
                .title("sample title.....")
                .content("sample Content.....")
                .writer("user00")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno:" + bno);
    }


    @Test
    public void testModify() {

        //변경에 필요한 데이터만
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(99L)
                .title("updated 99...")
                .content("updated content 99")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
    }

}
