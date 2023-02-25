package com.example.feedview

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class MainActivity : AppCompatActivity() {


    //lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act1_fragment)

        actionBar?.title  = ""
 //       Log.d("tag", "test")
        if (savedInstanceState == null) {
           // val bundle = Bundle()
           // bundle.putString("param", "value")
            val f = MainFragment()
            //f.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, f).commitAllowingStateLoss()
        }

//        val config = RealmConfiguration.Builder(schema = setOf(Feed::class, FeedItem::class, Enclosure::class)).build()
//        realm = Realm.open(config)
    }

    fun showArticle(link: String) {

        val bundle = Bundle()
        bundle.putString("url", link)
        val f = SecondFragment()
        f.arguments = bundle

        val frame2 = findViewById<View>(R.id.fragment_frame2)
        if (frame2 != null) {
            frame2.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.fragment_frame2, f).commitAllowingStateLoss()
        } else
            supportFragmentManager.beginTransaction().add(R.id.fragment_frame, f).addToBackStack("main")
                .commitAllowingStateLoss()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}


//open class Feed(
//    var items: RealmList<FeedItem> = realmListOf<FeedItem>()
//) : RealmObject {constructor() : this(realmListOf<FeedItem>())
//}
//
//open class FeedItem(
//    var title: String = "",
//    var link: String = "",
//    var eclosure: Enclosure? = null,
//    var description: String = "",
//) : RealmObject {constructor() : this("","", null, "")}
//
//open class Enclosure(
//    var link: String = "",
//    var type: String = ""
//)  : RealmObject {constructor() : this("", "")}
//

class RecAdapter(val items:ArrayList<FeedItemAPI>):RecyclerView.Adapter<RecHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        
        return RecHolder(view)
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
       //Log.w("tag", "onBindViewHolder")
        val item = items[position]
        
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}

class RecHolder(view: View):RecyclerView.ViewHolder(view){
    fun bind(item: FeedItemAPI){
        val vTitle = itemView.findViewById<TextView>(R.id.item_title)
        val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
        val vThumb = itemView.findViewById<ImageView>(R.id.item_image)
        vTitle.text = item.title
        vDesc.text = item.description
        
        Picasso.get()
            .load(item.enclosure.link)
            .resize(100, 100)
            .centerCrop()
            .into(vThumb)

        itemView.setOnClickListener {
            (vThumb.context as MainActivity).showArticle(item.link)
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse(item.link)
//            vThumb.context.startActivity(intent)
        }
    }
}