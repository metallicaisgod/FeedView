package com.example.feedview

import android.R.attr
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainFragment:Fragment() {

    var request: Disposable? = null
    lateinit var recView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)
        recView = view.findViewById<RecyclerView>(R.id.act1_recView)
       // Log.d("tag", "onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // Log.d("tag", "onViewCreated")
        val o =
            createRequest("https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fautostat.ru%2Fnews%2Frss%2F3")
                .map { Gson().fromJson(it, FeedAPI::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

      //  Log.d("tag", "test")

        request = o.subscribe({

//           val feed = Feed(
//                it.items.mapTo(
//                    realmListOf<FeedItem>()
//                )
//                    { feed ->
//                        FeedItem(
//                            feed.title,
//                            feed.link,
//                            Enclosure(
//                                feed.enclosure.link,
//                                feed.enclosure.type
//                            ),
//                            feed.description
//                        )
//                    }
//            )
//
//            //Log.d("tag", "${feed.items[0].title}")
//
//            realm.writeBlocking {
//                val writeTransactionItems = query<Feed>().find()
//                if(writeTransactionItems.size > 0)
//                    deleteAll()
//                copyToRealm(feed)
//            }
            val ab = requireActivity().actionBar
            requireActivity().title = it.feed.title
            Log.d("Set title", "${it.feed.title}")

//            Picasso.get()
//                .load(it.feed.link)
//                .into(object : Target {
//
//                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                    }
//                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                        Log.e("onBitmapFailed", "test")
//                    }
//                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                        Log.d("onBitmapLoaded", "test")
//                        val d: Drawable = BitmapDrawable(resources, bitmap)
//                        ab?.setIcon(d)
//                        ab?.setDisplayShowHomeEnabled(true)
//                        ab?.setDisplayHomeAsUpEnabled(true)
//                    }
//                })
            showRecyclerView(it)
        }, {
            Log.e("tag", "", it)
            //showRecyclerView(it)
        })

    }

    fun showRecyclerView(feed: FeedAPI) {
//        Log.d("tag", "showRecyclerView() -> Enter")
//        lateinit var feedItems: RealmList<FeedItem>
//        realm.writeBlocking{
//            val writeTransactionItems = query<Feed>().find()
//            if (writeTransactionItems.size > 0) {
//                feedItems = writeTransactionItems[0].items
//                Log.d("tag", "${feedItems[0].title}")
//                recView.adapter = RecAdapter(feedItems)
//                recView.layoutManager = LinearLayoutManager(this@MainActivity)
//            }
//        }
        recView.adapter = RecAdapter(feed.items)
        recView.layoutManager = LinearLayoutManager(activity)
//        Log.d("tag", "${feedItems[0].title}")
//        Log.d("tag", "showRecyclerView() -> Exit")
    }

     override fun onDestroyView() {
        super.onDestroyView()
        request?.dispose()
    }
}