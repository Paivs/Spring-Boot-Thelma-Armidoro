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
