package iducs.springboot.kchboard.repository;

import iducs.springboot.kchboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
