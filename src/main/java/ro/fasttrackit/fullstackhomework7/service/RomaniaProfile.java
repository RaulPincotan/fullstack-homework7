package ro.fasttrackit.fullstackhomework7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ro.fasttrackit.fullstackhomework7.domain.Restaurant;
import ro.fasttrackit.fullstackhomework7.repository.RestaurantRepository;

import java.util.List;

@Profile("romania")
@Component
@RequiredArgsConstructor
public class RomaniaProfile {
    private final RestaurantRepository repository;

    public List<Restaurant> getRestaurantsFromRomania() {
        return repository.findByCountry("Romania");
    }
}
