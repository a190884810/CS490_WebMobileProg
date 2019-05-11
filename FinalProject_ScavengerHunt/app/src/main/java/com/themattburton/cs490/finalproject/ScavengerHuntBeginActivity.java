package com.themattburton.cs490.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class ScavengerHuntBeginActivity extends AppCompatActivity {

    private ListView cardListView;
    private SearchWordCardAdapter adapter;
    int TAKE_PHOTO_CODE = 0;
    ImageView userImage;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int NUMBER_OF_HUNT_WORDS = 5;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean cameraPermissionAccepted = false;
    private String [] permissions = {Manifest.permission.CAMERA};
    private String responseJson;
    private Uri image;
    private String imageFilename;
    private File currentImageFile;
    private File currentImageFileJpg;
    private ScavengerHuntBeginActivity scavAct;
    List<String> mainWordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scavenger_hunt_begin);

        scavAct = ScavengerHuntBeginActivity.this;
//        cameraPermissionAccepted = ContextCompat.checkSelfPermission(ScavengerHuntBeginActivity.this,
//                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        userImage = findViewById(R.id.photo_view);
        cardListView = findViewById(R.id.searchWordsListView);
        adapter = new SearchWordCardAdapter(this);
        cardListView.setAdapter(adapter);

        if (userImage.getDrawable() == null) {
            int randomInt = new Random().nextInt(9);
            if (randomInt % 2 == 0) {
                userImage.setImageResource(R.drawable.oh_comeon_picard);
            } else {
                userImage.setImageResource(R.drawable.ohcomeonalreadyarnold);
            }
        }

        String[] words = getResources().getStringArray(R.array.searchWords);
        mainWordsList = new ArrayList(Arrays.asList(words));
        Collections.shuffle(mainWordsList);
        //adapter.addAll(mainWordsList);

        int currentHuntWordsSize = mainWordsList.size() < NUMBER_OF_HUNT_WORDS ? mainWordsList.size() : NUMBER_OF_HUNT_WORDS;

        for (int i = 0; i < currentHuntWordsSize; i++) {
            adapter.add(new SearchWordCardModel(mainWordsList.get(i).toString()));
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //TODO: restore user's main list of non-found words
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //TODO: save user's main list of non-found words

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        //TODO: save all the UI things
        super.onPause();
    }

    @Override
    protected void onResume() {
        //TODO: restore all the UI things
        super.onResume();
    }

    public void callCamera(View v) {
//        if (!cameraPermissionAccepted) {
//            ActivityCompat.requestPermissions(ScavengerHuntBeginActivity.this, permissions, REQUEST_CAMERA_PERMISSION);
//        }
//        if (cameraPermissionAccepted) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                //if (currentImageFile != null && currentImageFile.exists()) currentImageFile.delete();
                currentImageFile = new File(Environment.getExternalStorageDirectory(), "currentImage.bmp");
                image = Uri.fromFile(currentImageFile);
                imageFilename = currentImageFile.toString();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
//            }
            } else {
                postToastMessage("Can't take photo without permission!");
            }
    }

    //If the photo is captured then set the image view to the photo captured.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            if (true){ //data != null) {
                //image = data.getData();
                //userImage.setImageURI(image);
                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                    userImage.setImageBitmap(bmp);
                    //if(currentImageFileJpg != null && currentImageFileJpg.exists()) currentImageFileJpg.delete();
                    currentImageFileJpg = new File(Environment.getExternalStorageDirectory(), "currentImage.jpg");
                    FileOutputStream outputStream = new FileOutputStream(currentImageFileJpg);
                    bmp.compress(Bitmap.CompressFormat.JPEG,90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            //userImage.setImageBitmap(photo);
            Log.d("CameraDemo", "Pic saved");
            processImageForHunt(currentImageFileJpg);
            postToastMessage("Thinking!!! Please wait!!!");
        }
    }

    private void processImageForHunt(File photo) {
        OkHttpClient httpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://centralus.api.cognitive.microsoft.com/vision/v1.0/analyze").newBuilder();
        urlBuilder.setQueryParameter("visualFeatures", "Tags");
        urlBuilder.setQueryParameter("language", "en");

        String url = urlBuilder.build().toString();

        MediaType OCTET_STREAM = MediaType.parse("application/octet-stream");

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(OCTET_STREAM, photo ))
                .header("Content-Type", "application/octet-stream")
                .header("Ocp-Apim-Subscription-Key", getResources().getString(R.string.subscription_key)).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Something bad happened with request " + response);
                    }
                    responseJson = responseBody.string();
                    System.out.println(responseJson);
                    if (responseJson != null && !responseJson.isEmpty()) {
                        updateHunt(responseJson);
                    } else {
                        postToastMessage("No results this time; try again!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Response response = httpClient.newCall(request).execute();
        //System.out.println(response.toString());

    }

    private void updateHunt(String responseJson) throws JSONException {
        int successCount = 0;
        //List<String> returnedTags = new ArrayList<String>();
        JSONObject responseJsonObj = new JSONObject(responseJson);
        JSONArray responseJsonArray = responseJsonObj.getJSONArray("tags");
        for (int i = 0; i < responseJsonArray.length(); i++) {
            JSONObject currentTagObj = responseJsonArray.getJSONObject(i);
            final String name = currentTagObj.getString("name");
            System.out.println("tag: " + name);
            for (int j = 0; j < adapter.getCount(); j++) { //cardListView.getCount()
                final SearchWordCardModel card = adapter.getItem(j);
                System.out.println("card word: " + card.getSearchWord());
                if (card.isFound()) {
                    successCount++;
                    continue;
                } else if (name.toLowerCase().contains(card.getSearchWord().toLowerCase())) {
                    System.out.println("match on " + name);
                    successCount++;

                    final int finalCount = successCount;
                    scavAct.runOnUiThread(new Runnable() {
                        public void run() {
                            card.setFound(true);
                            adapter.notifyDataSetChanged();
                            postToastMessage("You matched " + name + "!!! \nNICE!!!");
                            if (finalCount == NUMBER_OF_HUNT_WORDS) {
                                postToastMessage("YOU FOUND ALL THE WORDS!!!  REALLY ACCEPTABLE JOB!!!");
                                userImage.setImageResource(R.drawable.great_job_you_rock);
                                setNewSearchWords();
                            }
                        }
                    });
                    /*new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            card.setSearchWord(name + " => FOUND!");
                        }
                    });*/
                    //adapter.remove(card);
                    //adapter.add(new SearchWordCardModel(name + " FOUND!  BADASS!!!"));
                    //adapter.notifyDataSetChanged();
                    //cardListView.getChildAt(j).setBackgroundColor(Color.GREEN);
                    //adapter..getView(j).setBackgroundColor(Color.GREEN);
                    //break;
                }

            }

            //cardListView.getAdapter().getItem(0);

        }
    }

    private void setNewSearchWords() {
        for (int j = 0; j < adapter.getCount(); j++) {
            SearchWordCardModel card = adapter.getItem(j);
            System.out.println("Trying to remove: " + card.getSearchWord() + " from " + mainWordsList.toString());
            mainWordsList.remove(mainWordsList.indexOf(card.getSearchWord()));
        }
        adapter.clear();
        int currentHuntWordsSize = mainWordsList.size() < NUMBER_OF_HUNT_WORDS ? mainWordsList.size() : NUMBER_OF_HUNT_WORDS;

        if (currentHuntWordsSize == 0) {
            userImage.setImageResource(R.drawable.false_you_did_an_awesome_job);
            postToastMessage("WINNER WINNER CHICKEN DINNER!!!  GAME OVER MAN!!!");
            //TODO: maybe reset mainWordsList back to the full list of search words, or send Stellar-based tokens to user wallet
        } else {
            for (int i = 0; i < currentHuntWordsSize; i++) {
                adapter.add(new SearchWordCardModel(mainWordsList.get(i)));
            }
            postToastMessage("Find these new words!");
        }
    }

    public void postToastMessage(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

  /*  public void redirectToHome(View v) {
        Intent redirect = new Intent(ScavengerHuntBeginActivity.this, MainActivity.class);
        startActivity(redirect);
    }*/

}
