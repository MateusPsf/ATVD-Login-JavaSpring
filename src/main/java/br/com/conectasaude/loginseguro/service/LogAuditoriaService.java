package br.com.conectasaude.loginseguro.service;

import br.com.conectasaude.loginseguro.model.LogAuditoria;
import br.com.conectasaude.loginseguro.repository.LogAuditoriaRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LogAuditoriaService {
    private final LogAuditoriaRepository repository;
    private final MongoTemplate mongoTemplate;

    public LogAuditoriaService(LogAuditoriaRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void registrar(String nivel, String evento, String descricao, String ator) {
        // Lista de campos permitidos: não receber nem persistir dados de requisição arbitrários.
        repository.save(new LogAuditoria(nivel, evento, descricao, mascararIdentificador(ator)));
    }

    public List<LogAuditoria> pesquisar(String nivel, String busca) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "ocorridoEm")).limit(200);
        if (StringUtils.hasText(nivel) && !"TODOS".equalsIgnoreCase(nivel)) {
            query.addCriteria(Criteria.where("nivel").is(nivel.toUpperCase(Locale.ROOT)));
        }
        if (StringUtils.hasText(busca)) {
            String termo = busca.trim();
            if (termo.length() > 80) termo = termo.substring(0, 80);
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("evento").regex(java.util.regex.Pattern.quote(termo), "i"),
                    Criteria.where("descricao").regex(java.util.regex.Pattern.quote(termo), "i"),
                    Criteria.where("ator").regex(java.util.regex.Pattern.quote(termo), "i")));
        }
        return mongoTemplate.find(query, LogAuditoria.class);
    }

    public long contarTodos() { return repository.count(); }
    public long contarNivel(String nivel) { return repository.countByNivel(nivel); }

    public static String mascararIdentificador(String identificador) {
        if (!StringUtils.hasText(identificador) || "anonymousUser".equals(identificador)) return "Não identificado";
        String valor = identificador.trim();
        int arroba = valor.indexOf('@');
        if (arroba > 0 && arroba < valor.length() - 1) {
            String usuario = valor.substring(0, arroba);
            String dominio = valor.substring(arroba + 1);
            return usuario.substring(0, 1) + "***@" + dominio;
        }
        return valor.length() <= 2 ? "**" : valor.substring(0, 1) + "***";
    }
}
