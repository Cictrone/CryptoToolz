import java.math.BigInteger;

/**
 *
 * Created by User on 5/14/2016.
 */
public class ModInv {

    private static BigInteger mod, result;

    public static void main(String[] args) {
        if (args.length != 2) usage();
        BigInteger base = new BigInteger(args[0]);
        mod = new BigInteger(args[1]);
        if (!base.gcd(mod).equals(BigInteger.ONE)){
            System.err.println("No inverse exists.");
            System.exit(1);
        }
        if (mod.isProbablePrime(100)){
            System.out.println("Exponent is Prime, Using Fermat's Little Theorem...");
            new ModExp(base, mod,mod.subtract(BigInteger.valueOf(2)), true);
            ModExp.RAISE();
            System.out.println();
            System.out.println("RESULTS\n======================");
            System.out.println(ModExp.result+" is the inverse.");
        }
        System.out.println("\nUsing Extended Euclidean Algorithm...");
        ExtendedEuclideanAlgorithm(mod, base, BigInteger.ZERO, BigInteger.ONE);
        System.out.println("RESULTS\n======================");
        System.out.println(result+" is the inverse.");
    }

    private static void ExtendedEuclideanAlgorithm(BigInteger x, BigInteger y, BigInteger t2, BigInteger t1){
        BigInteger r, q, t;
        while(true){
            r = x.mod(y);
            if (r.equals(BigInteger.ZERO)) break;
            q = x.subtract(r).divide(y);
            t = t2.subtract(t1.multiply(q));
            x = y;
            y = r;
            t2 = t1;
            t1 =  t;
        }
        if (t1.compareTo(BigInteger.ZERO) == -1){
            t1 = t1.add(mod);
        }
        result = t1;
    }

    private static void usage(){
        System.err.println("Usage: java ModInv <base> <mod>");
        System.exit(1);
    }
}
