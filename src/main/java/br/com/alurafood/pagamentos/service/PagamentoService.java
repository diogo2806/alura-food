package br.com.alurafood.pagamentos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alurafood.pagamentos.Model.Pagamento;
import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PagamentoService {


    @Autowired
    private PagamentoRepository repository;

    
    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDTO> obterTodos(Pageable paginacao){
        return repository
            .findAll(paginacao)
            .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long Id){
        Pagamento pagamento = repository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException());

        return  modelMapper.map(pagamento, PagamentoDTO.class);
    }


}
