package model;
//É implementada apenas pelas classes que possuem saldo e método de recarga, para que possa manipulá-las.
public interface ICComercial {
    public double getSaldo();
    public double recarga(double valor);
}