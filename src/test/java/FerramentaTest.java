/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import modelo.Ferramenta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ianfu
 */
public class FerramentaTest {
    
    public FerramentaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Ferramenta.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Ferramenta instance = new Ferramenta();
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Ferramenta.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int Id = 0;
        Ferramenta instance = new Ferramenta();
        instance.setId(Id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNome method, of class Ferramenta.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Ferramenta instance = new Ferramenta();
        String expResult = "";
        String result = instance.getNome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNome method, of class Ferramenta.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String Nome = "";
        Ferramenta instance = new Ferramenta();
        instance.setNome(Nome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMarca method, of class Ferramenta.
     */
    @Test
    public void testGetMarca() {
        System.out.println("getMarca");
        Ferramenta instance = new Ferramenta();
        String expResult = "";
        String result = instance.getMarca();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMarca method, of class Ferramenta.
     */
    @Test
    public void testSetMarca() {
        System.out.println("setMarca");
        String Marca = "";
        Ferramenta instance = new Ferramenta();
        instance.setMarca(Marca);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValor method, of class Ferramenta.
     */
    @Test
    public void testGetValor() {
        System.out.println("getValor");
        Ferramenta instance = new Ferramenta();
        int expResult = 0;
        int result = instance.getValor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValor method, of class Ferramenta.
     */
    @Test
    public void testSetValor() {
        System.out.println("setValor");
        int Valor = 0;
        Ferramenta instance = new Ferramenta();
        instance.setValor(Valor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
