package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Ponto de entrada do processo de pagamentos da AluraFood.
 *
 * <p>Esta classe concentra as operacoes que uma pessoa usuaria ou outro sistema
 * pode solicitar sobre pagamentos: consultar a lista, ver detalhes, registrar um
 * novo pagamento, atualizar informacoes e remover um pagamento quando necessario.</p>
 *
 * <p>Na visao do processo, o controller recebe a solicitacao, encaminha a regra
 * para o servico de pagamentos e devolve uma resposta clara para quem consumiu a API.</p>
 */
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    /**
     * Apresenta a lista de pagamentos registrados no sistema.
     *
     * <p>Use este metodo quando o processo precisar acompanhar os pagamentos ja
     * cadastrados, permitindo navegar pelos resultados em paginas para facilitar
     * consultas com muitos registros.</p>
     *
     * @param paginacao definicao de pagina, tamanho e ordenacao usada para montar a consulta.
     * @return pagina com os pagamentos encontrados no formato usado para resposta da API.
     */
    @GetMapping
    public Page<PagamentoDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return service.obterTodos(paginacao);
    }

    /**
     * Exibe as informacoes de um pagamento especifico.
     *
     * <p>Use este metodo quando a pessoa usuaria ou outro sistema precisar conferir
     * os dados de um pagamento individual antes de tomar uma decisao no processo,
     * como validar status, valor ou dados relacionados.</p>
     *
     * @param id identificador do pagamento que deve ser consultado.
     * @return resposta com os detalhes do pagamento solicitado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id) {
        PagamentoDto dto = service.obterPorId(id);

        return ResponseEntity.ok(dto);
    }


    /**
     * Registra um novo pagamento no fluxo da aplicacao.
     *
     * <p>Use este metodo quando um pedido ou outra etapa do processo precisar criar
     * uma cobranca/pagamento para posterior acompanhamento. Ao concluir, a resposta
     * informa onde o novo pagamento pode ser consultado.</p>
     *
     * @param dto informacoes necessarias para criar o pagamento.
     * @param uriBuilder recurso usado para montar o endereco de consulta do pagamento criado.
     * @return resposta com o pagamento criado e o endereco para acessa-lo novamente.
     */
    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
        PagamentoDto pagamento = service.criarPagamento(dto);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamento);
    }

    /**
     * Atualiza as informacoes de um pagamento existente.
     *
     * <p>Use este metodo quando alguma informacao do pagamento precisar ser ajustada
     * dentro do processo, mantendo o mesmo registro e substituindo os dados antigos
     * pelos dados recebidos na solicitacao.</p>
     *
     * @param id identificador do pagamento que deve ser atualizado.
     * @param dto novas informacoes que devem ficar registradas para o pagamento.
     * @return resposta com o pagamento ja atualizado.
     */
    @PutMapping
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto dto) {
        PagamentoDto atualizado = service.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Remove um pagamento do processo.
     *
     * <p>Use este metodo quando um pagamento nao deve mais permanecer disponivel
     * no sistema, por exemplo em situacoes de cancelamento, correcao operacional ou
     * limpeza de registros que nao devem seguir no fluxo.</p>
     *
     * @param id identificador do pagamento que deve ser removido.
     * @return resposta sem conteudo, indicando que a remocao foi concluida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id) {
        service.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
