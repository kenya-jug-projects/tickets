package com.owino;
public class TitheCalculatorCore {
    public double getTithe(double income){
        double percentageTithe = 10.0; //10pp
        return (percentageTithe/100.0) * income;
    }
}
