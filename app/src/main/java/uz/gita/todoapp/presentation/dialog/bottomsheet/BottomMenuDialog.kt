package uz.gita.todoapp.presentation.dialog.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.todoapp.MainActivity
import uz.gita.todoapp.databinding.DialogMenuBottomBinding
import uz.gita.todoapp.utils.Constants

class BottomMenuDialog : BottomSheetDialogFragment() {


    private lateinit var binding: DialogMenuBottomBinding

    private val viewModel: BottomMenuViewModel by viewModels<BottomMenuViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogMenuBottomBinding.inflate(inflater, container, false)
        viewModel.aboutUsLiveData.observe(this, aboutObserver)
        viewModel.shareAppLiveData.observe(this, shareAppObserver)
        viewModel.supportUsLiveData.observe(this, supportObserver)

        binding.apply {
            tvAboutUs.setOnClickListener { viewModel.aboutClick() }
            tvSupportUs.setOnClickListener { viewModel.supportClick() }
            tvShareApp.setOnClickListener { viewModel.shareClick() }
        }
        return binding.root
    }

    private val supportObserver = Observer<Unit> {
        dismiss()
        Constants.goToPlayMarket(activity as MainActivity)
    }

    private val shareAppObserver = Observer<Unit> {
        dismiss()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "UpTodo")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "link: https://play.google.com/store/apps/details?id=uz.gita.my_todo_app"
        )
        startActivity(Intent.createChooser(intent, "UpTodo"))
    }

    private val aboutObserver = Observer<Unit> {
        dismiss()
        val emailIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "muhammadalinomonov837@gmail.com"))
        startActivity(emailIntent)
//        findNavController().navigate(R.id.aboutFragment)
    }

}