import java.math.BigInteger;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HasiraPolynomialSolver {

    public static void main(String[] args) {
        String testCase1 = "{\"keys\":{\"n\":4,\"k\":3},\"1\":{\"base\":\"10\",\"value\":\"4\"},\"2\":{\"base\":\"2\",\"value\":\"111\"}}";

        // This is the verified, correct string for Test Case 2
        String testCase2 = "{\"keys\": {\"n\": 10,\"k\": 7},\"1\": {\"base\": \"6\",\"value\": \"13444211440455345511\"},\"2\": {\"base\": \"15\",\"value\": \"aed7015a346d635\"},\"3\": {\"base\": \"15\",\"value\": \"6aeeb69631c227c\"},\"4\": {\"base\": \"16\",\"value\": \"e1b5e05623d881f\"},\"5\": {\"base\": \"8\",\"value\": \"316034514573652620673\"},\"6\": {\"base\": \"3\",\"value\": \"2122212201122002221120200210011020220200\"}}";

        System.out.println("--- Running Test Case 1 ---");
        solveAndPrint(testCase1);

        System.out.println("\n\n--- Running Test Case 2 ---");
        solveAndPrint(testCase2);
    }

    public static void solveAndPrint(String jsonInput) {
        JSONObject data = new JSONObject(jsonInput);
        JSONObject keys = data.getJSONObject("keys");
        int k = keys.getInt("k");
        int degree = k - 1;

        List<BigInteger> roots = new ArrayList<>();
        for (int i = 1; i <= degree; i++) {
            String key = String.valueOf(i);
            JSONObject rootData = data.getJSONObject(key);
            int base = Integer.parseInt(rootData.getString("base"));
            String value = rootData.getString("value");
            roots.add(new BigInteger(value, base));
        }

        List<BigInteger> coeffs = new ArrayList<>();
        coeffs.add(BigInteger.ONE);

        for (BigInteger root : roots) {
            coeffs.add(BigInteger.ZERO);
            int size = coeffs.size();
            for (int j = size - 2; j >= 0; j--) {
                BigInteger oldCoeff = coeffs.get(j);
                BigInteger termToSubtract = root.multiply(oldCoeff);
                coeffs.set(j + 1, coeffs.get(j + 1).subtract(termToSubtract));
            }
        }

        System.out.println("\nCalculated Coefficients (for monic polynomial):");
        for (int i = 0; i < coeffs.size(); i++) {
            int power = degree - i;
            System.out.println("  Coefficient of x^" + power + ": " + coeffs.get(i).toString());
        }
    }
}