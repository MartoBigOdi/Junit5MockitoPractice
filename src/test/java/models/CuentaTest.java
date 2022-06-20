package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import automation.Banco;
import automation.Cuenta;
import exceptions.DineroInsuficienteException;

class CuentaTest {

    protected static void Mostrar(String mensaje){
        System.out.println(mensaje);
    }

    
    @BeforeEach
    void sysoMostrar(){
        System.out.println("---------------------------- running tests ----------------------------");
    }


    @Test
    void TestNombreCuenta(){
        Cuenta cuenta = new Cuenta("Marto", new BigDecimal("1000.23424"));
        //cuenta.setPersona("Marto");
        String esperado = "Marto";
        String real = cuenta.getPersona();
        
        assertEquals(esperado, real);
        assertTrue(real.equals("Marto"));
      
    }


    @Test
    void testSaldoCuenta(){
         Cuenta cuenta1 = new Cuenta("Marto", new BigDecimal("1000.23424"));

         //Convertimos el BigDecimal en double para comparar con el double que le pasamos de 
         //Expected
        assertEquals(1000.23424, cuenta1.getSaldo().doubleValue());
        //Dos formas de probar la misma premisa
        assertFalse(cuenta1.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta1.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        
    }
    
    
    @Test
    void testReferenciaCuenta(){
       Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("1123.3642"));
       Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("1123.3642"));
        
        //assertNotEquals(cuenta1, cuenta2, "No son iguales");
        assertEquals(cuenta1, cuenta2, "No son iguales");
    }


   @Test
   void testDebitoCuenta(){
    Cuenta cuenta = new Cuenta("Pablo Thompson", new BigDecimal("1000.3152"));
    cuenta.debito(new BigDecimal(100));

    assertNotNull(cuenta.getSaldo(), "No puede ser Null");
    assertEquals(900, cuenta.getSaldo().intValue());
    //Volvemos el valor del saldo en String para comparar
    assertEquals("900.3152", cuenta.getSaldo().toPlainString());
   }


    @Test
    void testCreditoCuenta(){
        Cuenta cuenta = new Cuenta("Pablo Thompson", new BigDecimal("1000.3152"));
        cuenta.credito(new BigDecimal(100));
    
        assertNotNull(cuenta.getSaldo(), "No puede ser Null");
        assertEquals(1100, cuenta.getSaldo().intValue());
        //Volvemos el valor del saldo en String para comparar
        assertEquals("1100.3152", cuenta.getSaldo().toPlainString());
       }



    @Test
    void testDineroInsuficienteExceptions(){
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
                cuenta.debito(new BigDecimal(1001));
        });

        String real = exception.getMessage();
        String msjEsperado = "Dinero Insuficiente";

        System.out.println(real);
        assertEquals(real, msjEsperado);
    }   



    @Test
    void testTransferirDineroCuenta(){
        Cuenta cuenta1 = new Cuenta("Guadalupe", new BigDecimal("1000.12345"));
        Cuenta cuenta2 = new Cuenta("Guillermina", new BigDecimal("1000.12345"));

        Banco banco = new Banco("MartBank");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));
                                            //Le pedimos el saldo de la cuenta y lo hacemos String para comparar rapido
        assertEquals("500.12345", cuenta1.getSaldo().toPlainString());
        assertEquals("1500.12345", cuenta2.getSaldo().toPlainString());

    }
    

    @Test
    void testRelacionBancoCuentas(){
        Cuenta cuenta1 = new Cuenta("Guadalupe", new BigDecimal("1000.12345"));
        Cuenta cuenta2 = new Cuenta("Guillermina", new BigDecimal("1000.12345"));

        Banco banco = new Banco("BankMarto");
        try{
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        } catch (RuntimeException exception){
            exception.getMessage();
        }

        Mostrar("---------- Transferencia -----------");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));
                                            //Le pedimos el saldo de la cuenta y lo hacemos String para comparar rapido
        assertEquals("500.12345", cuenta1.getSaldo().toPlainString());
        assertEquals("1500.12345", cuenta2.getSaldo().toPlainString());

        Mostrar("------------ assertRelacionBanco -------------");
        assertEquals(2 , banco.getCuentas().size());

    }




}
