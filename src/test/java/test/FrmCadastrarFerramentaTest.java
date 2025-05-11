package test;

import controle.FerramentaController;
import org.junit.jupiter.api.Test;
import servico.FerramentaService;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FrmCadastrarFerramentaTest {

    @Test
    public void deveCadastrarFerramentaComSucesso() {
        FrmFerramentaFake frm = new FrmFerramentaFake();
        FerramentaService mockService = mock(FerramentaService.class);

        when(mockService.cadastrarFerramenta(anyString(), anyString(), anyString()))
                .thenReturn(true);

        frm.getJTFNomeField().setText("Martelo");
        frm.getJTFMarcaField().setText("Tramontina");
        frm.getJTFValorField().setText("50");

        FerramentaController controller = new FerramentaController(frm, mockService);
        controller.cadastrar();

        assertEquals("", frm.getJTFNomeField().getText());
        assertEquals("", frm.getJTFMarcaField().getText());
        assertEquals("", frm.getJTFValorField().getText());
    }
}
