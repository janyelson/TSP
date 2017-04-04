import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

		Scanner scan;
		int n;
		int[][] d;

		scan = new Scanner(System.in);
		String linha = "";

		linha = scan.nextLine();

		n = Integer.parseInt(linha.split("\\s+")[0]);

        int rotas = 1<<n;

        d = new int[n][];

       	for(int i = 0; i < n; i++) {

            linha = scan.nextLine();
       		d[i] = new int[n];

       		for(int j = 0; j < n; j++) {
       			d[i][j] = Integer.parseInt(linha.split("\\s+")[j]);
       		}

       	}
       	scan.close();
        Arvore arvore = new Arvore();
        No[] listNo = new No[1];

        No[] listAux = null;
        int indiceAux = 0;

        No primeiro = new No(0, (1<<n)-2);

        arvore.inserir(primeiro, null);
        listNo[0] = primeiro;
        int m = 1;

        for(int s = n-1; s > 1; --s) {

	       	int aux = m*s;
	       	listAux = new No[aux];
            indiceAux = 0;

            for(int k = 0; k < m; k++) {

                for(int i = 1; i < n; i++) {
                	
                    if( (listNo[k].s & (1 << i)) == (1 << i)) {
                        No novoNo = new No(i, listNo[k].s ^ (1 << i));
                        arvore.inserir(novoNo, listNo[k]);
                        listAux[indiceAux] = novoNo;
                        indiceAux++;
                    }
                }
            }
            m = aux;
            listNo = new No[aux];
            listNo = Arrays.copyOf(listAux, listAux.length);
        } 


        listAux = new No[listNo.length];
        indiceAux = 0;
        for(int k = 0; k < listNo.length; k++) {
        	for(int i = 1; i < n; i++) {
        		if( (listNo[k].s & (1 << i)) == (1 << i)) {

                    No novoNo = new No(i, 0);
                    arvore.inserir(novoNo, listNo[k]);
                    listAux[indiceAux] = novoNo;
                    indiceAux++;
                }
        	} 
        }

        listNo = listAux;
        m = listNo.length;
        indiceAux = 0;
        
        for(int s = 0; s < n-1; s++) {
        	int aux = m/(s+1);
        	listAux = new No[aux];
        	indiceAux = 0; 
        	for(int k = 0; k < m; k++) {
        		if(s == 0) {
        			listNo[k].min(d[listNo[k].i][0], "1 - ");

        		}
        		int ant = listNo[k].antecessor.i;
        		int dij = d[ant][listNo[k].i];
        		int indice = listNo[k].i;
        		listNo[k].antecessor.min(dij + listNo[k].valor, "" + listNo[k].caminho + "" + (listNo[k].i+1) + " - ");
        		if(k != 0) {
        			if(listNo[k].antecessor != listNo[k-1].antecessor) {
        				listAux[indiceAux] = listNo[k].antecessor;
        				indiceAux++;
        			}
        		}
        		else {
        			listAux[indiceAux] = listNo[k].antecessor;
        			indiceAux++;
        		}
        	}

        	m = aux;
        	listNo = listAux;
        }

        System.out.println("Valor: " + arvore.primeiro.valor);
        System.out.println("Caminho: " + arvore.primeiro.caminho + "1");
	}

    public static int fatorial(int n){

        int fat = 1;

        for(int i = 1; i <= n; i++) {
            fat *= i;
        }
        return fat;
    }

}

class Arvore {
    public No primeiro;

    public Arvore() {
        primeiro = null;
    }

    public void inserir(No novoNo, No pai) {

        if(primeiro == null) {
            primeiro = novoNo;
        }

        novoNo.antecessor = pai;
        if(pai != null) {
        	pai.sucessor = novoNo;
        }
    }
}

class No {
    public int i;
    public int s;
    public No antecessor;
    public No sucessor;
    public int valor;
    public String caminho;

    public No(int i, int s) {
        this.i = i;
        this.s = s;
        valor = Integer.MAX_VALUE;
        caminho = "";
    }

    public void min(int d, String i) {
    	if( d < valor ) {
    		valor = d;
    		caminho = i;
    	}
    }
}


