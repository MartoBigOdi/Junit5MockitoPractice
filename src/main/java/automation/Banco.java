package automation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Banco {
    private String nombre;
    private List <Cuenta> cuentas;

    public Banco(String nombre){
        this.nombre = nombre;
        this.cuentas = new ArrayList <Cuenta> (); 
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }


    public void addCuenta(Cuenta c){
        if(c == null){
            throw new RuntimeErrorException (null, "No puede ser Null la cuenta");
        }
        this.cuentas.add(c);
        //Asi creamos la relacion de cuenta al Banco
        c.setBanco(this);
    }



    public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto){
        try{
        origen.debito(monto);
        destino.credito(monto);
            System.out.println("Se hizo una transferencia por: $" + monto);
        }
         catch(RuntimeException exception){
            System.out.println(exception.getMessage());
         }
    }
}
