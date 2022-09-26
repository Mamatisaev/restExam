package dortmund.service;

import dortmund.dto.request.CompanyRequest;
import dortmund.dto.response.CompanyResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.CompanyResponseView;
import dortmund.entity.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    CompanyResponse save(CompanyRequest companyRequest);

    List<CompanyResponse> findAll();

    CompanyResponse findById(Long companyId);

    Company getCompanyById(Long companyId);

    CompanyResponse updateCompanyById(Long companyId, CompanyRequest companyRequest);

    SimpleResponse delete(Long companyId);

    CompanyResponseView getPaginationOfCompanies(int page, int size);

}