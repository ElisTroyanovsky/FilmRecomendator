# FilmRecomendator 🎬

**FilmRecomendator** is a Java-based movie recommendation engine that utilizes a **Weighted Cosine Similarity** algorithm to suggest content. By parsing a structured CSV dataset containing movie features and attribute weights, the application calculates the geometric distance between vectors to identify the most relevant matches.

## 🚀 Key Features

* **Content-Based Filtering:** Recommendations based on movie attributes (Action, Comedy, Thriller, etc.), not user history.
* **Weighted Algorithm:** Takes into account specific feature weights defined in the dataset (e.g., "Cognitive Load" might be more important than "Movie Length").
* **Interactive Modes:**
    1.  **Match by Movie:** Select an existing movie to find similar titles.
    2.  **Match by Preferences:** Input your own ratings (0-5) for each category.

## 🧠 The Algorithm

The core of the engine is the Weighted Cosine Similarity formula:

$$
\text{Similarity} = \frac{\sum (A_i \cdot w_i) \cdot (B_i \cdot w_i)}{\sqrt{\sum (A_i \cdot w_i)^2} \cdot \sqrt{\sum (B_i \cdot w_i)^2}}
$$

Where $A$ and $B$ are the feature vectors, and $w$ is the weight vector.

## 🛠️ Usage

1.  Ensure `movies.csv` is in the root directory.
2.  Run the `Main` class.
3.  Follow the console instructions:

```text
Choose an option:
1. Match by Movie index
2. Match by Preferences (0-5)
3. Exit