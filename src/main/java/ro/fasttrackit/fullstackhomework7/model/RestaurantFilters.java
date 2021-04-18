package ro.fasttrackit.fullstackhomework7.model;

import lombok.Value;

import java.util.List;

@Value
public class RestaurantFilters {
    List<Integer> stars;
    List<String> cities;
}
