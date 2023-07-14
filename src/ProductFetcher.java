import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductFetcher {
    private static final String API_URL = "https://dummyjson.com/product";
    private static final String X_CONS_HEADER = "X-Cons";
    private static final String USER_KEY_HEADER = "user_key";
    private static final String X_CONS_VALUE = "1234567";
    private static final String USER_KEY_VALUE = "faY738sH";

    public static <JSONArray> void main(String[] args) {
        try {
            // Mengambil data JSON dari URL
            String jsonData = fetchJsonData();

            // Mengolah data JSON
            JSONArray products = new JSONArray(jsonData);
            int n = products.length();

            // Mengubah data JSON menjadi array produk
            UAS.Product[] productArray = new UAS.Product[n];
            for (int i = 0; i < n; i++) {
                JSONObject productJson = products.getJSONObject(i);
                String name = productJson.getString("name");
                int rating = productJson.getInt("rating");
                productArray[i] = new UAS.Product(name, rating);
            }

            // Mengurutkan produk dengan menggunakan Selection Sort
            selectionSort(productArray);

            // Menampilkan produk yang diurutkan
            for (UAS.Product product : productArray) {
                System.out.println(product.getName() + " - Rating: " + product.getRating());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String fetchJsonData() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty(X_CONS_HEADER, X_CONS_VALUE);
        connection.setRequestProperty(USER_KEY_HEADER, USER_KEY_VALUE);
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Failed to fetch JSON data from the server. Response code: " + responseCode);
        }
    }

    private static void selectionSort(UAS.Product[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].getRating() < arr[minIndex].getRating()) {
                    minIndex = j;
                }
            }
            // Tukar elemen terkecil dengan elemen pada indeks i
            UAS.Product temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}

class Product {
    private String name;
    private int rating;

    public Product(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }
}

