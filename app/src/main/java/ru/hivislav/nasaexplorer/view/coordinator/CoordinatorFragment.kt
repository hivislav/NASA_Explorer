package ru.hivislav.nasaexplorer.view.coordinator

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.FragmentCoordinatorBinding

class CoordinatorFragment : Fragment() {

    private var _binding: FragmentCoordinatorBinding? = null
    private val binding get() = _binding!!
    lateinit var spannableString: SpannableString

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spannableString = SpannableString(getString(R.string.large_text))
        mySetSpan()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = CoordinatorFragment()
    }

    private fun colorText(colorFirstNumber: Int) {
        binding.descriptionCoordinator.setText(spannableString, TextView.BufferType.SPANNABLE)
        spannableString = binding.descriptionCoordinator.text as SpannableString

        val map = mapOf(
            0 to ContextCompat.getColor(requireContext(), R.color.light_red),
            1 to ContextCompat.getColor(requireContext(), R.color.gold),
            2 to ContextCompat.getColor(requireContext(), R.color.orange),
            3 to ContextCompat.getColor(requireContext(), R.color.red)
        )

        val spans = spannableString.getSpans(0, spannableString.length, ForegroundColorSpan::class.java)
        for (span in spans) {
            spannableString.removeSpan(span)
        }

        var colorNumber = colorFirstNumber
        for (i in 0 until binding.descriptionCoordinator.text.length) {
            if (colorNumber == 3) colorNumber = 0 else colorNumber += 1
            spannableString.setSpan(ForegroundColorSpan(map.getValue(colorNumber)), i, i + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        }
    }

    private fun mySetSpan (i: Int = 1) {
        var currentCount = i
        val x = object : CountDownTimer(20000, 200) {
            override fun onTick(millisUntilFinished: Long) {
                colorText(currentCount)
                currentCount = if (++currentCount > 3) 0 else currentCount
            }

            override fun onFinish() {
                mySetSpan(currentCount)
            }
        }
        x.start()
    }
}