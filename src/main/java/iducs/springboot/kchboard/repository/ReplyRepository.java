package iducs.springboot.kchboard.repository;

import iducs.springboot.kchboard.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
}
