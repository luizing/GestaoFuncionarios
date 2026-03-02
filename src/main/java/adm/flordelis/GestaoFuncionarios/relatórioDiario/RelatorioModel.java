package adm.flordelis.GestaoFuncionarios.relatórioDiario;

import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.RegistrosDTO;
import adm.flordelis.GestaoFuncionarios.relatórioDiario.dtos.RelatorioDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private List<RegistroPresenca> registros = new ArrayList<>();

    public RelatorioModel(LocalDate data){
        this.data = data;
        this.horaInicio = LocalTime.of(7,30);
    }


    public void addRegistro(RegistroPresenca novoRegistro) {
        boolean jaCadastrado = this.registros.stream()
                .anyMatch(r -> r.getFuncionario().getId().equals(novoRegistro.getFuncionario().getId()));
        if (jaCadastrado) {
            throw new RuntimeException("Funcionário já registrado neste relatório diário.");
        }
        this.registros.add(novoRegistro);
    }

    public RelatorioDTO toDto(){
        List<RegistrosDTO> registrosDTO = this.registros.stream()
                .map(reg -> new RegistrosDTO(
                        reg.getFuncionario().getId(),
                        reg.getHorarioChegada(),
                        reg.getHorarioSaida()
                ))
                .toList();
        return new RelatorioDTO(this.id,this.data,this.horaInicio,this.horaTermino, registrosDTO);
    }
}

