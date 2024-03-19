package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor2 {

    private static List<String> arquivos = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");

        // Inicializando lista de arquivos
        arquivos.add("arquivo1.txt");
        arquivos.add("arquivo2.txt");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // Pegando referência do canal de saída do socket
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // Pegando referência do canal de entrada do socket
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // Laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String operacao = dis.readUTF();
            System.out.println("Operação recebida: " + operacao);
            String resposta = executarOperacao(operacao);

            dos.writeUTF(resposta);
        }
    }

    private static String executarOperacao(String operacao) {
        String[] parts = operacao.split(" ");
        String comando = parts[0];
        switch (comando) {
            case "readdir":
                return listarArquivos();
            case "rename":
                String antigoNome = parts[1];
                String novoNome = parts[2];
                return renomearArquivo(antigoNome, novoNome);
            case "create":
                String nomeArquivo = parts[1];
                return criarArquivo(nomeArquivo);
            case "remove":
                String arquivoRemover = parts[1];
                return removerArquivo(arquivoRemover);
            default:
                return "comando";
        }
    }

    private static String listarArquivos() {
        StringBuilder lista = new StringBuilder();
        for (String arquivo : arquivos) {
            lista.append(arquivo).append("\n");
        }
        return lista.toString();
    }
    
    private static String renomearArquivo(String antigoNome, String novoNome) {
        if (arquivos.contains(antigoNome)) {
            arquivos.remove(antigoNome);
            arquivos.add(novoNome);
            return "arquivo renomeado.";
        } else {
            return "arquivo inexistete";
        }
    }

    private static String criarArquivo(String nomeArquivo) {
        if (arquivos.contains(nomeArquivo)) {
            return "arquivo já existe.";
        } else {
            arquivos.add(nomeArquivo);
            return "arquivo criado";
        }
    }

    private static String removerArquivo(String nomeArquivo) {
        if (arquivos.contains(nomeArquivo)) {
            arquivos.remove(nomeArquivo);
            return "arquivo removido com sucesso.";
        } else {
            return "arquivo não existe.";
        }
    }
}
