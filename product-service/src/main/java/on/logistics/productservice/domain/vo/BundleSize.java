package on.logistics.productservice.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import on.logistics.productservice.exception.ProductException;
import on.logistics.productservice.exception.ProductExceptionCode;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BundleSize {

    @Column(name = "bundle_size", nullable = false)
    private Long value;

    public BundleSize(final Long value) {
        validate(value);
        this.value = value;
    }

    private void validate(final Long value) {
        if (value <= 0) {
            throw new ProductException(ProductExceptionCode.PRODUCT_BUNDLE_SIZE_MIN);
        }
    }

    public BundleSize update(final Long value) {
        return new BundleSize(value);
    }

}
