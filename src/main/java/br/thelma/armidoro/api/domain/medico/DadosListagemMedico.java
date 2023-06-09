package br.thelma.armidoro.api.domain.medico;

public record DadosListagemMedico(Long id, String nome, String email, String crm) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrp());
    }

}
