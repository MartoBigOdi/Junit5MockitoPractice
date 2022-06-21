package models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import automation.Banco;
import automation.Cuenta;
import exceptions.DineroInsuficienteException;

class CuentaTest {

    Cuenta cuenta;
    Banco banco;
    Cuenta cuenta1;
    Cuenta cuenta2;


    //Estos siempre on STATIC
    @BeforeAll
    static void beforeAll(){
        System.out.println("Inicializando pruebas");
    }

    //Estos siempre on STATIC
    @AfterAll
    static void AfterAll(){
        System.out.println("Finalizando pruebas");
    }



    @BeforeEach
    void initMetodoTest(){
        cuenta = new Cuenta("Marto", new BigDecimal("1000.23424"));
        cuenta1 = new Cuenta("Guadalupe", new BigDecimal("1000.12345"));
        cuenta2 = new Cuenta("Guillermina", new BigDecimal("1000.12345"));
        banco = new Banco("BankMarto");
    }


    @BeforeEach
    void sysoMostrarInicioMetodo() {
        System.out.println("---------------------------- Inicia Test ----------------------------");
    }


    @AfterEach
    void sysoMostrarFinalMetodo() {
        System.out.println("---------------------------- Paso Test -------------------------------");
    }


    protected static void mostrar(String mensaje) {
        System.out.println(mensaje);
    }


    @Test
    @DisplayName("Probando nombre de cuenta")
    void TestNombreCuenta() {

        // cuenta.setPersona("Marto");
        String esperado = "Marto";
        String real = cuenta.getPersona();

        //Mensajes de Error, Los pasamos con una expresion Lambda para ser mas eficientes los metodos
        //de esta forma solo se instancian cuando sea necesario su uzo. 
        assertNotNull(real, () -> "La cuenta no puede ser Null");
        assertEquals(esperado, real, () ->  " El nombre de la cuenta no es el que se esperaba");
        assertTrue(real.equals("Marto"), () ->  "Nombre cuenta esperada debe ser igual al real");

    }


    @Test
    @DisplayName("Probando saldo de cuenta")
    void testSaldoCuenta() {
        // Convertimos el BigDecimal en double para comparar con el double que le
        // pasamos de
        // Expected
        assertEquals(1000.23424, cuenta.getSaldo().doubleValue(), () -> "El valor no es como lo esperado");
        // Dos formas de probar la misma premisa
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0, () -> "Saldo no puede ser menor a 0");
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0 , () -> "Saldo no puede ser menor a 0");

    }


    @Test
    @DisplayName("Probando saldo de cuenta")
    void testReferenciaCuenta() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("1123.3642"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("1123.3642"));

        // assertNotEquals(cuenta1, cuenta2, "No son iguales");
        assertEquals(cuenta1, cuenta2, () -> "No son iguales las cuentas");
    }


    @Test
    @DisplayName("Probando debito de cuenta")
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo(), () -> "No puede ser Null");
        assertEquals(900, cuenta.getSaldo().intValue(),() -> "No es igual al esperado");
        // Volvemos el valor del saldo en String para comparar
        assertEquals("900.23424", cuenta.getSaldo().toPlainString());
    }


    @Test
    @DisplayName("Probando credito de cuenta")
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo(), () -> "No puede ser Null el saldo");
        assertEquals(1100, cuenta.getSaldo().intValue(), () -> "No es igual al esperado");
        // Volvemos el valor del saldo en String para comparar
        assertEquals("1100.23424", cuenta.getSaldo().toPlainString(), () -> "No es igual al esperado");
    }


    @Test
    @DisplayName("Probando dinero insuficiente de cuenta")
    void testDineroInsuficienteExceptions() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1001));
        });

        String real = exception.getMessage();
        String msjEsperado = "Dinero Insuficiente";

        System.out.println(real);
        assertEquals(real, msjEsperado, () -> "Mensaje de la Exception no es el esperado");
    }


    @Test
    @DisplayName("Probando transferencia de cuenta")
    void testTransferirDineroCuenta() {

        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));
        // Le pedimos el saldo de la cuenta y lo hacemos String para comparar rapido
        assertEquals("500.12345", cuenta1.getSaldo().toPlainString(), () -> "No es el mismo valor");
        assertEquals("1500.12345", cuenta2.getSaldo().toPlainString(), () -> "No es el mismo valor");
    }


    @Test
    @Disabled
    @DisplayName("Probando relacion entre las cuenta y el banco con assertAll")
    void testRelacionBancoCuentas() {
       
        try {
            banco.addCuenta(cuenta1);
            banco.addCuenta(cuenta2);
        } catch (RuntimeException exception) {
            exception.getMessage();
        }

        mostrar("---------- Transferencia -----------");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));

        assertAll(() -> {
            //Le pedimos el saldo de la cuenta y lo hacemos String para comparar rapido
            assertEquals("500.12345", cuenta1.getSaldo().toPlainString(), () -> "No es el mismo valor");
            assertEquals("1500.12345", cuenta2.getSaldo().toPlainString(), () -> "No es el mismo valor");
        }, () -> {
            mostrar("------------ assertRelacionBanco -------------");
            assertEquals(2, banco.getCuentas().size(), () -> "No es el mismo valor");
        }, () -> {
            mostrar("------------ AssertNameBank -------------");
            assertEquals("BankMarto", cuenta1.getBanco().getNombre(), () -> "No es el mismo valor");
        }, () -> {
            mostrar("------------ AssertCuentaDatos -------------");
            // Estamos recorriendo la lista del banco, con stream modelamos los datos y con
            // filter hacemos equals a lo buscado.
            assertEquals("Guadalupe", banco.getCuentas().stream()
                    .filter(
                            c -> c.getPersona()
                                    .equals("Guadalupe"))
                    .findFirst()
                    .get()
                    .getPersona(), () -> "No es el mismo Nombre");
        }, () -> {
            // Esto es lo mismo que arriba pero mas sensillo de entender, con la operacion
            // lambda hacemos la operacion para la
            // condicion
            assertTrue(banco.getCuentas().stream()
                    .anyMatch(
                            c -> c.getPersona()
                                    .equals("Guillermina")), () -> "No es el mismo Nombre");
        });

    }


    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    @DisplayName("Probando OS Mac o Linux")
    void testSoloLinuMac(){
        System.out.println("Ejecutando pruebas en Mac O Linux");
    }


    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows(){
        System.out.println("Ejecutando pruebas en Windows");
    }


    //Esto sirve para verificar si el sistema que nos corre los test es el apropiado, 
    //o simplemente para saber las propertirs del sistema
    @Test
    @DisplayName("Para mostrar las properties system")
    @Disabled
    void imprimirSystemProperties(){
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
    }

}
