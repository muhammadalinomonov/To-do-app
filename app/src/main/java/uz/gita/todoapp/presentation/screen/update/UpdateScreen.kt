package uz.gita.todoapp.presentation.screen.update

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.todoapp.R
import uz.gita.todoapp.data.model.CategoryEntity
import uz.gita.todoapp.databinding.FragmentUpdateTodoBinding
import uz.gita.todoapp.presentation.dialog.AddHeaderDialog
import uz.gita.todoapp.presentation.dialog.ChooseCalendarDialog
import uz.gita.todoapp.presentation.dialog.ChooseCategoryDialog
import uz.gita.todoapp.presentation.dialog.ChoosePriorityDialog
import uz.gita.todoapp.presentation.dialog.ChooseTimeDialog
import uz.gita.todoapp.presentation.dialog.TaskDeleteDialog
import uz.gita.todoapp.presentation.screen.update.viewmodel.UpdateViewModel
import uz.gita.todoapp.presentation.screen.update.viewmodel.UpdateViewModelImpl
import uz.gita.todoapp.utils.Constants
import uz.gita.todoapp.utils.cancelWork
import uz.gita.todoapp.utils.drawableStringToDrawable
import uz.gita.todoapp.workmanager.MyWorker
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.UUID
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class UpdateScreen : Fragment(R.layout.fragment_update_todo) {

    private var desc = ""
    val uuid = UUID.randomUUID()

    private lateinit var binding: FragmentUpdateTodoBinding
    private val viewModel: UpdateViewModel by viewModels<UpdateViewModelImpl>()
    private val args: UpdateScreenArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setTask(args.taskEntity)

        viewModel.openHeaderDialog.observe(this, openHeaderObserver)
        viewModel.openDateDialog.observe(this, openDateObserver)
        viewModel.openTimeDialog.observe(this, openTimeDialogObserver)
        viewModel.openCategoryDialog.observe(this, openCategoryDialogObserver)
        viewModel.openPriorityDialog.observe(this, openPriorityDialogObserver)
        viewModel.closeLiveData.observe(this, closeObserver)
        viewModel.addTaskLiveData.observe(this, saveObserver)
        viewModel.messageLiveData.observe(this, messageListener)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUpdateTodoBinding.bind(view)
        viewModel.apply {
            titleLiveData.observe(viewLifecycleOwner, headerObserver)
            dateLiveData.observe(viewLifecycleOwner, dataObserver)
            timeLiveData.observe(viewLifecycleOwner, timeObserver)
            taskPriorityLiveData.observe(viewLifecycleOwner, priorityObserver)
            categoryLiveData.observe(viewLifecycleOwner, categoryObserver)
        }

        binding.apply {
            tvHeader.setOnClickListener {
                viewModel.openHeaderDialog()
            }
            tvDate.setOnClickListener {
                viewModel.openDateDialog()
            }
            tvTime.setOnClickListener {
                viewModel.openTimeDialog()
            }
            tvCategory.setOnClickListener {
                viewModel.openCategoryDialog()
            }
            tvPriority.setOnClickListener {
                viewModel.openPriorityDialog()
            }
            btnEditTask.setOnClickListener {
                viewModel.addClicked()
            }
            imgClose.setOnClickListener {
                viewModel.closedClick()
            }
            txtDelete.setOnClickListener {
                val dialog = TaskDeleteDialog(requireContext())
                dialog.setDeleteListener {
                    if (it) {
                        cancelWork(requireContext(), args.taskEntity.uuid)
                        viewModel.deleteTask(args.taskEntity)
                        findNavController().popBackStack()
                    } else {
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }

        }
    }

    private val closeObserver = Observer<Unit> {
        findNavController().popBackStack()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val saveObserver = Observer<Unit> {


        cancelWork(requireContext(), args.taskEntity.uuid)
        startWorker(
            binding.tvDate.text.toString(),
            binding.tvTime.text.toString(),
            binding.tvHeader.text.toString(),
            desc,
            uuid
        )

        viewModel.updateTask(args.taskEntity.id, args.taskEntity.uuid)
    }

    private val messageListener = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    //title observers
    private val openHeaderObserver = Observer<Unit> {
        val dialog = AddHeaderDialog(
            requireContext(),
            viewModel.titleLiveData.value!!,
            viewModel.descriptionLiveData.value!!
        )
        dialog.show()
        dialog.setHeaderListener { s, s1 ->
            desc = s1
            viewModel.setHeader(s)
            viewModel.setDescription(s1)
        }
    }
    private val headerObserver = Observer<String> {
        binding.tvHeader.text = it
    }

    //date observers
    private val openDateObserver = Observer<Unit> {
        val dateDialog = ChooseCalendarDialog(requireContext(), viewModel.dateLiveData.value!!)
        dateDialog.show()
        dateDialog.setDateListener {
            viewModel.setDate(it)
        }
    }
    private val dataObserver = Observer<String> {
        binding.tvDate.text = it
    }

    //time observers
    private val openTimeDialogObserver = Observer<Unit> {
        val dialog = ChooseTimeDialog(requireContext(), viewModel.timeLiveData.value!!)
        dialog.show()
        dialog.setTimeListener { time ->
            viewModel.setTime(time)
        }
    }
    private val timeObserver = Observer<String> {
        binding.tvTime.text = it
    }

    //category observers
    private val openCategoryDialogObserver = Observer<Unit> {
        val dialog = ChooseCategoryDialog(
            requireContext(),
            Constants.categoryList,
            viewModel.categoryLiveData.value!!.id - 1
        )
        dialog.show()
        dialog.setCategoryClickListener {
            viewModel.setCategory(it)
        }
    }
    private val categoryObserver = Observer<CategoryEntity> {
        binding.tvCategory.text = it.categoryName
        binding.tvCategory.setCompoundDrawablesWithIntrinsicBounds(
            drawableStringToDrawable(requireContext(), it.categoryDrawable),
            0,
            0,
            0
        )
    }

    //priority observer
    private val openPriorityDialogObserver = Observer<Unit> {
        val dialog = ChoosePriorityDialog(requireContext(), viewModel.taskPriorityLiveData.value!!)
        dialog.show()
        dialog.setPriorityListener {
            viewModel.setPriority(it + 1)
        }
    }
    private val priorityObserver = Observer<Int> {
        when(it){
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
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun startWorker(
        date: String,
        time: String,
        title: String,
        description: String,
        uuid: UUID
    ) {

        Log.d("KKK", date.substring(6))

        val userSelectedDateTime = Calendar.getInstance()

        val chosenYear = date.substring(6).toInt()
        val chosenMonth = date.substring(3, 5).toInt()
        val chosenDay = date.substring(0, 2).toInt()

        val chosenHour = time.substring(0, 2).toInt()
        val chosenMin = time.substring(3).toInt()


        userSelectedDateTime.set(
            chosenYear,
            chosenMonth,
            chosenDay,
            chosenHour,
            chosenMin
        )


        val todayDateTime = Calendar.getInstance()
        todayDateTime.set(
            LocalDateTime.now().year,
            LocalDate.now().monthValue,
            LocalDateTime.now().dayOfMonth,
            LocalDateTime.now().hour,
            LocalDateTime.now().minute
        )


        val delayInSeconds =
            (userSelectedDateTime.timeInMillis / 1000L) - (todayDateTime.timeInMillis / 1000L)

        val constraint = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraint)
            .setId(uuid)
            .setInputData(workDataOf("title" to title, "desc" to description))
            .setInitialDelay(delayInSeconds, TimeUnit.SECONDS)
            .build()



        WorkManager.getInstance(requireContext())
            .enqueueUniqueWork(
                uuid.toString(),
                ExistingWorkPolicy.REPLACE,
                request
            )

    }

}