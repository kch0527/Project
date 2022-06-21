package iducs.springboot.kchboard.repository;


import iducs.springboot.kchboard.domain.AlbumRequestDto;
import iducs.springboot.kchboard.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

	static final String UPDATE_ALBUM_VIEWS = "UPDATE Album201812043 "
			+ "SET VIEWS = VIEWS + 1 "
			+ "WHERE ID = :id";
	
	static final String DELETE_ALBUM = "DELETE FROM Album201812043 "
			+ "WHERE ID IN (:deleteIdList)";
	

	
	@Transactional
	@Modifying
	@Query(value = UPDATE_ALBUM_VIEWS, nativeQuery = true)
	public int updateAlbumReadCntInc(@Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_ALBUM, nativeQuery = true)
	public int deleteAlbum(@Param("deleteIdList") Long[] deleteIdList);
}
