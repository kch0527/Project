package iducs.springboot.kchboard.repository;


import iducs.springboot.kchboard.entity.AlbumImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumImageRepository extends JpaRepository<AlbumImage, Long> {
	
	static final String SELECT_IMAGE_ID= "SELECT ID FROM album_image201812043 "
			+ "WHERE ALBUM_ID = :albumId AND DELETE_YN != 'Y'";
	
	static final String UPDATE_DELETE_YN= "UPDATE album_image201812043 "
			+ "SET DELETE_YN = 'Y' "
			+ "WHERE ID IN (:deleteIdList)";
	
	static final String DELETE_ALBUM_IMAGE_YN= "UPDATE album_image201812043 "
			+ "SET DELETE_YN = 'Y' "
			+ "WHERE ALBUM_ID IN (:albumIdList)";
	
	@Query(value = SELECT_IMAGE_ID, nativeQuery = true)
	public List<Long> findByAlbumId(@Param("albumId") Long albumId);
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_DELETE_YN, nativeQuery = true)
	public int updateDeleteYn(@Param("deleteIdList") Long[] deleteIdList);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_ALBUM_IMAGE_YN, nativeQuery = true)
	public int deleteAlbumFileYn(@Param("albumIdList") Long[] albumIdList);
}
