package adm.flordelis.GestaoFuncionarios.relatórioDiario;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;
import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioRepository;
import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioService;
import adm.flordelis.GestaoFuncionarios.funcionarios.dtos.CadastrarFuncionariosDTO;
import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.NovoRegistroRelatorioDTO;
import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.NovoRelatorioDTO;
import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.RelatorioDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository repository;
    private final FuncionarioService funcionarioService;

    public RelatorioService(RelatorioRepository repository, FuncionarioService funcionarioService) {
        this.repository = repository;
        this.funcionarioService = funcionarioService;
    }

    public List<RelatorioModel> getAll(){
        return repository.findAll();
    }

    public RelatorioModel getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relatorio nao encontrado"));
    }

    public RelatorioModel getByDate(LocalDate date){
        return repository.findByData(date)
                .orElseThrow(() -> new RuntimeException("Relatorio nao encontrado para essa data"));
    }

    public RelatorioModel adicionar(NovoRelatorioDTO dto) {
        RelatorioModel novo = new RelatorioModel(dto.data());
        return repository.save(novo);
    }

    public RelatorioModel adicionarRegistro(Long relatorioId, NovoRegistroRelatorioDTO registroDto) {
        RelatorioModel relatorio = getById(relatorioId);
        FuncionarioModel funcionario = funcionarioService.findById(registroDto.idFuncionario());
        RegistroPresenca novoRegistro = new RegistroPresenca(
                funcionario,
                registroDto.horaChegada(),
                registroDto.horaSaida()
        );
        relatorio.addRegistro(novoRegistro);
        return repository.save(relatorio);
    }

}
