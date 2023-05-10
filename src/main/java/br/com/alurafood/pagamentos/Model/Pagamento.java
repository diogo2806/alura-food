package br.com.alurafood.pagamentos.Model;

import java.math.BigDecimal;
import javax.management.loading.PrivateClassLoader;
import org.springframework.data.annotation.Id;
import javax.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    //private Status status;
    private Long pedidoId;
    private Long formaDePagamentoId;
    
}
