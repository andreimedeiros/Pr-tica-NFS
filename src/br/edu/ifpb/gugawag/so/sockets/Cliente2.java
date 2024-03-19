package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente2 {

    public static void main(String[] args) throws IOException {
        System.out.println("== Cliente ==");

        // Configurando o socket
        Socket socket = new Socket("127.0.0.1", 7001);
        // Pegando referência do canal de saída do socket
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // Pegando referência do canal de entrada do socket
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // Laço infinito do cliente
        while (true) {
            Scanner teclado = new Scanner(System.in);
            System.out.print("Operação (readdir, rename, create, remove): ");
            String operacao = teclado.nextLine();
            dos.writeUTF(operacao);
            String resposta = dis.readUTF();
            System.out.println("Servidor: " + resposta);
        }
    }
}
