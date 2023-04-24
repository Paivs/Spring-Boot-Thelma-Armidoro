package br.thelma.armidoro.api.domain.cadastro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcessoCadastroRepository extends JpaRepository<AcessoCadastro, Long> {

    @Query("""
            select a
            from AcessoCadastro a
            where
            a.login = :login
            """)
    List<AcessoCadastro> findSeJaGerou(String login);

    @Query("""
            select a
            from AcessoCadastro a
            where
            a.pin = :pin
            and
            a.login = :login
            and
            a.ativo = 1
            """)
    List<AcessoCadastro> findPorPin(String login, String pin);
}
