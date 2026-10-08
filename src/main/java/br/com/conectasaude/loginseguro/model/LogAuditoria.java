package br.com.conectasaude.loginseguro.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/** Registro de auditoria: nunca armazena senha, hash, token ou corpo de requisição. */
@Document(collection = "logs_auditoria")
public class LogAuditoria {
    @Id
    private String id;
    @Indexed
    private Instant ocorridoEm = Instant.now();
    @Indexed
    private String nivel;
    @Indexed
    private String evento;
    private String descricao;
    @Indexed
    private String ator; // identificador mascarado para reduzir exposição de dados pessoais

    public LogAuditoria() {}
    public LogAuditoria(String nivel, String evento, String descricao, String ator) {
        this.ocorridoEm = Instant.now();
        this.nivel = nivel;
        this.evento = evento;
        this.descricao = descricao;
        this.ator = ator;
    }
    public String getId() { return id; }
    public Instant getOcorridoEm() { return ocorridoEm; }
    public String getNivel() { return nivel; }
    public String getEvento() { return evento; }
    public String getDescricao() { return descricao; }
    public String getAtor() { return ator; }
}
