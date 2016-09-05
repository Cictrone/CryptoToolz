import java.math.BigInteger;

/**
 * CrackRsa implements the Pollard's Rho algorithm in order to
 * find the prime factors of the integer n, which is the second
 * command line argument and find the corresponding private key.
 *
 * @author Nicholas O'Brien
 * @version 21-Apr-2016
 */
public class CrackRsa {

    private static BigInteger e, n, d, c, p, q;


    /**
     * Will run the Pollard's Rho prime factorization algorithm
     * and then find the modular inverse (with respect to the euler
     * totient of n) of e and print it along with the factors of n (p & q)
     *
     * @param args The first arg is e the second is n; together they make
     *             up the public key.
     */
    public static void main(String[] args) {
        if (args.length != 2) usage();
        try{
            e = new BigInteger(args[0]);
            n = new BigInteger(args[1]);
        }catch (Exception e){
            System.err.println("The passed args were not in decimal");
            System.exit(1);
        }
        c = BigInteger.ONE;          // constant
        p = BigInteger.ZERO;         // factor1
        while (BigInteger.ZERO.compareTo(p) == 0){ // in case Pollard's rho fails
            PollardsRho();
        }
        getPrivateKey();
        if (p.compareTo(q) < 0){     // true for most cases...
            System.out.println("First Prime Factor: "+p);
            System.out.println("Second Prime Factor: "+q);
        }else {
            System.out.println("First Prime Factor: "+q);
            System.out.println("Second Prime Factor: "+p);
        }
        System.out.println("Euler Totient: "+p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        System.out.println("Private Exponent: "+d);
    }

    /**
     * Solves the following mathematical equations:
     *
     * phi(n) = (p-1)(q-1)
     * d = e^-1(mod phi(n))
     */
    private static void getPrivateKey(){
        BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        d = e.modInverse(totient);
    }

    /**
     * This function implements the Pollard's Rho integer
     * factorization algorithm. A static value named c is used
     * as the constant which is part of the polynomial function
     * which each possible factor goes through in every iteration.
     * In the event of Pollards Rho failing the c value is incremented
     * and the algorithm is tried again.
     */
    private static void PollardsRho(){
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(2);
        BigInteger d = BigInteger.valueOf(1);
        while (d.compareTo(BigInteger.ONE) == 0){
            a = a.multiply(a).add(c).mod(n); // a <- (a^2 + c)(mod n)
            b = b.multiply(b).add(c).mod(n); // b <- (b^2 + c)(mod n)
            b = b.multiply(b).add(c).mod(n); // b <- (b^2 + c)(mod n)
            d = a.subtract(b).abs().gcd(n);  // d <- gcd(|a-b|, n)
        }
        if (d.compareTo(n) == 0){
            p = BigInteger.ZERO;
            c = c.add(BigInteger.ONE);
        }
        p = d;
        q = n.divide(p);
    }

    private static void usage(){
        System.err.println("java CrackRsa <e> <n>");
        System.exit(1);
    }
}