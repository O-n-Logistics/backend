package on.logistics.companyservice.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import on.logistics.companyservice.exception.CompanyException;
import on.logistics.companyservice.exception.CompanyExceptionCode;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "address", nullable = false)
    private String value;

    public Address(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (Objects.isNull(value)) {
            throw new CompanyException(CompanyExceptionCode.COMPANY_ADDRESS_IS_NULL);
        }
    }

    public Address update(final String value) {
        return new Address(value);
    }
}
