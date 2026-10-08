package br.com.conectasaude.loginseguro.repository;

import br.com.conectasaude.loginseguro.model.LogAuditoria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogAuditoriaRepository extends MongoRepository<LogAuditoria, String> {
    long countByNivel(String nivel);
}
