package com.example.alkemyproyect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alkemyproyect.databinding.FragmentMainMoviesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainMoviesFragment : Fragment(),OnClickListener {

    private var _binding: FragmentMainMoviesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMoviesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadMovies()

    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun loadMovies(){

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit()
                .create(APIResponse::class.java)
                .getMyMovies(Constants.API_PATH + Constants.API_POPULAR )

            val movie: MovieResponse? = call.body()


            withContext(Dispatchers.Main) {
                val movies = movie?.movie ?: emptyList()

                if (call.isSuccessful){
                    binding.progressBar.visibility = View.GONE
                    initMovie(movies)
                }else{
                    //show error
                }
            }

        }

    }

    private fun initMovie(movie: List<Movie>) {
        val decoration = DividerItemDecoration(context, GridLayoutManager(context,3).orientation )
        binding.recyclerView.layoutManager = GridLayoutManager(context,2)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = MovieAdapter(movie, this)
        binding.recyclerView.addItemDecoration(decoration)
    }


    override fun onClick(movie: Movie) {
        super.onClick(movie)
        Toast.makeText(context,"${movie.id}", Toast.LENGTH_LONG).show()
        val args = Bundle()
        args.putLong(getString(R.string.key), movie.id.toLong())
        val bundle = bundleOf(getString(R.string.key) to movie.id.toString())
        findNavController().navigate(R.id.action_mainMoviesFragment_to_detailsMovieFragment,args)
    }

}