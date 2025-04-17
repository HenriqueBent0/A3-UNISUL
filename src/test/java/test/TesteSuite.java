package test;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    AmigoServiceTest.class
    // Adicione outras classes de teste aqui, se tiver
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteSuite {
    // Essa classe serve apenas para agrupar os testes
}
