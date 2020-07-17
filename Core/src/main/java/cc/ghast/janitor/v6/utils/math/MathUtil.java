package cc.ghast.artemis.v4.utils.maths;

import cc.ghast.artemis.v4.utils.lists.Tuple;
import cc.ghast.artemis.v4.utils.position.PlayerMovement;
import cc.ghast.artemis.v4.utils.position.PlayerPosition;
import cc.ghast.artemis.v4.utils.position.SimpleRotation;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Ghast
 * @since 17-Mar-20
 */
public class MathUtil {

    public static double delta(double a, double b) {
        return Math.abs(a - b);
    }

    public static boolean isUsingOptifine(SimpleRotation current, SimpleRotation previous) {
        float yawChange = (float) distanceBetweenAngles(current.getYaw(), previous.getYaw());
        float pitchChange = (float) distanceBetweenAngles(current.getPitch(), previous.getPitch());

        float yawWrapped = wrapAngleTo180(yawChange);
        float pitchWrapped = wrapAngleTo180(pitchChange);

        return pitchWrapped < 0.01;
    }

    public static boolean isUsingOptifine(PlayerMovement current, PlayerMovement previous) {
        float yawChange = (float) distanceBetweenAngles(current.getYaw(), previous.getYaw());
        float pitchChange = (float) distanceBetweenAngles(current.getPitch(), previous.getPitch());

        float yawWrapped = wrapAngleTo180(yawChange);
        float pitchWrapped = wrapAngleTo180(pitchChange);

        return pitchWrapped < 0.01;
    }

    public static Tuple<List<Double>, List<Double>> getOutliers(final Collection<? extends Number> collection) {
        final List<Double> values = new ArrayList<>();

        for (final Number number : collection) {
            values.add(number.doubleValue());
        }

        final double q1 = getMedian(values.subList(0, values.size() / 2));
        final double q3 = getMedian(values.subList(values.size() / 2, values.size()));

        final double iqr = Math.abs(q1 - q3);
        final double lowThreshold = q1 - 1.5 * iqr, highThreshold = q3 + 1.5 * iqr;

        final Tuple<List<Double>, List<Double>> tuple = new Tuple<>(new ArrayList<>(), new ArrayList<>());

        for (final Double value : values) {
            if (value < lowThreshold) {
                tuple.a().add(value);
            } else if (value > highThreshold) {
                tuple.b().add(value);
            }
        }

        return tuple;
    }

    private static double getMedian(final List<Double> data) {
        if (data.size() % 2 == 0) {
            return (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        } else {
            return data.get(data.size() / 2);
        }
    }

    public static float wrapAngleTo180(float value) {
        value = value % 360.0F;

        if (value >= 180.0F) {
            value -= 360.0F;
        }

        if (value < -180.0F) {
            value += 360.0F;
        }

        return value;
    }

    public static int getPotionLevel(final Player player, final PotionEffectType effect) {
        int effectId = effect.getId();
        return player.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().getId() == effectId).map(PotionEffect::getAmplifier).findAny().orElse(0) + 1;
    }

    public static double vectorDistance(final PlayerPosition from, final PlayerPosition to) {
        final Vector vectorFrom = from.toBukkitLocation().toVector();
        final Vector vectorTo = to.toBukkitLocation().toVector();

        vectorFrom.setX(0.0);
        vectorTo.setY(0.0);

        return vectorFrom.subtract(vectorTo).length();
    }

    public static double distanceBetweenAngles(float alpha, float beta) {
        float alphax = alpha % 360, betax = beta % 360;
        float delta = Math.abs(alphax - betax);
        return Math.abs(Math.min(360.0 - delta, delta));
    }

    public static double distanceBetweenAngles(double alpha, double beta) {
        double alphax = alpha % 360, betax = beta % 360;
        double delta = Math.abs(alphax - betax);
        return Math.abs(Math.min(360.0 - delta, delta));
    }

    public static double getDistanceBetweenAngles360(double alpha, double beta) {
        double abs = Math.abs(alpha % 360.0 - beta % 360.0);
        return Math.abs(Math.min(360.0 - abs, abs));
    }

    public static double getDistanceBetweenAngles360Raw(double alpha, double beta) {
        return Math.abs(alpha % 360.0 - beta % 360.0);
    }

    public static double roundToPlace(double value, int places) {
        double multiplier = Math.pow(10, places);
        return Math.round(value * multiplier) / multiplier;
    }

    public static long gcd(long x, long y) {
        long gcd = 1;

        for (int i = 1; i <= x && i <= y; ++i) {
            // Checks if i is factor of both integers
            if (x % i == 0 && y % i == 0) gcd = i;
        }
        return gcd;
    }

