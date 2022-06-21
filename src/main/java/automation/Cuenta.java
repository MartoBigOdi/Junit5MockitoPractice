package automation;

import java.math.BigDecimal;

import exceptions.DineroInsuficienteException;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta [persona=" + persona + ", saldo=" + saldo + "]";
    }

    
    //Sobre escribimos EQUALS para nuestro uso especifico
    @Override
    public boolean equals(Object object){
        //Decimos si es null o de otra clase de objectos devolve "false"
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta c = (Cuenta) object; 
        //Mismo si nuestro objeto es null en sus props devolvemos "false"
        if(this.persona == null || this.saldo == null){
            return false;
        }

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }


    public void debito(BigDecimal monto){
          BigDecimal nuevoSaldo = this.saldo.subtract(monto);
          if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
                throw new DineroInsuficienteException("Dinero Insuficiente");
          }
          this.saldo = nuevoSaldo;
    }

    //Aca podriamos validar que no sea null el monto y lanzar una RuntimeException
    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

}
