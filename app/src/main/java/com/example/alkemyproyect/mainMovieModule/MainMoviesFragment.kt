package com.example.alkemyproyect.mainMovieModule

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alkemyproyect.*
import com.example.alkemyproyect.common.APIResponse
import com.example.alkemyproyect.common.Constants
import com.example.alkemyproyect.databinding.FragmentMainMoviesBinding
import com.example.alkemyproyect.mainMovieModule.adapter.MovieAdapter
import com.example.alkemyproyect.movieDetailsModule.Movie
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainMoviesFragment : Fragment(), OnClickListener {

    private var _binding: FragmentMainMoviesBinding? = null
    private val binding get() = _binding!!

    private var bandera = true




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMoviesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        connecting()
    }

    private fun connecting(){
        if (isConnected(requireContext())){
            bandera = false
            loadMovies()
        }
        else {
            MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.title)
                .setView(layoutInflater.inflate(R.layout.dialog_alert, null))
                .setCancelable(false)
                .setPositiveButton("reintentar...") { _ , i ->
                    if (isConnected(requireContext())) {
                        loadMovies()
                    } else {
                        connecting()
                    }

                    Toast.makeText(requireContext(), "hollaa", Toast.LENGTH_LONG).show()
                }
                .show()
        }


    }
    private fun isConnected(requireContext: Context): Boolean {
        val cm = requireContext.getSystemService(Context.CONNECTIVITY_SERVICE)  as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
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
                .getMyMovies(Constants.API_PATH + Constants.API_POPULAR)

            val movie: MovieResponse? = call.body()


            withContext(Dispatchers.Main) {
                val movies = movie?.movie ?: emptyList()

                if (call.isSuccessful){
                    binding.progressBar.visibility = View.GONE
                    initMovie(movies)
                }else{
                    MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.title)
                        .setView(layoutInflater.inflate(R.layout.dialog_alert, null)).setCancelable(false)
                        .setPositiveButton("reintentar...") { _, i ->
                            onDestroy()
                        }
                        .show()
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
        val args = Bundle()
        args.putLong(getString(R.string.key), movie.id.toLong())
        findNavController().navigate(R.id.action_mainMoviesFragment_to_detailsMovieFragment,args)
    }


}