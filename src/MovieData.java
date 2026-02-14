import java.util.ArrayList;
import java.util.List;

public class MovieData {
    public List<Movie> movies = new ArrayList<>();
    public double[] weights;
    public String[] categoryNames;

    void print() {
        for (Movie movie : movies) {
            movie.print();
            System.out.print(", ");
        }
        System.out.println();
        for (double weight : weights) {
            System.out.print(weight);
            System.out.print(", ");
        }
        System.out.println();
        for (String name : categoryNames) {
            System.out.print(name);
            System.out.print(", ");
        }
        System.out.println();
    }
}