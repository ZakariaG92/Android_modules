package com.mbds.news.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbds.news.R
import com.mbds.news.adapters.ArticleAdapter
import com.mbds.news.api.repository.ArticleRpository
import com.mbds.news.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ArticlesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticlesFragmentNews : Fragment() {
    var art: String? = null
    private val repository = ArticleRpository()



    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch(Dispatchers.IO) {
            getData(view)
        }

    }


    private suspend fun getData(view: View) {
        withContext(Dispatchers.IO) {
            val result = repository.list()
            val news = mutableListOf<Article>()

            for (i in 0..(result?.articles?.size?.minus(1!!)!! )) {

                when(result?.articles.get(i).source?.name){
                    "Mediapart","Google News","Le Parisien","Le Monde","L'Indépendant","Libération","BFMTV","20 Minutes","Normandie Actu"-> { news.add(result?.articles.get(i))!!}
                }
            }


            bindData(news,view)




        }
    }
    //S'execute sur le thread principal
    private suspend fun bindData(result: MutableList<Article>, view: View) {
        withContext(Dispatchers.Main) {


            val recyclerView: RecyclerView = view.findViewById(R.id.recycler_articles)

            val adapterRecycler= result?.let { ArticleAdapter(it) }
            recyclerView.hasFixedSize()
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapterRecycler






        }
    }



}