    public static double mostFrequent(double[] arr) {

        // Insert all elements in hash
        Map<Double, Double> hp =
                new HashMap<Double, Double>();

        for (double key : arr) {
            if (hp.containsKey(key)) {
                double freq = hp.get(key);
                freq++;
                hp.put(key, freq);
            } else {
                hp.put(key, 1d);
            }
        }

        // find max frequency.
        double max_count = 0, res = -1;

        for (Map.Entry<Double, Double> val : hp.entrySet()) {
            if (max_count < val.getValue()) {
                res = val.getKey();
                max_count = val.getValue();
            }
        }

        return res;
    }


    public static double mode(double[] a) {
        double maxValue = 0, maxCount = 0;
        int i, j;
        for (i = 0; i < a.length; ++i) {
            int count = 0;
            for (j = 0; j < a.length; ++j) {
                if (a[j] == a[i])
                    ++count;
            }

            if (count > maxCount) {
                maxCount = count;
                maxValue = a[i];
            }
        }
        return maxValue;
    }


    public static boolean minesOrMore(int x, int y, int z) {
        return x > y - z && x < y + z;
    }

    public static boolean minesOrMore(int x, int y, double z) {
        return x > y - z && x < y + z;
    }

    public static boolean minesOrMore(double x, double y, double z) {
        return x > y - z && x < y + z;
    }


    public static double doubleDecimal(double i, int decimal) {
        try {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(decimal);
            df.setMinimumFractionDigits(0);
            df.setDecimalSeparatorAlwaysShown(false);
            return Double.parseDouble(df.format(i).replace(",", "."));
        } catch (Exception e) {
            return i;
        }
    }


    public static float floatDecimal(double i, int decimal) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(decimal);
        df.setMinimumFractionDigits(0);
        df.setDecimalSeparatorAlwaysShown(false);
        return Float.parseFloat(df.format(i).replace(",", "."));
    }


    public static long getSumLong(List<Long> longs) {
        long sum = 0;
        for (long l : longs) {
            sum += l;
        }
        return sum;
    }

    public static double trim(int degree, double d) {
        String format = "#.#";

        for (int i = 1; i < degree; i++) {
            format = format + "#";
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.parseDouble(twoDForm.format(d).replace(",", "."));
    }

    public static int hightestPing(long ping, int keepAlivePing, double transactionPing) {
        int highestPing = 0;
        if (ping > highestPing) {
            highestPing = (int) ping;
        }
        if (keepAlivePing > highestPing) {
            highestPing = keepAlivePing;
        }
        if (transactionPing > highestPing) {
            highestPing = (int) transactionPing;
        }
        return highestPing;
    }

    public static double angleOf(double minX, double minZ, double maxX, double maxZ) {
        // NOTE: Remember that most math has the Y axis as positive above the X.
        // However, for screens we have Y as positive below. For this reason,
        // the Y values are inverted to get the expected results.
        final double deltaY = (minZ - maxZ);
        final double deltaX = (maxX - minX);
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return (result < 0) ? (360d + result) : result;
    }

    public static double doPythagoras(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static double round(double value, int places) {
        try {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        } catch (NumberFormatException e) {
            return value;
        }
    }

    public static double getFluctuation(double[] array) {
        double max = 0;
        double min = Double.MAX_VALUE;
        double sum = 0;

        for (double i : array) {
            sum += i;
            if (i > max) max = i;
            if (i < min) min = i;
        }

        double average = sum / array.length;
        // example: 75 - ((75 - 35) / 2) = 75 - (40 / 2) = 75 - 20 = 55
        double median = max - ((max - min) / 2);
        double range = max - min;
        return (average / 50) / (median / 50);
    }

    public static double getStandardDeviation(final List<? extends Number> list) {
        final double average = list.stream().mapToDouble(Number::doubleValue).average().orElse(0.0);

        double stdDeviation = 0.0;

        for (final Number number : list) {
            stdDeviation += Math.pow(number.doubleValue() - average, 2);
        }

        return Math.sqrt(stdDeviation /= list.size());
    }

    public static double getStandardDeviation(final Deque<? extends Number> deque) {
        final double average = deque.stream().mapToDouble(Number::doubleValue).average().orElse(0.0);

        double stdDeviation = 0.0;

        for (final Number number : deque) {
            stdDeviation += Math.pow(number.doubleValue() - average, 2);
        }

        return Math.sqrt(stdDeviation /= deque.size());
    }

    public static double getStandardDeviation(final LinkedList<? extends Number> list) {
        final double average = list.stream().mapToDouble(Number::doubleValue).average().orElse(0.0);

        double stdDeviation = 0.0;

        for (final Number number : list) {
            stdDeviation += Math.pow(number.doubleValue() - average, 2);
        }

        return Math.sqrt(stdDeviation /= list.size());
    }

    public static List<Float> skipValues(double count, double min, double max){
        List<Float> floats = new ArrayList<>();
        for (float x = (float) min; x <= max; x += count){
            floats.add(x);
        }
        return floats;
    }
}
