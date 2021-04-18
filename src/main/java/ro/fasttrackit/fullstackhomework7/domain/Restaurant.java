package ro.fasttrackit.fullstackhomework7.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int stars;
    private String city;
    private String country;
    private LocalDate since;

    public Restaurant(String name, int stars, String city, String country, int year, int month, int day) {
        this.name = name;
        this.stars = stars;
        this.city = city;
        this.since = LocalDate.of(year, month, day);
    }
}
