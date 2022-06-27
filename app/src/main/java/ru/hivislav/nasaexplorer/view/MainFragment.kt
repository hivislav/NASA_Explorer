package ru.hivislav.nasaexplorer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.hivislav.nasaexplorer.databinding.FragmentMainBinding
import ru.hivislav.nasaexplorer.viewmodel.AppState
import ru.hivislav.nasaexplorer.viewmodel.MainViewModel

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
    }
}