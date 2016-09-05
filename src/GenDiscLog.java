import java.math.BigInteger;

/**
 *
 * Created by User on 5/14/2016.
 */
public class GenDiscLog {

    public static void main(String[] args) {
        if (args.length != 3)usage();
        BigInteger base = new BigInteger(args[0]);
        BigInteger mod = new BigInteger(args[1]);
        BigInteger result = new BigInteger(args[2]);
        BigInteger exp = BigInteger.valueOf(2);
        while(true){
            ModExp foo = new ModExp(base, mod, exp, false);
            ModExp.RAISE();
            if (ModExp.result.equals(result)) break;
            exp = exp.add(BigInteger.ONE);
        }
        System.out.println("RESULTS\n======================");
        System.out.println(exp+" is the exponent.");
    }

    private static void usage(){
        System.err.println("Usage: java GenDiscLog <base> <mod> <result>");
        System.exit(1);
    }
}
