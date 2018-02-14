package com.ds.spring.spark.example.repository;

import com.ds.spring.spark.example.models.TrxInfoRecord;
import org.springframework.data.repository.CrudRepository;

public interface TrxRecordRepository extends CrudRepository<TrxInfoRecord, Long>{

}
