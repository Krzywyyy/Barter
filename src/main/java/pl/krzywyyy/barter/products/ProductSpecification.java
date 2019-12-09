package pl.krzywyyy.barter.products;

import com.google.common.base.Strings;
import org.springframework.data.jpa.domain.Specification;
import pl.krzywyyy.barter.utils.enums.ProductCategory;
import pl.krzywyyy.barter.utils.enums.Specialization;

import javax.persistence.criteria.ParameterExpression;

class ProductSpecification {
    static Specification<Product> getSpecification(ProductSearchFilters filters) {
        boolean categorySpecified = !Strings.isNullOrEmpty(filters.getCategory());
        boolean specializationSpecified = !Strings.isNullOrEmpty(filters.getSpecialization());
        boolean locationAndDistanceSpecified = filters.getLatitude() != null
                && filters.getLongitude() != null
                && filters.getDistance() != null;

        Specification<Product> specification = null;
        if (categorySpecified) {
            specification = fromCategory(ProductCategory.valueOf(filters.getCategory()));
        }
        if (specializationSpecified) {
            if (specification != null) {
                specification = specification.and(fromSpecialization(Specialization.valueOf(filters.getSpecialization())));
            } else {
                specification = fromSpecialization(Specialization.valueOf(filters.getSpecialization()));
            }
        }
        if (locationAndDistanceSpecified) {
            if (specification != null) {
                specification = specification.and(maxDistance(filters.getLatitude(), filters.getLongitude(), filters.getDistance()));
            } else {
                specification = maxDistance(filters.getLatitude(), filters.getLongitude(), filters.getDistance());
            }
        }
        return specification;
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
