package test;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    AmigoServiceTest.class,     // Classe de teste AmigoServiceTest
    DevolucaoServiceTest.class // Classe de teste DevolucaoServiceTest
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteSuite {
    // Essa classe serve apenas para agrupar os testes
}
