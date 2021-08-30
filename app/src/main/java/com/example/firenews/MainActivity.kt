package com.example.firenews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfresh.NewsAdapter
import com.littlemango.stacklayoutmanager.StackLayoutManager
import com.littlemango.stacklayoutmanager.StackLayoutManager.ItemChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {



    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var PageNum =1
    var totalResults=-1
    val TAG ="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NewsAdapter(this@MainActivity,articles)
        newsList.adapter=adapter
        //newsList.layoutManager=LinearLayoutManager(this@MainActivity)
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)

        layoutManager.setItemChangedListener(object: StackLayoutManager.ItemChangedListener {

            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(Colorpicker.getColor()))
                Log.d(TAG,"First Visible Item -${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG,"Total Count -${layoutManager.itemCount }")
                if(totalResults>layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >=layoutManager.itemCount -5)
                {
                    PageNum++
                    getNews()
                }
            }

        })



        newsList.layoutManager=layoutManager

        getNews()
    }

    private fun getNews() {
        Log.d(TAG,"Request sent for $PageNum")
        val news:
       Call<News> = NewsService.newsInstance.getHeadlines("in",PageNum)
        news.enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news =response.body()
                if(news!=null)
                {
                    totalResults=news.totalResults
                    Log.d("nooo",news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()


                }


            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("nooo","Error in Fetching News")



            }
        })

    }
}