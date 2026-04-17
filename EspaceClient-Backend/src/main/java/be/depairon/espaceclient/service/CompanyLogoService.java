package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.CompanyLogoDTO;

public interface CompanyLogoService {

    CompanyLogoDTO getLogoByCompany(String com);
}
