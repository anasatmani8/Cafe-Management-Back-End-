package atmani.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import atmani.model.Image;
@Repository
public interface ImageDao extends JpaRepository<Image, Integer> {

}
