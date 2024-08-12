package com.example.healthCard.service;

import com.example.healthCard.dto.ConsultationDto;
import com.example.healthCard.healthCardException.HealthCardException;
import com.example.healthCard.model.ChiefEntity;
import com.example.healthCard.model.HealthCardConsultant;
import com.example.healthCard.model.HospitalEntity;
import com.example.healthCard.model.MemberEntity;
import com.example.healthCard.repo.ChiefRepo;
import com.example.healthCard.repo.HealthCardConsultantRepo;
import com.example.healthCard.repo.HospitalRepo;
import com.example.healthCard.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    HealthCardConsultantRepo healthCardConsultantRepo;

    @Autowired
    HospitalRepo hospitalRepo;

    @Autowired
    ChiefRepo chiefRepo;

    @Autowired
    MemberRepo memberRepo;

    public void addConsultation(ConsultationDto consultationDto){
        try {

            Optional<HospitalEntity> optionalHospitalEntity = hospitalRepo.findById(consultationDto.getHospitalId());
            if (optionalHospitalEntity.isPresent()){
                throw new HealthCardException("You must enter a valid hospital id.", 400);
            }
            HospitalEntity hospitalEntity = optionalHospitalEntity.get();

            Optional<ChiefEntity> optionalChiefEntity = chiefRepo.findById(consultationDto.getCustomerId());
            ChiefEntity chiefEntity = null;
            MemberEntity memberEntity = null;
            if (optionalChiefEntity.isEmpty()){
                Optional<MemberEntity> optionalMemberEntity = memberRepo.findById(consultationDto.getCustomerId());
                if (optionalMemberEntity.isEmpty()){
                    throw new HealthCardException("You must enter a valid customer id.", 400);
                }
                else {
                    memberEntity = optionalMemberEntity.get();
                }
            }else {
                chiefEntity = optionalChiefEntity.get();
            }
            HealthCardConsultant healthCardConsultant = HealthCardConsultant.builder()
                    .hospitalEntity(hospitalEntity)
                    .chiefEntity(chiefEntity)
                    .memberEntity(memberEntity)
                    .healthCardId(chiefEntity.getId())
                    .visitedTimeStamp(System.currentTimeMillis())
                    .build();

            healthCardConsultantRepo.save(healthCardConsultant);

        }catch (HealthCardException exception){
            throw exception;
        }
        catch (Exception exception){
            throw new HealthCardException(exception.getLocalizedMessage(), 500);
        }
    }
}
