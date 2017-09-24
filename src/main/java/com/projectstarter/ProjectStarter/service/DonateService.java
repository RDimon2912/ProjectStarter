package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Donate;
import com.projectstarter.ProjectStarter.repository.DonateRepository;
import com.projectstarter.ProjectStarter.service.dto.donate.DonateDto;
import com.projectstarter.ProjectStarter.service.transformer.DonateTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DonateService {

    private final DonateRepository donateRepository;
    private final DonateTransformer donateTransformer;

    @Transactional(readOnly = true)
    public List<DonateDto> findTheBiggestDonations() {
        List<Donate> donateList = donateRepository.findAllOrderByAmountDescLimitN(3);
        List<DonateDto> donateDtoList = new ArrayList<>();
        for (Donate donate: donateList) {
            donateDtoList.add(donateTransformer.makeObjectDonateDto(donate));
        }
        return donateDtoList;
    }
}
