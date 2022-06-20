package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import automation.App;


public class AppTest {

    @Test
    public void multiplicacionReturnZero() {

        App tester = new App();

        //Asset statements
        assertEquals(0, tester.multiplicacion(10, 0), "10 x 0 debe ser 0");
        assertEquals(0, tester.multiplicacion(0, 10), "0 x 10 debe ser 0");
        assertEquals(0, tester.multiplicacion(0, 0), "0 x 0 debe ser 0");
        
    }

    @Test
    @DisplayName("Test Booleano")    
    public void resultIsNull(){

        App tester = new App();

        //Asset statements
        assertFalse(tester.isNull(tester.multiplicacion(10, 1)), "El resultado debe ser True");
        assertTrue(tester.isNull(tester.multiplicacion(10, 0)), "El resultado debe ser False");
    }


}

