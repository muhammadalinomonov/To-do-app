package uz.gita.todoapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.todoapp.databinding.DialogEditStatusTaskBinding

class TaskDeleteDialog(ctx: Context) : Dialog(ctx) {

    private lateinit var binding: DialogEditStatusTaskBinding

    private var editListener: ((Boolean) -> Unit)? = null

    fun setDeleteListener(block: (Boolean) -> Unit) {
        editListener = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogEditStatusTaskBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        setCancelable(false)
        binding.title.text = "Delete task"
        binding.btnEdit.text = "Delete"
        binding.tvMessage.text = "Are you sure want to delete it?"
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