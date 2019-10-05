package co.devbeerloper.consumingrestserviceapp;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class CharacterActivity extends AppCompatActivity {

    private LinearLayout list;
    private TextView[] atributes=new TextView[8];
    public static final String URL_SWAPI = "https://swapi.co/api/";
    public String[]cualities=new String[8];
    private int index=1;
    public int n=0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character2);

        list=findViewById(R.id.atLayout);

        for (int i =0;i<atributes.length;i++){

            atributes[i] = new TextView(this);
            atributes[i].setTextSize(20);
            atributes[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(999, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            atributes[i].setLayoutParams(params);
            list.addView(atributes[i]);
        }
        callWebService("");
    }


    public void makeCall (View v) {
        callWebService("");
    }

    public void callWebService(String serviceEndPoint){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL urlService = new URL (URL_SWAPI + "people/"+index+"/" );
                    HttpsURLConnection connection =  (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();

                    if (connection.getResponseCode() == 200) {
                        // Success


                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object
                        for (int i =0 ; i<cualities.length;i++){
                            String key = jsonReader.nextName(); // Fetch the next key
                            String value = jsonReader.nextString();
                            cualities[i]=value;
                        }




                        atributes[0].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[0].setText("Nombre: "+cualities[0]);
                            }
                        });

                        atributes[1].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[1].setText("Altura: "+ cualities[1]);
                            }
                        });

                        atributes[2].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[2].setText("Peso: "+ cualities[2]);
                            }
                        });

                        atributes[3].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[3].setText("Color de cabello: "+ cualities[3]);
                            }
                        });

                        atributes[4].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[4].setText("Color de piel: "+ cualities[4]);
                            }
                        });

                        atributes[5].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[5].setText("Color de ojos: "+ cualities[5]);
                            }
                        });

                        atributes[6].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[6].setText("Cumpleaños: "+ cualities[6]);
                            }
                        });

                        atributes[7].post(new Runnable() {
                            @Override
                            public void run() {
                                atributes[7].setText("Genero: "+ cualities[7]);
                            }
                        });



                    } else {
                        // Error handling code goes here
                        Log.v("ERROR", "ERROR");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }// end callWebService()

    public void next(View view){
        index++;
        makeCall(view);
    }

    public void previous(View view){
        if(index>1){
            index--;
            makeCall(view);
        }

    }
}
