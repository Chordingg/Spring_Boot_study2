package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.Board;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title.." + i)
                    .content("content.." + i)
                    .writer("user" + (i%10))
                    .build();
            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());
        });
    }

    @Test
    public void testInsertAll(){
        for(int i=1; i<=100; i++){

            Board board = Board.builder()
                    .title("Title.." + i)
                    .content("Content.." + i)
                    .writer("writer.." + i)
                    .build();

            for(int j=0; j<3; j++){
                if(i % 5 == 0){
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(), i+"file" + j + ".jpg");
            }
            boardRepository.save(board);
        }  // end for
    }

    @Transactional
    @Test
    public void testSearchReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        boardRepository.searchWithAll(null,null,pageable);
    }

}