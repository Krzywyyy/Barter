package pl.krzywyyy.barter.products;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class ProductSearchFilters {
    private String category;
    private String specialization;
    private Float latitude;
    private Float longitude;
    private Integer distance;
}
