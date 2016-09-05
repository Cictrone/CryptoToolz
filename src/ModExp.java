import java.math.BigInteger;
/**
 *
 * Created by User on 5/14/2016.
 */
public class ModExp {

    private static BigInteger base, mod, exp;
    public static BigInteger result;
    private static boolean print = true;
    private static final BigInteger TWO =  BigInteger.valueOf(2);

    public ModExp(BigInteger base, BigInteger mod, BigInteger exp, boolean print){
        ModExp.base = base;
        ModExp.mod = mod;
        ModExp.exp = exp;
        ModExp.print = print;
    }

    public static void main(String[] args) {
        if (args.length != 3) usage();
        base = new BigInteger(args[0]);
        mod = new BigInteger(args[1]);
        exp = new BigInteger(args[2]);
        RAISE();
        System.out.println();
        System.out.println("RESULT\n======================");
        System.out.println(base + "^"+exp+ " = "+result);
    }

    public static void RAISE(){
        if (print)
        System.out.println("Powers of Two Exp\n======================");
        if (exp.equals(BigInteger.ONE)){
            if (print)System.out.println(base + "^"+exp+ " = "+base);
            return;
        }
        BigInteger[] gArray = new BigInteger[exp.bitLength()];
        gArray[0] = base;
        result = base.multiply(base).mod(mod);
        if (exp.equals(TWO)){
            if (print)System.out.println(base + "^"+exp+ " = "+result);
            return;
        }
        if (print)System.out.println(base + "^2 = "+result);
        gArray[1] = result;
        for (int i = 2;i < exp.bitLength();i++ ) {
            result =  result.multiply(result).mod(mod);
            if (print)System.out.println(base + "^"+Math.round(Math.pow(2, i))+ " = "+result);
            gArray[i] = result;
        }
        if (print)System.out.println();
        if (print)System.out.println();
        int lowestBit = exp.getLowestSetBit();
        if (print)System.out.println("Addition Round\n======================");

        result = gArray[lowestBit++];
        long q = Math.round(Math.pow(2, lowestBit));
        for (int i = lowestBit;i < exp.bitLength() ; i++) {
            if(exp.testBit (i)) {
                long j = q+Math.round(Math.pow(2, i));
                result = gArray[i].multiply(result).mod(mod);
                if (print)System.out.println(base + "^"+j+" = "+result);
                q = j;
            }
        }
    }

    private static void usage(){
        System.err.println("Usage: java ModExp <base> <mod> <exp>");
        System.exit(1);
    }
}
