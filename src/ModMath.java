import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * Created by User on 5/14/2016.
 */
public class ModMath {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String base, mod, exp, check;
        String argues[] = new String[2];
        String argues3[] = new String[3];
        boolean loop = true;
        while (loop){
            clearConsole();
            System.out.println("What would you like to do?");
            System.out.println("1: Mod Inverse\n" +
                                "2: Mod Exponentiation\n" +
                                "3: Prime Discrete Log\n" +
                                "4: Mod Multiply\n" +
                                "5: RSA Cracking\n" +
                                "6: ECC Discrete Log\n" +
                                "7: Group Test (Z_p^*)\n" +
                                "8: Exit");
            System.out.print(">");
            int choice;
            try{
                choice = Integer.parseInt(input.readLine());
            }catch (Exception e){
                choice = 0;
            }
            switch (choice){
                case 1:
                    System.out.print("Base: ");
                    base = input.readLine();
                    System.out.print("Mod: ");
                    mod = input.readLine();
                    argues[0] = base;
                    argues[1] = mod;
                    ModInv.main(argues);
                    break;
                case 2:
                    System.out.print("Base: ");
                    base = input.readLine();
                    System.out.print("Mod: ");
                    mod = input.readLine();
                    System.out.print("Exponent: ");
                    exp = input.readLine();
                    argues3[0] = base;
                    argues3[1] = mod;
                    argues3[2] = exp;
                    ModExp.main(argues3);
                    break;
                case 3:
                    System.out.print("Base: ");
                    base = input.readLine();
                    System.out.print("Mod: ");
                    mod = input.readLine();
                    System.out.print("Check: ");
                    check = input.readLine();
                    argues3[0] = base;
                    argues3[1] = mod;
                    argues3[2] = check;
                    GenDiscLog.main(argues3);
                    break;
                case 4:
                    System.out.print("Base1: ");
                    base = input.readLine();
                    System.out.print("Base2: ");
                    String base2 = input.readLine();
                    System.out.print("Mod: ");
                    mod = input.readLine();
                    BigInteger b1 = new BigInteger(base);
                    BigInteger b2 = new BigInteger(base2);
                    BigInteger modb = new BigInteger(mod);
                    System.out.println(b1+"*"+b2+"(mod "+modb+") = "+b1.multiply(b2).mod(modb));
                    break;
                case 5:
                    System.out.print("Public Key (e): ");
                    String e = input.readLine();
                    System.out.print("Public Key (n): ");
                    String n = input.readLine();
                    argues[0] = e;
                    argues[1] = n;
                    CrackRsa.main(argues);
                    break;
                case 6:
                    System.out.print("a: ");
                    BigInteger a = new BigInteger(input.readLine());
                    System.out.print("b: ");
                    BigInteger b = new BigInteger(input.readLine());
                    System.out.print("p: ");
                    BigInteger p = new BigInteger(input.readLine());
                    System.out.print("r_x: ");
                    BigInteger r_x = new BigInteger(input.readLine());
                    System.out.print("r_y: ");
                    BigInteger r_y = new BigInteger(input.readLine());
                    System.out.print("x: ");
                    BigInteger x = new BigInteger(input.readLine());
                    System.out.print("y: ");
                    BigInteger y = new BigInteger(input.readLine());
                    ECC.DISCRETELOG(a, b, p, r_x, r_y, x, y);
                    break;
                case 7:
                    ArrayList<String> list = new ArrayList<>();
                    String get;
                    do {
                        System.out.print("Input numbers (type mod to stop): ");
                        get = input.readLine();
                        list.add(get);
                    }while (!get.equals("mod"));
                    System.out.print("Enter modulus: ");
                    list.add(input.readLine());
                    String[] leargs = new String[list.size()];
                    for (int i = 0; i < leargs.length; i++) {
                        leargs[i] = list.get(i);
                    }
                    GroupTest.main(leargs);
                    break;
                case 8:
                    loop = false;
            }
            if (choice != 7){
                System.out.print("\nPress Enter to Continue....");
                input.readLine();
                System.out.println("\n");
            }
        }
    }

    private static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Ignore
        }
    }
}
