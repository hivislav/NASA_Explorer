package ru.hivislav.nasaexplorer.view.planets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.DialogFragmentFullScreenPictureBinding
import ru.hivislav.nasaexplorer.view.planets.mars.MarsFragment
import ru.hivislav.nasaexplorer.view.planets.mars.MarsFragment.Companion.FULL_SCREEN_DIALOG_FRAGMENT_BUNDLE_EXTRA

class FullScreenPictureDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentFullScreenPictureBinding? = null
    private val binding get() = _binding!!

    private var isCrop = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentFullScreenPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.fullScreenMarsRecycler)
            .load(arguments?.getString(FULL_SCREEN_DIALOG_FRAGMENT_BUNDLE_EXTRA))

        binding.fullScreenMarsRecycler.setOnClickListener {
            cropPhoto()
        }
    }

    private fun cropPhoto() {
        isCrop = !isCrop

        val transitionSet = TransitionSet()
        val changeImageTransform = ChangeImageTransform()
        val changeBounds = ChangeBounds()
        changeBounds.duration = 500L
        changeImageTransform.duration = 500L

        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

        transitionSet.addTransition(changeBounds)
        transitionSet.addTransition(changeImageTransform)

        TransitionManager.beginDelayedTransition(binding.root, transitionSet)
        if (isCrop) {
            binding.fullScreenMarsRecycler.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            binding.fullScreenMarsRecycler.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        MarsFragment.isCrop = !MarsFragment.isCrop
    }

    companion object {
        fun newInstance(bundle: Bundle): FullScreenPictureDialogFragment {
            val fragment = FullScreenPictureDialogFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}