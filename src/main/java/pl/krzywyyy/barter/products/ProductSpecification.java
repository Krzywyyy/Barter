package pl.krzywyyy.barter.products;

import com.google.common.base.Strings;
import org.springframework.data.jpa.domain.Specification;
import pl.krzywyyy.barter.utils.enums.ProductCategory;
import pl.krzywyyy.barter.utils.enums.Specialization;

class ProductSpecification {
    static Specification<Product> getSpecification(ProductSearchFilters filters) {
        boolean categorySpecified = !Strings.isNullOrEmpty(filters.getCategory());
        boolean specializationSpecified = !Strings.isNullOrEmpty(filters.getSpecialization());
        boolean locationAndDistanceSpecified = filters.getLatitude() != null
                && filters.getLongitude() != null
                && filters.getDistance() != null;
        boolean textSpecified = !Strings.isNullOrEmpty(filters.getSearchText());

        Specification<Product> specification = onlyActive();
        if (categorySpecified) {
            specification = specification.and(fromCategory(ProductCategory.valueOf(filters.getCategory())));
        }
        if (specializationSpecified) {
            specification = specification.and(fromSpecialization(Specialization.valueOf(filters.getSpecialization())));
        }
        if (locationAndDistanceSpecified) {
            specification = specification.and(maxDistance(filters.getLatitude(), filters.getLongitude(), filters.getDistance()));
        }
        if (textSpecified) {
            specification = specification.and(withWords(filters.getSearchText()));
        }
        return specification;
    }

    private static Specification<Product> onlyActive() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), 1);
    }

    private static Specification<Product> withWords(String searchText) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), '%' + searchText + '%');
    }

    private static Specification<Product> fromCategory(ProductCategory category) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    private static Specification<Product> fromSpecialization(Specialization specialization) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("specialization"), specialization);
    }

    private static Specification<Product> maxDistance(Float userLatitude, Float userLongitude, Integer distance) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(
                root.get(String.format("(ST_Distance_Sphere(point(latitude, longitude), point(%f, %f)) / 1000)", userLatitude, userLongitude)), distance);
    }

//    private static Specification<Product> maxDistance(Float userLatitude, Float userLongitude, Integer distance) {
//
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(
//                criteriaBuilder.function("distance", Float.class, criteriaBuilder.parameter(Double.class)  userLatitude, userLongitude), distance);
//    }
}
