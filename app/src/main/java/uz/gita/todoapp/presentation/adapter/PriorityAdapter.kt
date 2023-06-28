package uz.gita.todoapp.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.todoapp.R
import uz.gita.todoapp.databinding.ListItemPriorityBinding
import uz.gita.todoapp.utils.inflate


class PriorityAdapter : RecyclerView.Adapter<PriorityAdapter.ViewHolder>() {

    var selectedPos = 0


    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(private val binding: ListItemPriorityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                selectedPos = adapterPosition
                notifyDataSetChanged()
            }
        }

        @SuppressLint("SetTextI18n")
        fun onBind() {
            binding.tvPriority.text = "${adapterPosition + 1}"
            when(adapterPosition + 1){
                1 -> {

                    binding.tvPriority.text = "Low"
                    binding.tvPriority.setTextColor(Color.parseColor("#9A9595"))
                }
                2 -> {
                    binding.tvPriority.setTextColor(Color.parseColor("#4C4949"))
                    binding.tvPriority.text = "Medium"
                }
                3 -> {
                    binding.tvPriority.text = "High"
                    binding.tvPriority.setTextColor(Color.parseColor("#7E0000"))
                }
                4 -> {
                    binding.tvPriority.setTextColor(Color.parseColor("#EC3232"))
                    binding.tvPriority.text = "Urgent"
                }

            }
            if (adapterPosition == selectedPos) binding.root.setBackgroundResource(R.drawable.bg_for_selected) else
                binding.root.setBackgroundColor(Color.TRANSPARENT)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ListItemPriorityBinding.bind(parent.inflate(R.layout.list_item_priority)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

    override fun getItemCount() = 4

}