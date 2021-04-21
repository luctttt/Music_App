package com.example.music_app.homeFragment;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music_app.room.SongDataBase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SongViewModel extends ViewModel {
    // lưu dataBase phuc vu online , set su thay doi cua du lieu bang livedata
    List<SongModel> dsSong = new ArrayList<>();

    MutableLiveData<List<SongModel>> liveData;
    Context context;


    public SongViewModel(Context context) {
        this.context = context;
        liveData = new MutableLiveData<>();


        new GetData().execute("https://chiasenhac.vn/");
    }


    public MutableLiveData<List<SongModel>> getListSongLiveData() {
        return liveData;
    }

    public class GetData extends AsyncTask<String, Void, List<SongModel>> {

        @Override
        protected List<SongModel> doInBackground(String... strings) {
            String linkCrawl = strings[0];

            try {

                Document document = Jsoup.connect(linkCrawl).get();

                List<SongModel> models = new ArrayList<>();

                for (Element d : document.select("div.row").get(2).select("div.row").get(1).select("div.col")) {

                    String linkImg = d.select("div.card-header").attr("style");

                    String nameSong = d.select("div.card-header").select("a").first().attr("title");

                    String artistSong = d.select("p.card-text").text();

                    String linkSong = d.select("h3.card-title").select("a").attr("href");

                    linkSong = linkCrawl.substring(0, linkCrawl.length() - 1) + linkSong;

                    Document doc1 = Jsoup.connect(linkSong).get();
                    Elements els = doc1.select("div.tab-content");
                    String linkMusic = "";
                    Elements elements = els.first().select("ul.list-unstyled").first().select("a.download_item");

                    for (Element e : elements) {
                        String link = e.attr("href");
                        if (link.contains(".mp3")) {
                            linkMusic = link;
                            break;
                        }
                        //Log.d("TAG", "doInBackground: " + link);
                    }

                    if (linkImg != null) {
                        linkImg = linkImg.substring(linkImg.indexOf("http"), linkImg.lastIndexOf(")"));
                    }

                    models.add(new SongModel(nameSong.trim(), artistSong, linkSong, linkImg, linkMusic ));

//                    Log.d("TAG", "doInBackground: " + linkImg);
//                    Log.d("TAG", "doInBackground: " + nameSong);
//                    Log.d("TAG", "doInBackground: " + artistSong);
//                    Log.d("TAG", "doInBackground: " + linkSong);
                }

                for (Element d : document.select("div.row").get(4).select("div.row").select("div.col")) {

                    String linkImg = d.select("div.card-header").attr("style");

                    String nameSong = d.select("div.card-header").select("a").first().attr("title");

                    String artistSong = d.select("p.card-text").text();

                    String linkSong = d.select("h3.card-title").select("a").attr("href");

                    linkSong = linkCrawl.substring(0, linkCrawl.length() - 1) + linkSong;

                    Document doc1 = Jsoup.connect(linkSong).get();
                    Elements els = doc1.select("div.tab-content");
                    String linkMusic = "";
                    Elements elements = els.first().select("ul.list-unstyled").first().select("a.download_item");

                    for (Element e : elements) {
                        String link = e.attr("href");
                        if (link.contains(".mp3")) {
                            linkMusic = link;
                            break;
                        }
                    }

                    if (linkImg != null) {
                        linkImg = linkImg.substring(linkImg.indexOf("http"), linkImg.lastIndexOf(")"));
                    }

                    models.add(new SongModel(nameSong.trim(), artistSong, linkSong, linkImg, linkMusic ));

//                    Log.d("TAG", "doInBackground: " + linkImg);
//                    Log.d("TAG", "doInBackground: " + nameSong);
//                    Log.d("TAG", "doInBackground: " + artistSong);
//                    Log.d("TAG", "doInBackground: " + linkSong);
                }

                return models;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<SongModel> songModels) {      // sau khi hoan thanh thi roi vao ham nay
            super.onPostExecute(songModels);

            if (songModels != null) {
                dsSong.addAll(songModels);
                Log.d("TAG", "onPostExecute: model onPost : " + songModels.size());
                SongDataBase.getInstance(context).songDao().deleteAllSong();        // delete
                SongDataBase.getInstance(context).songDao().insertListSong(songModels);     // select
                liveData.setValue(dsSong);
            } else {
                Toast.makeText(context, "Không có kết nối internet : !!!", Toast.LENGTH_SHORT).show();
                liveData.setValue(SongDataBase.getInstance(context).songDao().getListSong());
            }

        }
    }

}
