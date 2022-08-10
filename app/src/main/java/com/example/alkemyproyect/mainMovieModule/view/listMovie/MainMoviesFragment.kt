package com.example.alkemyproyect.mainMovieModule.view.listMovie

import android.os.Bundle
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
import com.example.alkemyproyect.mainMovieModule.model.listMovieModel.Movie
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainMoviesFragment : Fragment(), OnClickListener {

    private var _binding: FragmentMainMoviesBinding? = null
    private val binding get() = _binding!!

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
        viewModel.error.observe(viewLifecycleOwner, Observer {error ->

            MaterialAlertDialogBuilder(requireContext()).setTitle(error)
                .setView(layoutInflater.inflate(R.layout.dialog_alert,null))
                .setCancelable(false).setPositiveButton("Salir... ",{ _ , i ->
                    onDestroy()
            }).show()
        })
        viewModel.isloading.observe(viewLifecycleOwner, Observer {success ->
            if (success) binding.progressBar.visibility = View.VISIBLE
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



    override fun onClick(movie: Movie) {
        super.onClick(movie)
        val args = Bundle()
        args.putLong(getString(R.string.key), movie.id.toLong())
        findNavController().navigate(R.id.action_mainMoviesFragment_to_detailsMovieFragment,args)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}