package ro.fasttrackit.fullstackhomework7.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.fullstackhomework7.domain.Restaurant;
import ro.fasttrackit.fullstackhomework7.model.RestaurantFilters;
import ro.fasttrackit.fullstackhomework7.service.CollectionResponse;
import ro.fasttrackit.fullstackhomework7.service.PageInfo;
import ro.fasttrackit.fullstackhomework7.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;


    @GetMapping
    CollectionResponse<Restaurant> getAll(RestaurantFilters restaurantFilters,
                                          Pageable pageable) {
        Page<Restaurant> restaurantPage = service.getAll(restaurantFilters, pageable);
        return CollectionResponse.<Restaurant>builder()
                .content(restaurantPage.getContent())
                .pageInfo(PageInfo.builder()
                        .totalPages(restaurantPage.getTotalPages())
                        .totalElements(restaurantPage.getNumberOfElements())
                        .currentPage(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .build()).build();
    }

    @PostMapping
    Restaurant addRestaurant(@RequestBody Restaurant newRestaurant) {
        return service.addRestaurant(newRestaurant);
    }

    @PutMapping("{restaurantId}")
    Restaurant replaceRestaurant(@RequestBody Restaurant newRestaurant, @PathVariable long restaurantId) {
        return service.replaceRestaurant(restaurantId, newRestaurant);
    }


    @PatchMapping("{restaurantId}")
    Restaurant patchRestaurant(@RequestBody JsonPatch patch, @PathVariable long restaurantId) {
        return service.patchRestaurant(restaurantId, patch);
    }

    @DeleteMapping("{restaurantId}")
    void deleteRestaurant(@PathVariable long restaurantId) {
        service.deleteRestaurant(restaurantId);
    }
}
