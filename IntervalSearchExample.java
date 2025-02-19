import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class IntervalSearchExample {

    public static void addInterval(TreeMap<Integer, List<Map.Entry<List<Integer>, String>>> map, List<Integer> interval, String data, int granularity) {
        int start = interval.get(0);
        int end = interval.get(1);
        int startBucket = getBucket(start, granularity);
        int endBucket = getBucket(end, granularity);

        for (int bucket = startBucket; bucket <= endBucket; bucket += granularity) {
            map.computeIfAbsent(bucket, k -> new ArrayList<>()).add(new AbstractMap.SimpleEntry<>(interval, data));
        }
    }

    public static List<String> query(TreeMap<Integer, List<Map.Entry<List<Integer>, String>>> map, int queryStart, int queryEnd, int granularity) {
        List<String> result = new ArrayList<>();
        int startBucket = getBucket(queryStart, granularity);
        int endBucket = getBucket(queryEnd, granularity);

        for (Map.Entry<Integer, List<Map.Entry<List<Integer>, String>>> entry : map.subMap(startBucket, true, endBucket, true).entrySet()) {
            for (Map.Entry<List<Integer>, String> intervalEntry : entry.getValue()) {
                List<Integer> interval = intervalEntry.getKey();
                if (intersects(interval, queryStart, queryEnd)) {
                    result.add(intervalEntry.getValue());
                }
            }
        }
        return result;
    }


    public static List<String> querySingleNumber(TreeMap<Integer, List<Map.Entry<List<Integer>, String>>> map, int queryPoint) {
        List<String> result = new ArrayList<>();

        // Retrieve all intervals with start points less than or equal to queryPoint
        for (Map.Entry<Integer, List<Map.Entry<List<Integer>, String>>> entry : map.headMap(queryPoint, true).entrySet()) {
            for (Map.Entry<List<Integer>, String> intervalEntry : entry.getValue()) {
                List<Integer> interval = intervalEntry.getKey();
                if (contains(interval, queryPoint)) {
                    result.add(intervalEntry.getValue());
                }
            }
        }
        return result;
    }

    private static boolean contains(List<Integer> interval, long queryPoint) {
        int intervalStart = interval.get(0);
        int intervalEnd = interval.get(1);
        return intervalStart <= queryPoint && intervalEnd >= queryPoint;
    }


    private static boolean intersects(List<Integer> interval, int queryStart, int queryEnd) {
        int intervalStart = interval.get(0);
        int intervalEnd = interval.get(1);
        return intervalStart <= queryEnd && intervalEnd >= queryStart;
    }

    private static int getBucket(int value, int granularity) {
        return (value / granularity) * granularity;
    }



    public static void main(String[] args) {
        // Initialize the TreeMap
        TreeMap<Integer, List<Map.Entry<List<Integer>, String>>> intervalMap = new TreeMap<>();
        int granularity = 1000;

        // Add intervals
        addInterval(intervalMap, Arrays.asList(353922, 353923), "CcAvenue8", granularity);
        addInterval(intervalMap, Arrays.asList(353600, 353608), "CcAvenue", granularity);
        addInterval(intervalMap, Arrays.asList(353610, 353700), "CcAvenue2", granularity);
        addInterval(intervalMap, Arrays.asList(353702, 353818), "CcAvenue3", granularity);
        addInterval(intervalMap, Arrays.asList(353821, 353899), "CcAvenue4", granularity);
        addInterval(intervalMap, Arrays.asList(353902, 353906), "CcAvenue5", granularity);
        addInterval(intervalMap, Arrays.asList(353912, 353912), "CcAvenue6", granularity);
        addInterval(intervalMap, Arrays.asList(100000, 200000), "CcAvenue Rupay2", granularity);
        addInterval(intervalMap, Arrays.asList(100400, 200000), "CcAvenue Rupay3", granularity);


        // Query intervals
        List<String> result = querySingleNumber(intervalMap, 100405);
        System.out.println("Intervals that contain with 100405: " + result);

        result = querySingleNumber(intervalMap, 353904);
        System.out.println("Intervals that contain with 353904: " + result);
    }
}