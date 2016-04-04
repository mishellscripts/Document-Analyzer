/**
 * Used by: Michelle Lai, Michelle Song
 * 
 * Simple class to hold a piece of data and its count. The class has package
 * access so that the various implementations of DataCounter can access its
 * contents, but not client code.
 * 
 * @param <E> type of data whose count we are recording.
 */
public class DataCount<String> {
    /**
     * The data element whose count we are recording.
     */
    String data;

    /**
     * The count for the data element.
     */
    int count;

    /**
     * Create a new data count.
     * 
     * @param data the data element whose count we are recording.
     * @param count the count for the data element.
     */
    DataCount(String data, int count) {
        this.data = data;
        this.count = count;
    }
}