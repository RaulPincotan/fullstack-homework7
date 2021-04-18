package ro.fasttrackit.fullstackhomework7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.fullstackhomework7.domain.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean findByName(String name);

    Page<Restaurant> findByNameIn(List<String> name, Pageable pageable);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Restaurant> findByCountry(String romania);
}
