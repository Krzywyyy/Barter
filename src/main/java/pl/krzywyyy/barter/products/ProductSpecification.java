package pl.krzywyyy.barter.products;

import com.google.common.base.Strings;
import org.springframework.data.jpa.domain.Specification;
import pl.krzywyyy.barter.utils.enums.ProductCategory;
import pl.krzywyyy.barter.utils.enums.Specialization;

class ProductSpecification {
    static Specification<Product> getSpecification(ProductSearchFilters filters) {
        boolean categorySpecified = !Strings.isNullOrEmpty(filters.getCategory());
        boolean specializationSpecified = !Strings.isNullOrEmpty(filters.getSpecialization());

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
        return specification;
    }

    private static Specification<Product> fromCategory(ProductCategory category) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    private static Specification<Product> fromSpecialization(Specialization specialization) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("specialization"), specialization);
    }
}
