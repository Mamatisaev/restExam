package dortmund.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponse {

    private Long companyId;

    private String companyName;

    private String locatedCountry;

}