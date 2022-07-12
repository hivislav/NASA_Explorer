package ru.hivislav.nasaexplorer.view.picoftheday

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.size.Scale
import com.google.android.material.snackbar.Snackbar
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.FragmentPicOfTheDayBinding
import ru.hivislav.nasaexplorer.utils.setMyDate
import ru.hivislav.nasaexplorer.viewmodel.AppState
import ru.hivislav.nasaexplorer.viewmodel.MainViewModel
import java.util.*

class PicOfTheDayFragment : Fragment() {

    private var _binding: FragmentPicOfTheDayBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPicOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildImageLoader()

        arguments?.getInt(PIC_OF_THE_DAY_BUNDLE_EXTRA).let { date ->
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it, date)
            }
            viewModel.sendRequestByDate(currentDate.setMyDate(date ?: 0))
        }



        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_BASE_URL${binding.input.text.toString()}")
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buildImageLoader() {
        imageLoader = ImageLoader.Builder(requireContext())
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    private fun renderData(appState: AppState, date: Int?) {
        when (appState) {
            is AppState.Error -> {
                Snackbar.make(binding.root, appState.error.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction(
                    getString(R.string.snackbar_error_try_again)) {viewModel.sendRequestByDate(currentDate.setMyDate(date ?: 0))}.show()
            }

            is AppState.Loading -> {
                binding.mainFragmentPicOfTheDay.load(R.drawable.loading, imageLoader)
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
        fun newInstance(bundle: Bundle) : PicOfTheDayFragment {
            val fragment = PicOfTheDayFragment()
            fragment.arguments = bundle
            return fragment
        }

        const val PIC_OF_THE_DAY_BUNDLE_EXTRA = "PIC_OF_THE_DAY_BUNDLE_EXTRA"
        private val currentDate = Date()
        private const val WIKI_BASE_URL = "https://en.wikipedia.org/wiki/"
    }
}