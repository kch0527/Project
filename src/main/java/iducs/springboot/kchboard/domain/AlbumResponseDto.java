package iducs.springboot.kchboard.domain;

import iducs.springboot.kchboard.entity.Album;
import iducs.springboot.kchboard.entity.Comment;
import iducs.springboot.kchboard.entity.MemberEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class AlbumResponseDto {
	private Long id;
	private String title;
	private LocalDateTime registerTime;
	private MemberEntity member;
	private int views;
	private Comment comment;
	
	public AlbumResponseDto(Album entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.registerTime = entity.getRegisterTime();
		this.views = entity.getViews();
		this.member = entity.getMember();
	}

	@Override
	public String toString() {
		return "AlbumResponseDto{" +
				"id=" + id +
				", title='" + title + '\'' +
				", registerTime=" + registerTime +
				", views=" + views +
				'}';
	}

	public String getRegisterTime() {
		return toStringDateTime(this.registerTime);
	}
	
    public static String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}