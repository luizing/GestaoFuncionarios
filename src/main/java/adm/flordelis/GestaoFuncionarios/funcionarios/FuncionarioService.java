package adm.flordelis.GestaoFuncionarios.funcionarios;

import adm.flordelis.GestaoFuncionarios.funcionarios.dtos.CadastrarFuncionariosDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<FuncionarioModel> getAll(){
         return repository.findAll();
    }

    public FuncionarioModel findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
    }

    public FuncionarioModel cadastrar(CadastrarFuncionariosDTO dto) {
        FuncionarioModel novo = new FuncionarioModel(dto.nome(), dto.contratoInicial());
        return repository.save(novo);
    }
}
