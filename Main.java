import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static int map[][];
    public static int dp[][];
    public static int aux[][];
    public static int n;
    public static int routes;

	public static void main(String args[]) {

    	int i,j;

    	Scanner scan = new Scanner(System.in);
		String linha = "";

		linha = scan.nextLine();

		n = Integer.parseInt(linha);
        if (n == 0) return;

        routes = (int) java.lang.Math.pow(2,n);
        

        dp = new int[n][];
        aux = new int[n][];
        map = new int[n][];

        for(i = 0; i < n; i++) {
        	dp[i] = new int[routes];
            aux[i] = new int[routes];
        }

        for(i = 0; i < n; i++) {
            map[i] = new int[n];
        }   

        for(i = 0; i < n; i++) {
            linha = scan.nextLine();
       		for(j = 0; j < n; j++) {
       			map[i][j] = Integer.parseInt(linha.split("\\s+")[j]);
       		}
       	}

        for(i = 0;i < n; i++){
            for(j = 0; j < routes; j++) {
                dp[i][j] = aux[i][j] = -1; 
            }
        }
        for(i=0; i < n; i++){
            dp[i][0] = map[i][0];
        }
        int result = compute(0,routes-2);
        System.out.println("Valor: " + result);
        System.out.print("Circuito: 0");
        getpath(0, routes-2);
        System.out.println(" - 0");   	
	}

    public static void getpath(int start,int set)
    {
        if(aux[start][set] == -1) return;

        int x = aux[start][set];
        int mask = (routes -1 ) - ( 1 << x );
        int masked = set & mask;

        System.out.print(" - " + x + "");
        getpath(x, masked);
    }

    public static int compute(int start,int set)
    {   
        int masked, mask, temp, i;
        int result = Integer.MAX_VALUE;

        if(dp[start][set]!=-1) return dp[start][set];
    
        for(i=0;i<n;i++)
        {   
            mask = (routes-1)-(1<<i);
            masked = set & mask;
            if(masked!=set)
            {   
                temp = map[start][i] + compute(i, masked);
                if(temp < result) {
                    aux[start][set] = i;
                    result = temp;
                }
            }
        }
        return dp[start][set] = result;
    }
}