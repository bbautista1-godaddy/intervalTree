import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class IntervalTreeExample {
    public static void main(String[] args) {
        // Initialize the TreeMap to store intervals
        TreeMap<Long, List<Map.Entry<List<Long>, String>>> intervalMap = new TreeMap<>();

        // Add intervals
        addInterval(intervalMap, 100L, 200L, "Type1");
        addInterval(intervalMap, 150L, 250L, "Type2");
        addInterval(intervalMap, 300L, 400L, "Type3");
        addInterval(intervalMap, 1000L, 1100L, "Type4");
        addInterval(intervalMap, 1050L, 1150L, "Type5");
        addInterval(intervalMap, 1100L, 1200L, "Type6");

        // Query intervals
        List<String> result = queryIntervals(intervalMap, 175L, 225L);
        System.out.println("Intervals intersecting with [175, 225]: " + result);

        result = queryIntervals(intervalMap, 1000L, 1200L);
        System.out.println("Intervals intersecting with [1000, 1200]: " + result);
    }

    public static void addInterval(TreeMap<Long, List<Map.Entry<List<Long>, String>>> map, long start, long end, String data) {
        map.computeIfAbsent(start, k -> new ArrayList<>()).add(new AbstractMap.SimpleEntry<>(Arrays.asList(start, end), data));
    }

    public static List<String> queryIntervals(TreeMap<Long, List<Map.Entry<List<Long>, String>>> map, long queryStart, long queryEnd) {
        List<String> result = new ArrayList<>();

        // Retrieve all intervals with start points less than or equal to queryEnd
        for (Map.Entry<Long, List<Map.Entry<List<Long>, String>>> entry : map.headMap(queryEnd, true).entrySet()) {
            for (Map.Entry<List<Long>, String> intervalEntry : entry.getValue()) {
                List<Long> interval = intervalEntry.getKey();
                if (intersects(interval, queryStart, queryEnd)) {
                    result.add(intervalEntry.getValue());
                }
            }
        }
        return result;
    }

    private static boolean intersects(List<Long> interval, long queryStart, long queryEnd) {
        long intervalStart = interval.get(0);
        long intervalEnd = interval.get(1);
        return intervalStart <= queryEnd && intervalEnd >= queryStart;
    }
}