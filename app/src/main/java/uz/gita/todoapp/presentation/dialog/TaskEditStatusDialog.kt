package uz.gita.todoapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.todoapp.R
import uz.gita.todoapp.databinding.DialogEditStatusTaskBinding

class TaskEditStatusDialog(ctx: Context, private val isWorking: Boolean) : Dialog(ctx) {

    private lateinit var binding: DialogEditStatusTaskBinding

    private var editListener: ((Boolean) -> Unit)? = null

    fun setEditListener(block: (Boolean) -> Unit) {
        editListener = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogEditStatusTaskBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        setCancelable(false)
        binding.tvMessage.text =
            if (isWorking) context.resources?.getString(R.string.completed_task_message) else
                context.resources?.getString(R.string.in_completed_task_message)
        binding.btnEdit.setOnClickListener {
            editListener?.invoke(true)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            editListener?.invoke(false)
            dismiss()
        }

    }

}