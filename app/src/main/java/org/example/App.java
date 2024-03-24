/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {
    public static void main(String[] args) {
        // Creamos una cuenta bancaria compartida
        CuentaBancaria cuenta = new CuentaBancaria(1000);

        // Creamos varios clientes que realizarán operaciones en la cuenta
        Cliente cliente1 = new Cliente(cuenta, "Cliente 1");
        Cliente cliente2 = new Cliente(cuenta, "Cliente 2");

        // Creamos hilos para los clientes
        Thread hiloCliente1 = new Thread(cliente1);
        Thread hiloCliente2 = new Thread(cliente2);

        // Iniciamos los hilos
        hiloCliente1.start();
        hiloCliente2.start();
    }

    static class CuentaBancaria {
        private int saldo;
        private Lock lock;

        public CuentaBancaria(int saldoInicial) {
            this.saldo = saldoInicial;
            this.lock = new ReentrantLock();
        }

        // Método para realizar un depósito
        public void depositar(int cantidad) {
            lock.lock();
            try {
                saldo += cantidad;
                System.out.println("Depósito realizado. Saldo actual: " + saldo);
            } finally {
                lock.unlock();
            }
        }

        // Método para realizar un retiro
        public void retirar(int cantidad) {
            lock.lock();
            try {
                if (saldo >= cantidad) {
                    saldo -= cantidad;
                    System.out.println("Retiro realizado. Saldo actual: " + saldo);
                } else {
                    System.out.println("Fondos insuficientes para realizar el retiro.");
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class Cliente implements Runnable {
        private CuentaBancaria cuenta;

        public Cliente(CuentaBancaria cuenta, String nombre) {
            this.cuenta = cuenta;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                // Simulamos operaciones de depósito y retiro
                cuenta.depositar(100);
                cuenta.retirar(50);
            }
        }
    }

}
