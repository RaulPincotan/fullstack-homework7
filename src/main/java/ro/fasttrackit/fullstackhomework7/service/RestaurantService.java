package ro.fasttrackit.fullstackhomework7.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ro.fasttrackit.fullstackhomework7.domain.Restaurant;
import ro.fasttrackit.fullstackhomework7.exceptions.ResourceNotFoundException;
import ro.fasttrackit.fullstackhomework7.model.RestaurantFilters;
import ro.fasttrackit.fullstackhomework7.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;
    private final RestaurantValidator validator;
    private final ObjectMapper mapper;


    public Page<Restaurant> getAll(RestaurantFilters filters, Pageable pageable) {
        if (!CollectionUtils.isEmpty(filters.getCities())) {
            return repository.findByNameIn(filters.getCities(), pageable);
        }
        return repository.findAll(pageable);
    }

    public Restaurant addRestaurant(Restaurant newRestaurant) {
        validator.validaateNewThrow(newRestaurant);
        return repository.save(newRestaurant);
    }

    public Restaurant replaceRestaurant(long restaurantId, Restaurant newRestaurant) {
        newRestaurant.setId(restaurantId);
        validator.validateReplaceThrow(restaurantId, newRestaurant);

        Restaurant restaurantDB = repository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find restaurant with id: " + restaurantId));
        copyRestaurant(newRestaurant, restaurantDB);
        return repository.save(newRestaurant);
    }

    private void copyRestaurant(Restaurant newRestaurant, Restaurant restaurantDB) {
        newRestaurant.setName(restaurantDB.getName());
        newRestaurant.setCity(restaurantDB.getCity());
        newRestaurant.setSince(restaurantDB.getSince());
    }

    @SneakyThrows
    public Restaurant patchRestaurant(long restaurantId, JsonPatch jsonPatch) {

        validator.validateExistsOrThrow(restaurantId);
        Restaurant restaurantDB = repository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find restaurant with id: " + restaurantId));
        JsonNode patchProductJson = jsonPatch.apply(mapper.valueToTree(restaurantDB));
        Restaurant patchedRestaurant = mapper.treeToValue(patchProductJson, Restaurant.class);
        return replaceRestaurant(restaurantId, patchedRestaurant);
    }


    public void deleteRestaurant(long restaurantId) {
        validator.validateExistsOrThrow(restaurantId);
        repository.deleteById(restaurantId);
    }

}
