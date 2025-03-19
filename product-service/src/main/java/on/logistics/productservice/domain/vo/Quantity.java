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
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private Long value;

    public Quantity(final Long value) {
        validate(value);
        this.value = value;
    }

    private void validate(final Long value) {
        if (value < 0) {
            throw new ProductException(ProductExceptionCode.PRODUCT_QUANTITY_MIN);
        }
    }

    public Quantity update(final Long value) {
        return new Quantity(value);
    }

    public Quantity reduceQuantity(final Long value) {
        if (this.value - value < 0) {
            throw new ProductException(ProductExceptionCode.PRODUCT_QUANTITY_MIN);
        }
        return new Quantity(this.value - value);
    }

    public Quantity increaseQuantity(Long quantity) {
        return new Quantity(this.value + quantity);
    }
}
