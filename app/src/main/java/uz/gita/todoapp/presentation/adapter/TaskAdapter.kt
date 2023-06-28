package uz.gita.todoapp.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.todoapp.R
import uz.gita.todoapp.data.room.entity.TaskEntity
import uz.gita.todoapp.databinding.ListItemTodoBinding
import uz.gita.todoapp.utils.Constants
import uz.gita.todoapp.utils.drawableStringToDrawable
import uz.gita.todoapp.utils.inflate


class TaskAdapter : ListAdapter<TaskEntity, TaskAdapter.ViewHolder>(TaskItemCallback) {



    object TaskItemCallback : DiffUtil.ItemCallback<TaskEntity>() {

        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.isWorking == newItem.isWorking
        }
    }


    private var checkListener: ((TaskEntity) -> Unit)? = null
    private var itemClickListener: ((TaskEntity) -> Unit)? = null
    private var longItemClickListener: ((TaskEntity) -> Unit)? = null

    fun setCheckedListener(block: (TaskEntity) -> Unit) {
        checkListener = block
    }

    fun setItemClickListener(block: (TaskEntity) -> Unit) {
        itemClickListener = block
    }

    fun setItemLongClickListener(block: (TaskEntity) -> Unit) {
        longItemClickListener = block
    }

    inner class ViewHolder(private val binding: ListItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                rbTodo.setOnClickListener {
                    rbTodo.isChecked = !rbTodo.isChecked
                    checkListener?.invoke(getItem(adapterPosition))
                }
                root.setOnClickListener {
                    itemClickListener?.invoke(getItem(adapterPosition))
                }
                root.setOnLongClickListener {
                    longItemClickListener?.invoke(getItem(adapterPosition))
                    true
                }
            }

        }

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun onBind() {
            val data = getItem(adapterPosition)
            binding.apply {
                tvNameTodo.text = data.title



                if (data.isWorking == 1){
                    root.elevation = 4f
                    tvNameTodo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                root.setBackgroundResource(if (data.isWorking == 1) R.drawable.bg_todo_completed else R.drawable.bg_todo)
                tvTimeTodo.text = "$TIME ${data.time}"
                val category = Constants.categoryList[data.categoryId - 1]
                tvCategoryTask.text = category.categoryName
                tvPriorityTask.text = data.priority.toString()
                binding.imgCategoryTask.setImageResource(
                    drawableStringToDrawable(
                        binding.tvNameTodo.context,
                        category.categoryDrawable
                    )
                )


                when(data.priority){
                    1 -> {

                        tvPriorityTask.text = "Low"
                        tvPriorityTask.setTextColor(Color.parseColor("#FFFFFFFF"))
                    }
                    2 -> {
                        tvPriorityTask.setTextColor(Color.parseColor("#4C4949"))
                        tvPriorityTask.text = "Medium"
                    }
                    3 -> {
                        tvPriorityTask.text = "High"
                        tvPriorityTask.setTextColor(Color.parseColor("#7E0000"))
                    }
                    4 -> {
                        tvPriorityTask.setTextColor(Color.parseColor("#EC3232"))
                        tvPriorityTask.text = "Urgent"
                    }

                }
                rbTodo.isChecked = data.isWorking == 1
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemTodoBinding.bind(
            parent.inflate(R.layout.list_item_todo)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

    companion object{
        const val TIME = "Time:"
    }
}
