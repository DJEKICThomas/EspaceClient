package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.entity.WebUserCompany;
import be.depairon.espaceclient.repository.WebUserCompanyRepository;
import be.depairon.espaceclient.service.WebUserCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WebUserCompanyServiceImpl implements WebUserCompanyService {

    private final WebUserCompanyRepository webUserCompanyRepository;

    public WebUserCompanyServiceImpl(WebUserCompanyRepository webUserCompanyRepository) {
        this.webUserCompanyRepository = webUserCompanyRepository;
    }

    @Override
    @Transactional
    public void save(WebUserCompany webUserCompany) {
        this.webUserCompanyRepository.save(webUserCompany);
    }
}
