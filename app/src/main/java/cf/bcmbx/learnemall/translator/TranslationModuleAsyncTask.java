package cf.bcmbx.learnemall.translator;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cf.bcmbx.learnemall.BuildConfig;
import cf.bcmbx.learnemall.R;
import cf.bcmbx.learnemall.model.Word;

public class TranslationModuleAsyncTask extends AsyncTask<String, Void, String> {
    private ProgressDialog pDialog;
    private Context mContext;
    private Word mWordToTranslate;
    private String mResult = null;
    private EditText mTranslationEditText;

    public TranslationModuleAsyncTask(Context c, Word word, EditText translation) {
        mContext = c;
        mWordToTranslate = word;
        mTranslationEditText = translation;
    }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage(mContext.getString(R.string.wait));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... items) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String json;
            final String TRANSLATOR_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
            final String KEY_PARAM = "key";
            final String WORD_PARAM = "text";
            final String LANG_PARAM = "lang";
            final String TRANSLATION_LANGS_PARAM = items[1] + "-" + items[0];

            Uri builtUri = Uri.parse(TRANSLATOR_BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM, BuildConfig.TRANSLATOR_API_KEY)
                    .appendQueryParameter(WORD_PARAM, mWordToTranslate.getWord())
                    .appendQueryParameter(LANG_PARAM, TRANSLATION_LANGS_PARAM)
                    .build();
            try {
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    json = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    json = null;
                }
                json = buffer.toString();
            } catch (IOException e) {
                json = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                JSONObject jsonObj = new JSONObject(json);
                JSONArray textArray = jsonObj.getJSONArray("text");
                mResult = textArray.getString(0);
                Log.d("tag", textArray.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            mWordToTranslate.setTranslation(result);
            mTranslationEditText.setText(result);
        }
    }
