package pl.krzywyyy.barter.products;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class ProductSearchFilters {
    private String category;
    private String specialization;
    private Float latitude;
    private Float longitude;
    private Integer distance;
    private String searchText;
}
