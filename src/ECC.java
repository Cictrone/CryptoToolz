import java.math.BigInteger;
import java.util.Scanner;

public class ECC {
    private static BigInteger a, b, p, g_x, g_y, e_0, e_1, r_x, r_y;
    private static final BigInteger THREE =  BigInteger.valueOf(3);
    private static final BigInteger FOUR =  BigInteger.valueOf(4);
    private static final BigInteger TWENTY_SEVEN =  BigInteger.valueOf(27);
    private static final BigInteger TWO =  BigInteger.valueOf(2);

    public static void main(String[] args){
        if(args.length != 5 && args.length != 3) usage();
        try{
            a = new BigInteger(args[0]);
            b = new BigInteger(args[1]);
            p = new BigInteger(args[2]);
            if (args.length == 5){
                g_x = new BigInteger(args[3]);
                g_y = new BigInteger(args[4]);
            }
        }catch(Exception e){
            System.err.println("Not all Arguments are Decimal Numbers.");
            System.exit(1);
        }
        if (!isCurve(a, b, p)){
            System.err.println("No Elliptic Curve Group exists for the given constants");
            System.err.println("(4(a^2) + 27(b^2))(mod p) = 0, which it should not.");
            System.exit(1);
        }else{
            System.out.println("Elliptic Curve: y^2 = x^3 + "+a+"x + "+b);
            System.out.println("An Elliptic Curve Group exists for the given constants!");
            System.out.println("(4(a^2) + 27(b^2))(mod p) = not Zero!\n");
        }
        if (args.length == 5){
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.print("First Person's Exponent: ");
                e_0 = new BigInteger(scanner.nextLine());
                if (e_0.compareTo(BigInteger.ZERO) != 1){
                    System.err.println("Exponents must be > 0");
                    System.exit(1);
                }
                System.out.print("Second Person's Exponent: ");
                e_1 = new BigInteger(scanner.nextLine());
                if(e_1.compareTo(BigInteger.ZERO) != 1){
                    System.err.println("Exponents must be > 0");
                    System.exit(1);
                }
            }
            catch(Exception e){
                System.err.println("The Given Exponents were not both Decimal Numbers.");
                System.exit(1);
            }


            RAISE(e_0);
            BigInteger x_0 = r_x;
            BigInteger y_0 = r_y;
            System.out.printf("First Person's Point: (%s, %s)\n", x_0, y_0);

            RAISE(e_1);
            BigInteger x_1 = r_x;
            BigInteger y_1 = r_y;
            System.out.printf("Second Person's Point: (%s, %s)\n", x_1, y_1);

            g_x = r_x;
            g_y = r_y;
            RAISE(e_0);
            System.out.printf("Shared Secret Group Element is (%s, %s)\n", r_x, r_y);
            System.exit(0);
        }
        BigInteger order = BigInteger.ONE;
        boolean exists = false;
        System.out.println("GENERATING GROUP ELEMENTS\n=========================");
        for (BigInteger x = BigInteger.ZERO; x.compareTo(p)!= 0; x = x.add(BigInteger.ONE)){
            for (BigInteger y = BigInteger.ZERO; y.compareTo(p)!= 0; y = y.add(BigInteger.ONE)){
                BigInteger LHS = y.multiply(y).mod(p);
                BigInteger RHS = x.multiply(x).multiply(x).add(a.multiply(x)).add(b).mod(p);
                if (LHS.compareTo(RHS) == 0){
                    order = order.add(BigInteger.ONE);
                    exists= true;
                    System.out.print("(" + x + "," + y + ")");
                }
            }
            if (exists){
                System.out.println();
                exists = false;
            }
        }
        System.out.println("and O (the point at infinity).");
        System.out.println("Group Order is "+order);
    }

    private static Boolean isCurve(BigInteger a, BigInteger b, BigInteger p){
        BigInteger test = FOUR.multiply(a.multiply(a.multiply(a))).add(TWENTY_SEVEN.multiply(b.multiply(b)));
        return !test.mod(p).equals(BigInteger.ZERO);
    }

    public static void DISCRETELOG(BigInteger a, BigInteger b, BigInteger p, BigInteger p_x, BigInteger p_y, BigInteger x_n, BigInteger y_n){
        System.out.println("Elliptic Curve: y^2 = x^3 + "+a+"x + "+b);
        System.out.println("Solving for n: ("+p_x+","+p_y+") = n("+x_n+","+y_n+")");
        BigInteger exp = BigInteger.valueOf(2);
        BigInteger guess_x = x_n;
        BigInteger guess_y = y_n;
        ECC.a = a;
        ECC.b = b;
        ECC.p = p;
        if (guess_x.compareTo(p_x)==0 && guess_y.compareTo(p_y)==0){
            System.out.println("n is 1");
            return;
        }
        while (true){
            ADD(guess_x, guess_y, x_n, y_n);
            guess_x = r_x;
            guess_y = r_y;
            if (guess_x.compareTo(p_x)==0 && guess_y.compareTo(p_y)==0)break;
            exp = exp.add(BigInteger.ONE);
        }
        System.out.println("RESULTS\n======================");
        System.out.println("n is "+exp);
    }



    private static void ADD(BigInteger x_0, BigInteger y_0, BigInteger x_1, BigInteger y_1){
        if (x_0 == null){
            r_x = x_1;
            r_y = y_1;
            return;
        }
        if(x_1 == null){
            r_x = x_0;
            r_y = y_0;
            return;
        }
        BigInteger lambda;
        if(!x_0.equals(x_1)){
            lambda = y_0.subtract(y_1).mod(p).multiply(x_0.subtract(x_1).modInverse(p)).mod(p);
            r_x = lambda.multiply(lambda).subtract(x_0).subtract(x_1).mod(p);
            r_y = x_1.subtract(r_x).multiply(lambda).subtract(y_1).mod(p);
            return;
        }
        if(!y_0.equals(y_1) || y_1.equals(BigInteger.ZERO)){
            r_x = null;
            r_y = null;
            return;
        }
        lambda = x_0.multiply(x_0).multiply(THREE).add(a).mod(p).multiply(y_0.multiply(TWO).modInverse(p)).mod(p);
        r_x = lambda.multiply(lambda).subtract(x_0).subtract(x_1).mod(p);
        r_y = x_1.subtract(r_x).multiply(lambda).subtract(y_1).mod(p);
        }

    private static void RAISE(BigInteger exp){
        if (exp.equals(BigInteger.ONE)){
            r_x = g_x;
            r_y = g_y;
            return;
        }
        BigInteger[] gArray = new BigInteger[exp.bitLength()*2];
        gArray[0] = g_x;
        gArray[1] = g_y;
        ADD(g_x, g_y, g_x, g_y);
        gArray[2] = r_x;
        gArray[3] = r_y;
        for (int i = 2;i < exp.bitLength() ;i++ ) {
            ADD(r_x, r_y, r_x, r_y);
            gArray[2*i] = r_x;
            gArray[(2*i)+1] = r_y;
        }

        int lowestBit = exp.getLowestSetBit();
        r_x = gArray[2*lowestBit];
        r_y = gArray[(2*lowestBit)+1];
        lowestBit++;
        for (int i = lowestBit;i < exp.bitLength() ; i++) {
            if(exp.testBit (i)) {
                ADD(r_x, r_y, gArray[2*i], gArray[(2*i)+1]);
            }
        }
    }

    private static void usage(){
        System.err.println("java ECC <a> <b> <p> [<g_x> <g_y>]");
        System.exit(1);
    }
 }
