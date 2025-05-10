package test;

import controle.AmigoController;
import visao.FrmCadastrarAmigo;
import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import servico.AmigoService;

public class FrmCadastroAmigoTest {

    @Mock
    AmigoService amigoService;

    FrmCadastrarAmigo frm;
    AmigoController controller;

    @BeforeEach
    public void setUp() {
        System.setProperty("test.environment", "true"); // Define a propriedade de ambiente de teste
        MockitoAnnotations.openMocks(this);
        frm = new FrmCadastrarAmigo() {
            // Sobrescreve a exibição de mensagem para imprimir no console durante os testes
            @Override
            public void mostrarMensagem(String mensagem) {
                System.out.println(mensagem);
            }
        };
        controller = new AmigoController(frm);
        frm.setController(controller);
    }

    @Test
    public void testCadastroComSucesso() {
        when(amigoService.insertAmigoBD(anyString(), anyInt(), anyInt())).thenReturn(true);

        frm.getJTFNome().setText("João Teste");
        frm.getJTFTelefone().setText("123456789");

        controller.cadastrarAmigo(); // Executa a lógica do cadastro

        assertEquals("Amigo Cadastrado com Sucesso!", frm.getUltimaMensagem());
    }
}
