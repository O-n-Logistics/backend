package on.logistics.companyservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import on.logistics.companyservice.domain.entity.dtos.CreateCompanyDto;
import on.logistics.companyservice.domain.entity.enums.CompanyType;
import on.logistics.companyservice.domain.entity.vo.Address;
import on.logistics.companyservice.domain.entity.vo.Name;
import on.logistics.companyservice.global.domain.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE company SET is_deleted = true WHERE id = ?")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", unique = true, nullable = false)
    private UUID userId;

    @Embedded
    private Name name;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    @Column(name = "managed_hub_id", nullable = true)
    private UUID managedHubId;

    @Enumerated(EnumType.STRING)
    private Address address;

    public static Company create(CreateCompanyDto createCompanyDto) {
        return Company.builder()
            .userId(createCompanyDto.userId())
            .name(new Name(createCompanyDto.companyName()))
            .type(createCompanyDto.type())
            .address(new Address(createCompanyDto.companyAddress()))
            .build();
    }

    public void update(String companyName, String companyAddress) {
        if (companyName != null) {
            this.name = name.update(companyName);
        }
        if (companyAddress != null) {
            this.address = address.update(companyAddress);
        }
    }

    public void updateHub(UUID managedHubId) {
        this.managedHubId = managedHubId;
    }
}
