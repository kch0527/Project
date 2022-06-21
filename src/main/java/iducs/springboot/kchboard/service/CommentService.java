package iducs.springboot.kchboard.service;

import iducs.springboot.kchboard.entity.Comment;
import iducs.springboot.kchboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment addComment(Comment comment){
        commentRepository.save(comment);
        return comment;
    }

    public String nowTime(){
        LocalDateTime nowTime = LocalDateTime.now();
        String format = nowTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return format;
    }

    public Comment findComment(Long id){
        Comment findComment = commentRepository.getById(id);
        return findComment;
    }

    @Transactional
    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }
}
