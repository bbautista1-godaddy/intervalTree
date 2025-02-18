
public class Main {
    public static void main(String[] args) {
        IntervalTree<String> it = new IntervalTree<>();
        it.addInterval(100000, 200000, "CcAvenue Rupay2");
        it.addInterval(250010, 250010, "DLocal2");
        it.addInterval(234567, 234568, "DLocal1");

        it.addInterval(353922, 353923, "CcAvenue8");
        it.addInterval(353600, 353608, "CcAvenue");
        it.addInterval(353610, 353700, "CcAvenue2");
        it.addInterval(353702, 353818, "CcAvenue3");
        it.addInterval(353821, 353899, "CcAvenue4");
        it.addInterval(353902, 353906, "CcAvenue5");
        it.addInterval(353912, 353912, "CcAvenue6");
        
        List<String> result1 = it.get(353915);
        List<String> result2 = it.get(234568);


        System.out.print(it.toString());
    }
      
}
