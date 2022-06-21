package iducs.springboot.kchboard.domain;

import iducs.springboot.kchboard.entity.Album;
import iducs.springboot.kchboard.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlbumRequestDto {
	private Long id;
	private String title;

	private MemberEntity member;

	public Album toEntity() {
		return Album.builder()
			.title(title)
				.member(member)
			.build();
	}

	@Override
	public String toString() {
		return "BoardRequestDto{" +
				"id=" + id +
				", title='" + title + '\'' +
				'}';
	}
}