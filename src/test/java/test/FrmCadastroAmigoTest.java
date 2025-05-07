package test;

import org.junit.jupiter.api.*;
import org.mockito.*;
import servico.AmigoService;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FrmCadastroAmigoTest {

    @Mock
    AmigoService service; // Mock da classe AmigoService
    FrmAmigoFake frm;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        frm = new FrmAmigoFake();
        when(service.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(true); // Simula o comportamento do método insertAmigoBD
    }

    @Test
    public void testCadastroComSucesso() {
        frm.getJTFNome().setText("João Teste");
        frm.getJTFTelefone().setText("123456789");
        frm.cadastrar();

        assertEquals("Amigo Cadastrado com Sucesso!", frm.getMensagem());
    }

    @Test
    public void testCadastroComNomeInvalido() {
        frm.getJTFNome().setText("J");
        frm.getJTFTelefone().setText("123456789");
        frm.cadastrar();

        assertEquals("Nome deve conter ao menos 2 caracteres.", frm.getMensagem());
    }

    @Test
    public void testCadastroComTelefoneInvalido() {
        frm.getJTFNome().setText("Nome Válido");
        frm.getJTFTelefone().setText("123"); // Inválido (menos que 9 dígitos)
        frm.cadastrar();

        assertEquals("Informe um número válido.", frm.getMensagem());
    }

    @Test
    public void testCadastroComTelefoneNaoNumerico() {
        frm.getJTFNome().setText("Teste");
        frm.getJTFTelefone().setText("abcdefghi"); // não é número
        frm.cadastrar();

        assertEquals("Informe um número válido.", frm.getMensagem());
    }

    @AfterEach
    public void tearDown() {
        frm = null;
        service = null;
    }
}
