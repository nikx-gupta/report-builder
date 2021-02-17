package org.devignite.reportBuilder.repository;


import org.devignite.reportBuilder.model.ReportMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface ReportManifestRepository extends MongoRepository<ReportMetadata, String> {

}
