package iducs.springboot.kchboard.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "album_image201812043")
@Getter
@Entity
public class AlbumImage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long albumId;
	private String origFileName;
	private String saveFileName;
	private int fileSize;
	private String fileExt;
	private String filePath;
	private String deleteYn;
	

	@Builder
	public AlbumImage(Long id, Long albumId, String origFileName, String saveFileName, int fileSize, String fileExt,
                      String filePath, String deleteYn){
		this.id = id;
		this.albumId = albumId;
		this.origFileName = origFileName;
		this.saveFileName = saveFileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.filePath = filePath;
		this.deleteYn = deleteYn;
	}
}
