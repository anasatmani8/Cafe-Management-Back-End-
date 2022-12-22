package atmani.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import atmani.model.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
