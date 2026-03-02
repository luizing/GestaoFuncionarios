package adm.flordelis.GestaoFuncionarios.relatórioDiario;

import adm.flordelis.GestaoFuncionarios.funcionarios.FuncionarioModel;
import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.RelatorioDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_relatorios")
public class RelatorioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaTermino;

    @ElementCollection
    private List<RegistroPresenca> registros;

    public RelatorioModel(LocalDate data){
        this.data = data;
        this.horaInicio = LocalTime.of(7,30);
    }

    public void addRegistro(RegistroPresenca novo){
        registros.add(novo);
    }

    public RelatorioDTO toDto(){
        return new RelatorioDTO(this.id,this.data,this.horaInicio,this.horaTermino, this.registros);
    }
}

