package ro.fasttrackit.fullstackhomework7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.fullstackhomework7.domain.Restaurant;
import ro.fasttrackit.fullstackhomework7.exceptions.ValidatorException;
import ro.fasttrackit.fullstackhomework7.repository.RestaurantRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestaurantValidator {
    private final RestaurantRepository repository;

    public void validaateNewThrow(Restaurant newRestaurant) {
        validate(newRestaurant, true)
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    public void validateReplaceThrow(long restaurantId, Restaurant newRestaurant) {
        exists(restaurantId)
                .or(() -> validate(newRestaurant, false))
                .ifPresent(ex -> {
                    throw ex;
                });
    }


    private Optional<ValidatorException> validate(Restaurant restaurant, boolean newEntity) {
        if (restaurant.getName() == null) {
            return Optional.of(new ValidatorException("Name cannot be null"));
        } else if (newEntity && repository.findByName(restaurant.getName())) {
            return Optional.of(new ValidatorException("Name cannot be duplicate"));
        } else if (!newEntity && repository.existsByNameAndIdNot(restaurant.getName(), restaurant.getId())) {
            return Optional.of(new ValidatorException("Name cannot be duplicate"));
        } else {
            return Optional.empty();
        }

    }

    private Optional<ValidatorException> exists(long restaurantId) {
        return repository.existsById(restaurantId)
                ? Optional.empty() : Optional.of(new ValidatorException("Restaurant with id " + restaurantId + " does not exist"));
    }

    public void validateExistsOrThrow(long restaurantId) {
        exists(restaurantId).ifPresent(ex -> {
            throw ex;
        });
    }
}
