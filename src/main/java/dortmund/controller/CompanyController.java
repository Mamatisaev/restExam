package dortmund.controller;

import dortmund.dto.request.CompanyRequest;
import dortmund.dto.response.CompanyResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.CompanyResponseView;
import dortmund.serviceImpl.CompanyServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("api/companies")
@PreAuthorize("hasAuthority('ADMIN')")
public class CompanyController {

    private final CompanyServiceImpl companyServiceImpl;

    public CompanyController(CompanyServiceImpl companyServiceImpl) {
        this.companyServiceImpl = companyServiceImpl;
    }

    @PostMapping("/saveCompany")
    public CompanyResponse save(@RequestBody CompanyRequest companyRequest) {
        return companyServiceImpl.save(companyRequest);
    }

    @GetMapping("/getAllCompanies")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public List<CompanyResponse> findAll() {
        return companyServiceImpl.findAll();
    }

    @GetMapping("/findCompany/{companyId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public CompanyResponse findById(@PathVariable Long companyId) {
        return companyServiceImpl.findById(companyId);
    }

    @PutMapping("/updateCompany/{companyId}")
    public CompanyResponse updateCompanyById(@PathVariable Long companyId,
                                             @RequestBody CompanyRequest companyRequest) {
        return companyServiceImpl.updateCompanyById(companyId, companyRequest);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    public SimpleResponse delete(@PathVariable Long companyId) {
        return companyServiceImpl.delete(companyId);
    }

    @GetMapping("/paginationOfCompanies")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public CompanyResponseView getPaginationOfCompanies(@RequestParam int page,
                                                        @RequestParam int size) {
        return companyServiceImpl.getPaginationOfCompanies(page, size);
    }

}