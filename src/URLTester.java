package src;// Illustrates how to do an HTTP GET

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.*;

public class URLTester {


    public static void main(String[] args) throws IOException {
        String urlString = "https://www.wunderground.com/weather/us/ca/san-francisco/37.778862, -122.504807";
        URL u = new URL(urlString);
        URLConnection connection = u.openConnection();
        HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
        int code = httpsConnection.getResponseCode();
        String message = httpsConnection.getResponseMessage();
        System.out.println(code + " " + message);
        if (code != HttpURLConnection.HTTP_OK) {
            return;
        }
        InputStream instream = connection.getInputStream();
        Scanner in = new Scanner(instream);
        ArrayList<String> sList = new ArrayList<String>();
        while (in.hasNext()) {
            sList.add(in.next());
        }
        List<String> charList = sList
                .stream()
                .filter(x -> x.chars()
                        .allMatch(Character::isLetter) && x != null)
                .collect(Collectors.toList());
        System.out.println(charList);
    }
}