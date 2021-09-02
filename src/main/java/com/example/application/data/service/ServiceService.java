package com.example.application.data.service;

import com.example.application.data.entity.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService{

    private ServiceRepository serviceRepository;
    private CompanyRepository companyRepository;

    public List<TypeService> findAll() {
        return (List<TypeService>) serviceRepository.findAll().stream()
                .distinct()
                .collect(Collectors.toList());
    }
    public List<TypeService> findByName(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return (List<TypeService>) serviceRepository.findAll();
        } else {
            return serviceRepository.search(stringFilter);
        }

    }


    public ServiceService(@Autowired ServiceRepository serviceRepository,
                          CompanyRepository companyRepository) {
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
    }

    public void addService(TypeService typeService){
        serviceRepository.save(typeService);
    }

    public void updateService(Integer idService ,TypeService typeService){
        serviceRepository.save(typeService);
    }

    public void deleteService(Integer idService){
        serviceRepository.deleteById(idService);
    }

    @PostConstruct
    private void postConstruct() {


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
