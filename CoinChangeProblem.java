package test4;

/**
 *
 * @author lapec
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CoinChangeProblem {
    private int[] tabpoidsdispo;
    private int poidsvaleur;
    private int[] res;
    private int poidmini;
    private boolean solved;

    public CoinChangeProblem(int[] poids, int choix) {
        this.tabpoidsdispo = poids;
        this.poidsvaleur = choix;
        res = new int[poids.length];
        poidmini = 0;
        resolution();
    }

    private void resolution() {
        //tableau de stockage des résultats
        int[] tabresultat = new int[poidsvaleur + 1];
        //array to determine c
        int[] s = new int[poidsvaleur + 1];
        //initilisation du tableau
        for (int i = 0; i <= poidsvaleur; i++) {
            tabresultat[i] = Integer.MAX_VALUE - 1;
        }
        tabresultat[0] = 0;
        for (int i = 0; i < tabpoidsdispo.length; i++) {
            for (int j = 1; j <= poidsvaleur; j++) {
                
                if (j - tabpoidsdispo[i] >= 0) {
                    //on verifier dans le tableau s'il n'y a pas une meilleur valeur
                    if (tabresultat[j] > 1 + tabresultat[j - tabpoidsdispo[i]]) {
                        tabresultat[j] = 1 + tabresultat[j - tabpoidsdispo[i]];
                        //update array for computing c
                        s[j] = i;
                    }
                }
            }

        }
        //array of coins
        poidmini = tabresultat[poidsvaleur];
        int temp = poidsvaleur;
        while (temp > 0) {
            int denom = s[temp];
            res[denom]++;
            temp -= tabpoidsdispo[denom];
        }
        solved = true;
    }

    public int[] minCoinArray() {
        return res;
    }

   public int minCoins() {
        return poidmini;
    }
    
     public static int[] minCoinChange(int[] v, int A) {
        //array to hold results
        int[] r = new int[A + 1];
        //array to determine c
        int[] s = new int[A + 1];
        //initialize array
        for (int i = 0; i <= A; i++) {
            r[i] = Integer.MAX_VALUE - 1;
        }
        r[0] = 0;
        for (int i = 0; i < v.length; i++) {
            for (int j = 1; j <= A; j++) {
                //see if we can add coin of denom v[i]
                if (j - v[i] >= 0) {
                    //check to see if better
                    if (r[j] > 1 + r[j - v[i]]) {
                        r[j] = 1 + r[j - v[i]];
                        //update array for computing c
                        s[j] = i;
                    }
                }
            }

        }
        
        //array of coins
        int[] c = new int[v.length];
        while (A > 0) {
            int denom = s[A];
            c[denom]++;
            A -= v[denom];
        }
        return c;
    }
    
      public static int minCoins(int[] c) {
        int count = 0;
        for (int n : c) {
            count += n;
        }
        return count;
    }

    public void printProblem() {
        System.out.println("Tableau des poids disponible: " + Arrays.toString(tabpoidsdispo));
        System.out.println("L'objet de poids: " + poidsvaleur);
       String str = "";
        boolean first = true;
        for (int i = 0; i < res.length; i++) {
            if (res[i] != 0) {
                int count = res[i];
                if (first) {
                    first = false;
                    str += tabpoidsdispo[i];
                    count--;
                }

                for (int j = 0; j < count; j++) {
                    str += " + " + tabpoidsdispo[i];
                }
            }
        }
        String sortie = "L'objet de poids de " + poidsvaleur + " kg peut etre pesé avec le nombre minimale de "+ poidmini +"\n" 
                + "L'objet de poids de " + poidsvaleur + " kg peut etre pesé de " + res + " façon différentes\n";
            
        System.out.println(sortie);
    }

     public static void main(String[] args) {
         
       List<CoinChangeProblem> problems = readFile("amount.txt");
        int num = 1;
        for (CoinChangeProblem p : problems) {
            int[] c = p.minCoinArray();
            int m = p.minCoins();
            System.out.println("Problem #" + num + ":");
            p.printProblem();
            System.out.println("--------------------------------------");
            num++;
        }
     
    }

    public static List<CoinChangeProblem> readFile(String filename) {
        BufferedReader br = null;
        List<CoinChangeProblem> problems = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while (line != null) {
                String[] sVals = line.split(" ");
                int[] vals = new int[sVals.length];
                for (int i = 0; i < sVals.length; i++) {
                    vals[i] = Integer.parseInt(sVals[i]);
                }

                int amt = Integer.parseInt(br.readLine());
                problems.add(new CoinChangeProblem(vals, amt));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return problems;
    }

}
