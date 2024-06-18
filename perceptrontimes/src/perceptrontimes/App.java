package perceptrontimes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class App{
    public static void main(String[] args) {
        System.out.println("Exemplo de RNA Perceptron para classificação de equipes");
        List<Ponto> amostras = new ArrayList<>();
        List<Integer> saidas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\pedro\\eclipse-workspace\\perceptrontimes\\src\\amostras_saidas_problemaTimes.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(";");
                double x = Double.parseDouble(valores[0]);
                double y = Double.parseDouble(valores[1]);
                int saida = Integer.parseInt(valores[2]);
                amostras.add(new Ponto(x, y));
                saidas.add(saida);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double taxaAprendizado = 0.1;
        int geracoes = 1000;
        int limiar = 1;
        Perceptron p = new Perceptron(amostras, saidas, taxaAprendizado, geracoes, limiar);

        p.treinar();

        Scanner scanner = new Scanner(System.in);
        String op;
        do {
            System.out.print("\n\nInforme valor para x (-1 a 1): ");
            double x = Double.parseDouble(scanner.nextLine());
            System.out.print("Informe valor para y (-1 a 1): ");
            double y = Double.parseDouble(scanner.nextLine());

            p.testar(new Ponto(x, y));
            System.out.print("1 - Sair: ");
            op = scanner.nextLine();
        } while (!op.equals("1"));

        scanner.close();
    }
}
