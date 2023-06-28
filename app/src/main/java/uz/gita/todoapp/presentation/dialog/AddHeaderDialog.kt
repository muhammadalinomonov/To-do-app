package uz.gita.todoapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.todoapp.databinding.DialogAddHeaderBinding


class AddHeaderDialog(ctx: Context, private val title: String, private val description: String) :
    Dialog(ctx) {

    private var addHeaderListener: ((String, String) -> Unit)? = null

    fun setHeaderListener(block: (String, String) -> Unit) {
        addHeaderListener = block
    }


    private lateinit var binding: DialogAddHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogAddHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.apply {

            edTaskTitle.setText(title)
            edTaskDescription.setText(description)
        }
        binding.btnAddHeader.setOnClickListener {

            val title = binding.edTaskTitle.text.toString()
            val description = binding.edTaskDescription.text.toString()
            if (title.isNotEmpty()) {
                addHeaderListener?.invoke(title, description)
                dismiss()
            } else {
                binding.edTaskTitle.error = "Required"
            }
        }
    }

}