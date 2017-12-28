package com.cit.kyc.cache.repository;

import com.cit.kyc.cache.models.KycInfoRecord;
import org.springframework.data.repository.CrudRepository;

public interface KYCRecordRepository extends CrudRepository<KycInfoRecord, Long>{

}
