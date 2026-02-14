import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main app = new Main();
        MovieData data = new MovieData();
        try {
            data = app.loadMovieData("movies.csv");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean run = true;

        while(run){
            System.out.println("\nChoose an option:");
            System.out.println("1. Match by Movie index");
            System.out.println("2. Match by Preferences (0-5)");
            System.out.println("3. Exit");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    for (int i = 0; i < data.movies.size(); i++) {
                        System.out.println(i + ": " + data.movies.get(i).getTitle());
                    }
                    System.out.print("Select movie index: ");
                    int index = scanner.nextInt();

                    if (index >= 0 && index < data.movies.size()) {
                        double[] movieFeatures = data.movies.get(index).getFeatures();
                        app.printRecs(app.getRecommendations(movieFeatures, data));
                    } else {
                        System.out.println("Invalid index.");
                    }
                    break;

                case 2:
                    double[] userData = app.getUserData(data, scanner);
                    app.printRecs(app.getRecommendations(userData, data));
                    break;

                case 3:
                    run = false;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
        scanner.close();
    }

    void printRecs(Movie[] recommendations){
        System.out.println("Your recommendations:");
        for(Movie movie : recommendations){
            if (movie != null) {
                movie.ToString();
            }
        }
    }

    public MovieData loadMovieData(String fileName) throws IOException {
        MovieData data = new MovieData();
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String[] header = br.readLine().split(",");
        data.categoryNames = Arrays.copyOfRange(header, 1, header.length);

        String[] weightRow = br.readLine().split(",");
        data.weights = new double[weightRow.length - 1];
        for (int i = 1; i < weightRow.length; i++) {
            data.weights[i - 1] = Double.parseDouble(weightRow[i]);
        }

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            double[] feats = new double[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                feats[i - 1] = Double.parseDouble(parts[i]);
            }
            data.movies.add(new Movie(parts[0], feats));
        }
        br.close();
        return data;
    }

    public double[] getUserData(MovieData data, Scanner scanner) {
        String[] categories = data.categoryNames;
        double[] userData = new double[categories.length];

        for (int i = 0; i < categories.length; i++) {
            System.out.print("Enter your rating for " + categories[i] + "(0-5): ");
            double input = scanner.nextDouble();
            while (input < 0.0 || input > 5.0) {
                System.out.print("Invalid rating, try again: ");
                input = scanner.nextDouble();
            }
            userData[i] = input / 5.0;
        }
        return userData;
    }

    public Movie[] getRecommendations(double[] userData, MovieData data) {
        if (data.movies.isEmpty() || userData.length != data.movies.getFirst().getFeatures().length || data.movies.getFirst().getFeatures().length != data.weights.length) {
            throw new IllegalArgumentException("Vector lengths do not match or are empty");
        }
        Movie[] recommendations = new Movie[3];
        double[] multUserRec = multiply(data.weights, userData);
        for (Movie movie : data.movies) {
            double[] multFilmDat = multiply(data.weights, movie.getFeatures());
            movie.similarity = calculateSimilarity(multFilmDat, multUserRec);
        }
        data.movies.sort((a, b) -> Double.compare(b.similarity, a.similarity));
        for (int i = 0; i < data.movies.size() && i < 3; i++) {
            recommendations[i] = data.movies.get(i);
        }
        return recommendations;
    }



    public double[] multiply(double[] a, double[] b) {
        if (a.length != b.length || a.length == 0 || b.length == 0) {
            return new double[0];
        }
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * b[i];
        }
        return result;
    }

    public double calculateSimilarity(double[] a, double[] b){
    // formula = (sum(a[i}*b[i]))/(sqrt(sum(a[i]^2))*sqrt(sum(b[i]^2)))
        double sumMultAB = 0;
        double sumA = 0;
        double sumB = 0;
        for(int i=0;i<a.length;i++){
            sumMultAB+=a[i]*b[i];
            sumA+=a[i]*a[i];
            sumB+=b[i]*b[i];
        }
        return (sumMultAB)/(Math.sqrt(sumA)*Math.sqrt(sumB));
    }
}