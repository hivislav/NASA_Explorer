package ru.hivislav.nasaexplorer.view

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.request.ImageRequest
import coil.size.Scale
import com.google.android.material.snackbar.Snackbar
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.FragmentMainBinding
import ru.hivislav.nasaexplorer.utils.setMyDate
import ru.hivislav.nasaexplorer.viewmodel.AppState
import ru.hivislav.nasaexplorer.viewmodel.MainViewModel
import java.util.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.mainFragmentChipToday.isChecked = true
        viewModel.sendRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_BASE_URL${binding.input.text.toString()}")
            })
        }

        with(binding){
            this.mainFragmentChipDayBeforeYesterday.setOnClickListener {
                viewModel.sendRequestByDate(currentDate.setMyDate(2))
            }
            this.mainFragmentChipYesterday.setOnClickListener {
                viewModel.sendRequestByDate(currentDate.setMyDate(1))
            }
            this.mainFragmentChipToday.setOnClickListener {
                viewModel.sendRequestByDate(currentDate.setMyDate(0))
            }
        }

        (requireActivity() as MainActivity).setSupportActionBar(binding.mainBottomAppBar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {}
            R.id.action_settings -> {
                requireActivity().supportFragmentManager.beginTransaction().hide(this)
                    .add(R.id.container, SettingsFragment.newInstance()).addToBackStack("").commit()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                Snackbar.make(binding.root, appState.error.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction(
                    getString(R.string.snackbar_error_try_again)) {viewModel.sendRequest()}.show()
            }

            is AppState.Loading -> {
                binding.mainFragmentPicOfTheDay.load(R.drawable.loading)
            }

            is AppState.Success -> {
                binding.mainFragmentPicOfTheDay.load(appState.pictureOfTheDayResponseDTO.url) {
                    scale(Scale.FIT)
                }
                binding.mainFragmentPicOfTheDayDescription.text = appState.pictureOfTheDayResponseDTO.explanation
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()

        private val currentDate = Date()
        private const val WIKI_BASE_URL = "https://en.wikipedia.org/wiki/"
    }
}