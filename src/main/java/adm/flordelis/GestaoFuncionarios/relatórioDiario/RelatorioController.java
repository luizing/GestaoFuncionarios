package adm.flordelis.GestaoFuncionarios.relatórioDiario;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;
import adm.flordelis.GestaoFuncionarios.funcionarios.dtos.FuncionarioDTO;
import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.RelatorioDTO;
import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.NovoRelatorioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {
    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RelatorioDTO>> getAll(){
        List<RelatorioDTO> relatorios = service.getAll().stream()
                .map(r -> new RelatorioDTO(r.getId(),r.getData(),r.getHoraInicio(),r.getHoraTermino(),r.getRegistros()))
                .toList();
        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioDTO> getByid(@PathVariable Long id){
        RelatorioModel relatorio = service.getById(id);
        return ResponseEntity.ok(relatorio.toDto());
    }

    @GetMapping("/{date}")
    public ResponseEntity<RelatorioDTO> getByDate(@PathVariable LocalDate data){
        RelatorioModel relatorio = service.getByDate(data);
        return ResponseEntity.ok(relatorio.toDto());
    }

    @PostMapping
    public ResponseEntity<RelatorioDTO> novoRelatorio(@RequestBody NovoRelatorioDTO dto) {
        RelatorioModel novo = service.adicionar(dto);
        return ResponseEntity.status(201).body(novo.toDto());
    }
}
