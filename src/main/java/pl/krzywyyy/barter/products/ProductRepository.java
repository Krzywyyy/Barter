package pl.krzywyyy.barter.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.krzywyyy.barter.users.User;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByUser(User user);

    @Query(value = "select * from product p " +
            "where p.active = 1 " +
            "and p.category = (case when ?1 = '' then p.category else ?1 end) " +
            "and p.specialization = (case when ?2 = '' then p.specialization else ?2 end) " +
            "and (select ST_DISTANCE_SPHERE(point(p.latitude, p.longitude), point(?3, ?4))) / 1000 < ?5",
    nativeQuery = true)
    Page<Product> findAllFilteredProducts(
            String category,
            String specialization,
            float latitude,
            float longitude,
            int distance,
            Pageable pageable
            );
}
