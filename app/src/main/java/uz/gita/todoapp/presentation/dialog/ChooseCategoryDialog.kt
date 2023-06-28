package uz.gita.todoapp.presentation.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.todoapp.data.model.CategoryEntity
import uz.gita.todoapp.databinding.DialogChooseCategoryBinding
import uz.gita.todoapp.presentation.adapter.CategoryAdapter

class ChooseCategoryDialog(
    ctx: Context,
    private val list: List<CategoryEntity>,
    private val selectedCategory: Int
) : Dialog(ctx) {

    private lateinit var binding: DialogChooseCategoryBinding

    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter()
    }

    private var categoryListener: ((CategoryEntity) -> Unit)? = null

    fun setCategoryClickListener(block: (CategoryEntity) -> Unit) {
        categoryListener = block
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogChooseCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        adapter.submitList(list)
        binding.listCategory.adapter = adapter
        adapter.selectedPos = selectedCategory
        adapter.notifyDataSetChanged()
        binding.btnChooseCategory.setOnClickListener {
            categoryListener?.invoke(list[adapter.selectedPos])
            dismiss()
        }
    }

}