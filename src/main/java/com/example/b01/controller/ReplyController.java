package com.example.b01.controller;

import com.example.b01.dto.PageRequestDTO;
import com.example.b01.dto.PageResponseDTO;
import com.example.b01.dto.ReplyDTO;
import com.example.b01.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Log4j2
public class ReplyController {

    private final ReplyService replyServcie;

    @ApiOperation(value = "Replies POST", notes = "Post 방식으로 댓글 등록")
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> register (@RequestBody ReplyDTO replyDTO, BindingResult bindingResult) throws
            BindException {
        log.info(replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap =  new HashMap<>();

        Long rno = replyServcie.register(replyDTO);

        resultMap.put("rno",rno);

        return resultMap;
    }

    @ApiOperation(value = "Replies POST", notes = "Post 방식으로 댓글 등록")
    @GetMapping(value="/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
                                             PageRequestDTO pageRequestDTO) {
        PageResponseDTO<ReplyDTO> responseDTO = replyServcie.getListOfBoard(bno, pageRequestDTO);

        return responseDTO;
    }

    @ApiOperation(value = "Read Reply", notes = "Get 방식으로 댓글 조회")
    @GetMapping(value="/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){

        ReplyDTO replyDTO = replyServcie.read(rno);

        return replyDTO;
    }

    @ApiOperation(value = "Delete Reply", notes = "Delete 방식으로 댓글 삭제")
    @DeleteMapping(value="/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno){

        replyServcie.remove(rno);

        Map<String,Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);
        return resultMap;
    }


    @ApiOperation(value = "Modify Reply", notes = "Put 방식으로 댓글 수정")
    @PutMapping(value="/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO){

        replyDTO.setRno(rno);

        replyServcie.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("rno", rno);

        return resultMap;
    }





}
