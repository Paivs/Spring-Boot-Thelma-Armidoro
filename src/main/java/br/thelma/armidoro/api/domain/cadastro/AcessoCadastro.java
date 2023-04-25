package br.thelma.armidoro.api.domain.cadastro;

import br.thelma.armidoro.api.services.EmailService;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Random;

@Table(name = "acessocadastro")
@Entity(name = "AcessoCadastro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AcessoCadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String pin;

    private LocalDateTime data;

    private Boolean ativo;

    public AcessoCadastro(DadosCadastroAcessoCadastro dados) {
        this.login = dados.login();
        this.pin = gerarPin();
        this.data = LocalDateTime.now();
        this.ativo = true;
    }

    public void enviarPin(EmailService emailService){
        emailService.pinCadastro(this.login, this.pin);
    }

    public String gerarPin(){
        // Determia as letras que poderão estar presente nas chaves
        String caracterAlphaNumerico = "ABCDEFGHIJKLMNOPQRSTUVYWXZ1234567890";

        Random random = new Random();

        String armazenaChaves = "";
        int index = 1;

        for( int i = 0; i < 5; i++ ) {
            index = random.nextInt( caracterAlphaNumerico.length() );
            armazenaChaves += caracterAlphaNumerico.substring( index, index + 1 );
        }

        return armazenaChaves;
    }

    public boolean estaInvalido(LocalDateTime dataPin){
        LocalDateTime dataAgora = LocalDateTime.now();
        System.out.println("-- TESTE DE PIN --");

        if (dataPin.plusMinutes(5).isBefore(dataAgora)){
            System.out.println("PIN INVÁLIDO");
            return false;
        } else if(dataPin.plusMinutes(5).isAfter(dataAgora) || dataPin.plusMinutes(5).isEqual(dataAgora)){
            System.out.println("PIN VÁLIDO");
            return true;
        }else{
            System.out.println("PIN: ERRO DE LEITURA");
            return false;
        }
    }
}
