package ru.hivislav.nasaexplorer.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {// TODO
            }
            AppState.Loading -> {// TODO
            }
            is AppState.Success -> {
                binding.mainFragmentPicOfTheDay.load(appState.pictureOfTheDayResponseDTO.url) {
                    //TODO
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