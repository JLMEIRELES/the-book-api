package br.com.ucb.book.service;

import br.com.ucb.book.domain.persistence.ReceitaPersistence;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReceitaServiceTest {

    @InjectMocks
    private ReceitaPersistence receitaPersistence;
}
