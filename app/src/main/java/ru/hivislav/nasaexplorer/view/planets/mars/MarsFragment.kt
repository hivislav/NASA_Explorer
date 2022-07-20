package ru.hivislav.nasaexplorer.view.planets.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.FragmentMarsBinding
import ru.hivislav.nasaexplorer.model.entities.MarsPhotoDTO
import ru.hivislav.nasaexplorer.view.planets.FullScreenPictureDialogFragment
import ru.hivislav.nasaexplorer.viewmodel.MarsAppState
import ru.hivislav.nasaexplorer.viewmodel.MarsViewModel


class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    private val adapter = MarsRecyclerViewAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(marsPhoto: MarsPhotoDTO) {
            isCrop = !isCrop

            if (isCrop) {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                FullScreenPictureDialogFragment.newInstance(
                    Bundle().apply {
                        putString(FULL_SCREEN_DIALOG_FRAGMENT_BUNDLE_EXTRA, marsPhoto.imgSrc)
                    }).show(transaction, "")
            }
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.marsRecycler.also {
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))
            it.addItemDecoration(DividerItemDecoration(context, GridLayoutManager.HORIZONTAL))
        }

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
            }
        viewModel.sendRequestByDate("2016-6-3")

        }

    private fun renderData(appState: MarsAppState) {
        when (appState) {
            is MarsAppState.Error -> {
                Snackbar.make(binding.root, appState.error.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction(
                    getString(R.string.snackbar_error_try_again)) {viewModel.sendRequestByDate("2016-6-3")}.show()
            }

            is MarsAppState.Loading -> {

            }

            is MarsAppState.Success -> {
                appState.listOfMarsPhotos.let { adapter.setMarsData(it.photos) }
            }
        }
    }

    interface OnItemViewClickListener{
        fun onItemViewClick(marsPhoto : MarsPhotoDTO)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MarsFragment()
        const val FULL_SCREEN_DIALOG_FRAGMENT_BUNDLE_EXTRA = "FULL_SCREEN_DIALOG_FRAGMENT_BUNDLE_EXTRA"
        var isCrop = false
    }
}