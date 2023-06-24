
package br.com.alurafood.pagamentos.dto;

import java.math.BigDecimal;
import br.com.alurafood.pagamentos.Model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTO{


    private Long Id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Long pedidoId;
    private Long formaDePagamentoId;
    
}