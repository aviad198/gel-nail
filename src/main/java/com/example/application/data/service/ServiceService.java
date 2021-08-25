package com.example.application.data.service;

import com.example.application.data.entity.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService{
    private ServiceRepository serviceRepository;
    private CompanyRepository companyRepository;

    public List<TypeService> findAll() {
        return serviceRepository.findAll();
    }

    public ServiceService(ServiceRepository serviceRepository,
                          CompanyRepository companyRepository) {
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
    }
/*
    @PostConstruct
    public void populateTestData() {

        if (serviceRepository.count() == 0) {
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            serviceRepository.saveAll(
                    Stream.of("padikor 100 30","mnikor 120 60")
                            .map(name -> {
                                String[] split = name.split(" ");
                                TypeService typeService = new TypeService();
                                typeService.setName(split[0]);
                                typeService.setPrice(Integer.parseInt(split[1]));
                                typeService.setTime(Integer.parseInt(split[2]));
                                typeService.setCompany(companies.get(r.nextInt(companies.size())));
                                return typeService;
                            }).collect(Collectors.toList()));
        }
    }
 */
}
