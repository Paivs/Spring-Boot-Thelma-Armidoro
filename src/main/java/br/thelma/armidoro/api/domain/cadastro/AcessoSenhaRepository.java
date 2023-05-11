package br.thelma.armidoro.api.domain.cadastro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcessoSenhaRepository extends JpaRepository<AcessoSenha, Long> {

    @Query("""
            select a
            from AcessoSenha a
            where
            a.login = :login
            """)
    List<AcessoSenha> findSeJaGerou(String login);

    @Query("""
            select a
            from AcessoSenha a
            where
            a.pin = :pin
            and
            a.login = :login
            and
            a.ativo = 1
            """)
    AcessoSenha findPorPin(String login, String pin);
}
