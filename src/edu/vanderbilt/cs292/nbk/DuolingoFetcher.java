package edu.vanderbilt.cs292.nbk;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;

public class DuolingoFetcher {

    private static final String BASE_WORD_URL = "https://www.duolingo.com/words?page=%d&sort_by=word"
            + "&desc=false&_=" + System.currentTimeMillis();

    private AsyncHttpClient mClient = new AsyncHttpClient();

    private List<Cookie> mCookies;

    public DuolingoFetcher(String user, String pw) throws IOException, InterruptedException,
            ExecutionException {
        Future<Response> f = mClient.preparePut(
                "https://www.duolingo.com/login?login=" + user + "&password=" + pw).execute();
        Response r = f.get();
        mCookies = r.getCookies();

    }

    private void addCookies(BoundRequestBuilder b) {
        for (Cookie c : mCookies) {
            b.addCookie(c);
        }
    }

    public List<WordData> getWordData() {
        List<WordData> words = new LinkedList<WordData>();
        int page = 0;
        try {
            while (true) {
                page++;
                String pageStr = String.format(BASE_WORD_URL, page);
                BoundRequestBuilder b = mClient.prepareGet(pageStr);
                addCookies(b);
                Future<Response> f = b.execute();
                Response r = f.get();
                String body = r.getResponseBody();
                System.out.println(body);
                JSONTokener tkn = new JSONTokener(body);
                JSONObject json = new JSONObject(tkn);

                JSONArray vocab = json.getJSONArray("vocab");
                if (vocab.length() == 0) {
                    break;
                } else {
                    for (int i = 0; i < vocab.length(); i++) {
                        JSONObject wordData = vocab.getJSONObject(i);
                        JSONArray formsData = wordData.getJSONArray("forms_data");
                        String language = wordData.getString("language");
                        for (int j = 0; j < formsData.length(); j++) {
                            JSONObject form = formsData.getJSONObject(j);
                            String word = form.getString("surface_form");
                            String pos = form.getString("pos_key"); // part of
                                                                    // speech
                            double str = form.getDouble("strength");
                            words.add(new WordData(word, language, pos, str));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return words;
    }

}
