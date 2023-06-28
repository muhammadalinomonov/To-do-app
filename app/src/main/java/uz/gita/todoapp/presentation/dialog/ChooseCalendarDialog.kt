package uz.gita.todoapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import uz.gita.todoapp.databinding.DialogChooseCalendarBinding
import uz.gita.todoapp.utils.getCurrentDate
import uz.gita.todoapp.utils.toDate
import java.time.Month

class ChooseCalendarDialog(context: Context, private var date: String) : Dialog(context) {
    private var dateListener: ((String) -> Unit)? = null
    private lateinit var binding: DialogChooseCalendarBinding
    private var day = 0
    private var month = 0
    private var year = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogChooseCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.calendarView.date = date.toDate().time

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            day = dayOfMonth
            this.month = month
            this.year = year
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val builder = StringBuilder()
            val dayS = if (day > 9) "$day" else "0$day"
            month ++
            val monthS = if (month > 9) "$month" else "0$month"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                builder.append("$dayS.$monthS.$year")
            } else {
                builder.append("$dayS.$monthS.$year")
            }
            dateListener?.invoke(if (day > 0) builder.toString() else getCurrentDate())
            dismiss()
        }
    }

    fun setDateListener(block: (String) -> Unit) {
        dateListener = block
    }

}