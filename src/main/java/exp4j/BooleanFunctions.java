package exp4j;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import sun.security.krb5.internal.crypto.Des;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Functions {

    public static Map<String, Operator> functions =
            Collections.unmodifiableMap(new HashMap<String, Operator>() {{
                put(Designation.NOT.value, not);
                put(Designation.XOR.value, xor);
                put(Designation.AND.value, and);
                put(Designation.OR.value, or);
            }});

    public enum Designation {
        NOT("not"),
        XOR("xor"),
        AND("and"),
        OR("or");

        public final String value;

        private Designation(String value) {
            this.value = value;
        }
    }

    public enum Precedence {
        NOT(4),
        XOR(3),
        AND(2),
        OR(1);

        public final Integer value;

        private Precedence(Integer value) {
            this.value = value;
        }
    }

    public static Operator not =
            new Operator(Designation.NOT.value, 1, false, Precedence.NOT.value) {
        @Override
        public double apply(double... args) {
            final int arg = (int) args[0];
            if (!isArgumentBoolean(arg)) {
                throw new IllegalArgumentException("Not boolean value of input");
            }
            return 1 - arg;
        }
    };

    public static Operator xor =
            new Operator(Designation.XOR.value, 2, false, Precedence.XOR.value) {
        @Override
        public double apply(double... args) {
            final int arg1 = (int) args[0];
            final int arg2 = (int) args[1];
            if (!isArgumentBoolean(arg1) || !isArgumentBoolean(arg2)) {
                throw new IllegalArgumentException("Not boolean value of input");
            }

            return arg1 ^ arg2;
        }
    };

    public static Operator and =
            new Operator(Designation.AND.value, 2, false, Precedence.AND.value) {
                @Override
                public double apply(double... args) {
                    final int arg1 = (int) args[0];
                    final int arg2 = (int) args[1];
                    if (!isArgumentBoolean(arg1) || !isArgumentBoolean(arg2)) {
                        throw new IllegalArgumentException("Not boolean value of input");
                    }

                    return arg1 & arg2;
                }
            };

    public static Operator or =
            new Operator(Designation.OR.value, 2, false, Precedence.OR.value) {
                @Override
                public double apply(double... args) {
                    final int arg1 = (int) args[0];
                    final int arg2 = (int) args[1];
                    if (!isArgumentBoolean(arg1) || !isArgumentBoolean(arg2)) {
                        throw new IllegalArgumentException("Not boolean value of input");
                    }

                    return arg1 & arg2;
                }
            };

    private static boolean isArgumentBoolean(int arg) {
        return arg == 1 || arg == 0;
    }

}
