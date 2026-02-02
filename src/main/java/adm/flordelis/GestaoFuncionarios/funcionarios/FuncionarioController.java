package adm.flordelis.GestaoFuncionarios.funcionarios;

import adm.flordelis.GestaoFuncionarios.funcionarios.dtos.CadastrarFuncionariosDTO;
import adm.flordelis.GestaoFuncionarios.funcionarios.dtos.FuncionarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> getAll(){
        List<FuncionarioModel> funcionarios = service.getAll();

    };

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id){};

    @PostMapping
    public ResponseEntity<FuncionarioDTO> cadastrar(@RequestBody CadastrarFuncionariosDTO dto){};
}
