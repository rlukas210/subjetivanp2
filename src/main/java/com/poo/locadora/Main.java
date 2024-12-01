package com.poo.locadora;

import com.poo.locadora.util.HibernateUtil;
import com.poo.locadora.util.Menu;

public class Main {
    public static void main(String[] args) {
        // Inicia a conexão com o banco
        HibernateUtil.getSessionFactory().openSession();
        System.out.println("Conexão com o banco de dados estabelecida.");

        // Chama o menu principal
        Menu.MenuLocacao();

        HibernateUtil.getSessionFactory().close();
    }
}
