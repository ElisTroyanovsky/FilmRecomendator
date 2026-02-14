public class Movie {
    private String title;
    private double[] features;
    public double similarity;

    public Movie(String title, double[] features) {
        this.title = title;
        this.features = features;
    }

    public void print() {
        System.out.print(title + ": ");
        for (double feature : features) {
            System.out.print(feature);
            System.out.print(", ");
        }
        System.out.println();
    }
    public String getTitle() {
        return title;
    }
    public double[] getFeatures() {
        return features;
    }
    public void ToString(){
        System.out.println(title + ": " + String.format("%.2f", similarity));
    }
}
