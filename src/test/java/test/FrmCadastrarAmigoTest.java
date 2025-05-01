package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servico.AmigoService;
import visao.FrmCadastrarAmigo;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class FrmCadastrarAmigoTest {

    private FrmCadastrarAmigo frm;
    private AmigoService amigoServiceMock;

    @BeforeEach
    void setUp() throws Exception {
        frm = new FrmCadastrarAmigo();

        // Mock do serviço
        amigoServiceMock = mock(AmigoService.class);
        frm.amigoService = amigoServiceMock;

        // Criar campos privados simulados
        setPrivateField(frm, "JTFNome", new JTextField());
        setPrivateField(frm, "JTFTelefone", new JTextField());
    }

    @Test
    void testCadastrarComDadosValidos() throws Exception {
        getTextField("JTFNome").setText("Marquinhos");
        getTextField("JTFTelefone").setText("123456789");

        when(amigoServiceMock.insertAmigoBD("Marquinhos", 0, 123456789)).thenReturn(true);

        frm.cadastrar();

        verify(amigoServiceMock).insertAmigoBD("Marquinhos", 0, 123456789);
        assert getTextField("JTFNome").getText().isEmpty();
        assert getTextField("JTFTelefone").getText().isEmpty();
    }

    @Test
    void testCadastrarNomeInvalido() throws Exception {
        getTextField("JTFNome").setText("A");
        getTextField("JTFTelefone").setText("123456789");

        frm.cadastrar();

        verify(amigoServiceMock, never()).insertAmigoBD(anyString(), anyInt(), anyInt());
    }

    @Test
    void testCadastrarTelefoneInvalido() throws Exception {
        getTextField("JTFNome").setText("A");
        getTextField("JTFTelefone").setText("123");

        frm.cadastrar();

        verify(amigoServiceMock, never()).insertAmigoBD(anyString(), anyInt(), anyInt());
    }

    @Test
    void testCadastrarTelefoneNaoNumerico() throws Exception {
        getTextField("JTFNome").setText("A");
        getTextField("JTFTelefone").setText("abcdefgh");

        frm.cadastrar();

        verify(amigoServiceMock, never()).insertAmigoBD(anyString(), anyInt(), anyInt());
    }

    // Utilitário para alterar campo privado
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    // Utilitário para acessar campo JTextField privado
    private JTextField getTextField(String fieldName) throws Exception {
        Field field = frm.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (JTextField) field.get(frm);
    }
}
