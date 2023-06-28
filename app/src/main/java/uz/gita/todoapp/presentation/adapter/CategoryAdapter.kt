package uz.gita.todoapp.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.todoapp.R
import uz.gita.todoapp.data.model.CategoryEntity
import uz.gita.todoapp.databinding.ListItemCategoryBinding
import uz.gita.todoapp.utils.drawableStringToDrawable
import uz.gita.todoapp.utils.inflate

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categoryList: List<CategoryEntity> = emptyList()
    var selectedPos = 0

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CategoryEntity>) {
        categoryList = list
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(private val binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                selectedPos = adapterPosition
                notifyDataSetChanged()
            }
        }

        fun onBind() {
            val data = categoryList[adapterPosition]
            binding.tvCategory.text = data.categoryName
            binding.imgCategory.setImageResource(
                drawableStringToDrawable(
                    binding.tvCategory.context,
                    data.categoryDrawable
                )
            )
            if (adapterPosition == selectedPos) {
                binding.root.setBackgroundResource(R.drawable.bg_for_selected)
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemCategoryBinding.bind(parent.inflate(R.layout.list_item_category)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = categoryList.size
}