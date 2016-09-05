import java.math.BigInteger;
import java.util.HashSet;

/**
 *
 * Created by User on 5/14/2016.
 */
public class GroupTest {

    private static HashSet<BigInteger> group = new HashSet<>();
    private static BigInteger mod;
    private static Boolean notFound = true;

    public static void main(String[] args) {
        if (args.length < 3) usage();
        group = new HashSet<>();
        int i = 0;
        while (!args[i].equalsIgnoreCase("mod")) {
            try {
                BigInteger n = new BigInteger(args[i]);
                if (group.contains(n)) notGroup("Element in set twice");
                group.add(n);
            } catch (Exception e) {
                System.err.println("BigInteger not passed as " + i + "th element");
                usage();
            }
            i++;
        }
        try{
            mod = new BigInteger(args[++i]);
        }catch (Exception e) {
            notGroup("Modulus given is too long");
        }

        boolean passed = true;
        try {
            testClosure();
        }catch (IllegalArgumentException iae){
            passed = false;
        }
        if (passed){
            System.out.println("Closure Property is checked");
            System.out.println("Associative Property is checked");
            System.out.println("Identity Property is checked");
            try {
                testInverses();
            }catch (IllegalArgumentException iae){
                passed = false;
            }
            if (passed){
                System.out.println("Inverse Property is checked");
                System.out.println("The passed set and mod form a group over multiplication");
            }
        }
    }

    private static void testClosure(){
        group.forEach(e1 -> group.forEach(e2 -> {
            BigInteger val = e1.multiply(e2).mod(mod);
            System.out.println(e1+" * "+e2+" (mod "+mod+") is "+val);
            if (!group.contains(val)) notGroup(e1 + " * " + e2 + " (mod "+mod+") is "+val+" and not in the set");
        }));
    }

    private static void testInverses(){
        group.forEach(e1 ->{
            group.forEach(e2 -> {
                BigInteger val = e1.multiply(e2).mod(mod);
                if (val.equals(e1)) notFound = false;
            });
            if (notFound) notGroup(e1+ " had no modular inverse within the set");
            notFound= true;
        });
    }


    private static void usage(){
        System.out.flush();
        System.err.println("Usage: java GroupTest n1 [n2] ... [ni] mod k");
        System.exit(1);
    }
    private static void notGroup(String reason){
        System.out.flush();
        System.err.println("Not a Group: "+reason);
        System.err.flush();
        throw new IllegalArgumentException("Not a Group: "+reason);
    }
}
