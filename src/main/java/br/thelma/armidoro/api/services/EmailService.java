package br.thelma.armidoro.api.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
@NoArgsConstructor
@AllArgsConstructor
public class EmailService {

    @Autowired
    public JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    public String remetente;

    public String enviarEmailTexto(String destinatario, String titulo, String mensagem){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(remetente);

        message.setText(mensagem);
        message.setTo(destinatario);
        message.setSubject(titulo);

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

    public void pinCadastro(String destinatario, String pin){
        String mensagem = """
Muito obrigado por querer fazer parte do nosso aplicativo, ficamos felizes em tê-lo conosco!

Esse é seu PIN:""" + " " + pin + "\n\nAté a próxima! Não é necessário responder essa mensagem.";

        enviarEmailTexto(destinatario,"PIN Thelma Amidoro" ,mensagem);
    }

    public void pinMudarSenha(String destinatario, String pin){
        String mensagem = """
Mudar senha Thelma Armidoro...

Esse é seu PIN:""" + " " + pin + "\n" +
                "Caso não tenha sido você quem requisitou a mudança de senha, favor entrar em contato com o suporte imediatamente \n" +
        "\nAté a próxima! Não é necessário responder essa mensagem.";

        enviarEmailTexto(destinatario,"PIN Thelma Amidoro - Mudança de senha" ,mensagem);
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

}
