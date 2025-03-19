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
public class Name {

    public static final int MAX_LENGTH = 100;

    @Column(name = "name", nullable = false, length = MAX_LENGTH)
    private String value;

    public Name(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (Objects.isNull(value)) {
            throw new CompanyException(CompanyExceptionCode.COMPANY_NAME_IS_NULL);
        }
        if (value.length() > MAX_LENGTH) {
            throw new CompanyException(CompanyExceptionCode.COMPANY_NAME_MAX_LENGTH);
        }
    }

    public Name update(final String value) {
        return new Name(value);
    }
}
