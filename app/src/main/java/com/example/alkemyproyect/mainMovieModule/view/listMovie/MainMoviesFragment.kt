package com.example.alkemyproyect.mainMovieModule.view.listMovie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alkemyproyect.*
import com.example.alkemyproyect.databinding.FragmentMainMoviesBinding
import com.example.alkemyproyect.mainMovieModule.view.adapter.MovieAdapter
import com.example.alkemyproyect.mainMovieModule.viewModels.listMovieViewModels.MovieViewModel
import com.example.alkemyproyect.mainMovieModule.model.Movie


class MainMoviesFragment : Fragment(), OnClickListener {

    private var _binding: FragmentMainMoviesBinding? = null
    private val binding get() = _binding!!

    private var bandera = true

    val viewModel by viewModels<MovieViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMoviesBinding.inflate(inflater, container, false)

       setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {

      viewModel.getMovies()
        viewModel.movieList.observe(viewLifecycleOwner, Observer {movie ->
            initMovie(movie)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {movie ->
            Log.i("valor", movie)
        })
        viewModel.isloading.observe(viewLifecycleOwner, Observer {movie ->
            if (movie) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE

        })

    }

    private fun initMovie(movie: List<Movie>) {
        val decoration = DividerItemDecoration(context, GridLayoutManager(context,3).orientation )
        binding.recyclerView.layoutManager = GridLayoutManager(context,2)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = MovieAdapter(movie, this)
        binding.recyclerView.addItemDecoration(decoration)
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        connecting()
    }*/

  /*  private fun connecting(){
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


    }*/
    /*private fun isConnected(requireContext: Context): Boolean {
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
    }*/

   /* private fun loadMovies(){

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

    }*/




    override fun onClick(movie: Movie) {
        super.onClick(movie)
        val args = Bundle()
        args.putLong(getString(R.string.key), movie.id.toLong())
        findNavController().navigate(R.id.action_mainMoviesFragment_to_detailsMovieFragment,args)
    }


}