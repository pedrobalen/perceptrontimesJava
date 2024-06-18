package perceptrontimes;


import java.util.List;
import java.util.Random;

public class Perceptron {
    public List<Ponto> amostras;
    public List<Integer> saidas;
    public double taxaAprendizado;
    public int geracoes;
    public int limiar;
    public int numeroAmostras;
    public double[] pesos;

    public Perceptron(List<Ponto> amostras, List<Integer> saidas, double taxaAprendizado, int geracoes, int limiar) {
        this.amostras = amostras;
        this.saidas = saidas;
        this.taxaAprendizado = taxaAprendizado;
        this.geracoes = geracoes;
        this.limiar = limiar;
        this.numeroAmostras = amostras.size();
        this.pesos = new double[3];
    }

    private int funcaoAtivacaoSignal(double soma) {
        return soma >= 0 ? 1 : -1;
    }

    public void treinar() {
        for (Ponto amostra : amostras) {
            amostra.limiar = this.limiar;
        }

        Random gerador = new Random();
        pesos[0] = limiar;
        pesos[1] = gerador.nextDouble();
        pesos[2] = gerador.nextDouble();

        int conta = 0;
        boolean aprendeu;
        double soma;
        int saidaGerada;

        while (true) {
            aprendeu = true;
            soma = 0;

            for (int i = 0; i < amostras.size(); i++) {
                Ponto amostra = amostras.get(i);
                soma = amostra.limiar * pesos[0] + amostra.x * pesos[1] + amostra.y * pesos[2];
                saidaGerada = funcaoAtivacaoSignal(soma);

                if (saidaGerada != this.saidas.get(i)) {
                    aprendeu = false;
                    double erro = this.saidas.get(i) - saidaGerada;
                    pesos[0] += this.taxaAprendizado * erro * amostra.limiar;
                    pesos[1] += this.taxaAprendizado * erro * amostra.x;
                    pesos[2] += this.taxaAprendizado * erro * amostra.y;
                }
            }

            conta++;

            if (aprendeu || conta > this.geracoes) {
                System.out.println("Gerações de treinamento: " + conta);
                break;
            }
        }
    }

    public void testar(Ponto amostra) {
        amostra.limiar = this.limiar;
        double soma = amostra.limiar * pesos[0] + amostra.x * pesos[1] + amostra.y * pesos[2];
        int saidaGerada = funcaoAtivacaoSignal(soma);

        if (saidaGerada == 1) {
            System.out.println("Classe: " + saidaGerada + " ou Time Azul");
        } else {
            System.out.println("Classe: " + saidaGerada + " ou Time Vermelho");
        }
    }
}
