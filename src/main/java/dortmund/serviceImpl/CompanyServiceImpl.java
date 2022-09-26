package dortmund.serviceImpl;

import dortmund.dto.mapper.CompanyMapper;
import dortmund.dto.request.CompanyRequest;
import dortmund.dto.response.CompanyResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.CompanyResponseView;
import dortmund.entity.Company;
import dortmund.exception.NotFoundException;
import dortmund.repository.CompanyRepository;
import dortmund.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyMapper companyMapper, CompanyRepository companyRepository) {
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
    }

    public CompanyResponse save(CompanyRequest companyRequest) {
        Company company = companyMapper.mapToEntity(companyRequest);
        Company company1 = companyRepository.save(company);
        return companyMapper.mapToResponse(company1);
    }

    public List<CompanyResponse> findAll() {
        List<CompanyResponse> companyResponses = new ArrayList<>();
        for (Company company : companyRepository.findAll()) {
            companyResponses.add(companyMapper.mapToResponse(company));
        }
        return companyResponses;
    }

    public CompanyResponse findById(Long companyId) {
        Company company = getCompanyById(companyId);
        return companyMapper.mapToResponse(company);
    }

    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(
                () -> new NotFoundException("Company with id " + companyId + " doesn't exist."));
    }

    public CompanyResponse updateCompanyById(Long companyId, CompanyRequest companyRequest) {
        Company company = getCompanyById(companyId);
        String currentCompanyName = company.getCompanyName();
        String newCompanyName = companyRequest.getCompanyName();
        if (newCompanyName != null && !newCompanyName.equals(currentCompanyName)) {
            company.setCompanyName(newCompanyName);
        }
        String currentLocatedCountry = company.getLocatedCountry();
        String newLocatedCountry = companyRequest.getLocatedCountry();
        if (newLocatedCountry != null && !newLocatedCountry.equals(currentLocatedCountry)) {
            company.setLocatedCountry(newLocatedCountry);
        }
        companyMapper.updateCompany(company, companyRequest);
        companyRepository.save(company);
        return companyMapper.mapToResponse(company);
    }

    public SimpleResponse delete(Long companyId) {
        boolean exists = companyRepository.existsById(companyId);
        if (!exists) {
            throw new NotFoundException("Company with id " + companyId + " doesn't exist.");
        }
        companyRepository.deleteById(companyId);
        return new SimpleResponse("DELETED", "Company with id " + companyId + " is successfully deleted.");
    }

    @Override
    public CompanyResponseView getPaginationOfCompanies(int page, int size) {
        CompanyResponseView companyResponseView = new CompanyResponseView();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("companyName"));
        List<CompanyResponse> companyResponses = new ArrayList<>();
        Page<Company> allCompanies = companyRepository.findAll(pageable);
        for (Company company : allCompanies) {
            companyResponses.add(companyMapper.mapToResponse(company));
        }
        companyResponseView.setCompanyResponses(companyResponses);
        companyResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        companyResponseView.setTotalPages(allCompanies.getTotalPages());
        return companyResponseView;
    }

}