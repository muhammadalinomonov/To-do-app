package uz.gita.todoapp.presentation.screen.home


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.todoapp.R
import uz.gita.todoapp.data.room.entity.TaskEntity
import uz.gita.todoapp.databinding.ScreenHomeBinding
import uz.gita.todoapp.presentation.adapter.TaskAdapter
import uz.gita.todoapp.presentation.dialog.TaskEditStatusDialog
import uz.gita.todoapp.presentation.dialog.bottomsheet.BottomMenuDialog
import uz.gita.todoapp.presentation.screen.home.viewmodel.HomeViewModel
import uz.gita.todoapp.presentation.screen.home.viewmodel.HomeViewModelImpl
import uz.gita.todoapp.utils.cancelWork


@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private lateinit var binding: ScreenHomeBinding
    private val adapter: TaskAdapter by lazy { TaskAdapter() }


    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openAddTaskLiveData.observe(this, addTaskObserver)
        viewModel.openEditDialog.observe(this, editDialogListener)
        viewModel.openUpdateTaskLiveData.observe(this, updateTaskObserver)
        viewModel.openBottomMenu.observe(this, openBottomMenuObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ScreenHomeBinding.bind(view)



        binding.dots.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.dots)

            // Inflating popup menu from popup_menu.xml file

            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.status_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem -> // Toast message on menu item clicked


                when (menuItem.itemId) {
                    R.id.all_tasks -> {
                        viewModel.setPosition(0)
                    }

                    R.id.completed -> {
                        viewModel.setPosition(1)
                    }

                    R.id.incomplete -> {
                        viewModel.setPosition(2)
                    }

                    R.id.about_app -> {
                        viewModel.menuClick()
                    }
                }
                true

            }
            // Showing the popup menu
            // Showing the popup menu
            popupMenu.show()
        }


        /*val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.status_tasks,
                R.layout.item_spinner
            )*/
//        binding.spinnerStatus.adapter = spinnerAdapter

        binding.apply {

            fabAdd.setOnClickListener {
                viewModel.addTaskClick()
            }
            imgSort.setOnClickListener {
                viewModel.menuClick()
            }
            listTodo.adapter = adapter
//            spinnerStatus.onItemSelectedListener = itemSelectedListener
        }

        viewModel.getImage()


        viewModel.allTasks.observe(viewLifecycleOwner, tasksObserver)

        adapter.setCheckedListener {
            viewModel.editClicked(it)
        }
        adapter.setItemClickListener {
            viewModel.openUpdate(it)
        }
        adapter.setItemLongClickListener {
            Toast.makeText(requireContext(), it.description, Toast.LENGTH_SHORT).show()
        }


    }


    @SuppressLint("SuspiciousIndentation")
    private val tasksObserver = Observer<List<TaskEntity>> {
        if (it == null) {
            Log.d("TTT", "it is null")
        }
        it?.let {
            if (it.isEmpty())
                binding.imgPlaceHolder.visibility = View.VISIBLE
            else binding.imgPlaceHolder.visibility = View.INVISIBLE
            adapter.submitList(it)
        }


    }


    private var itemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.setPosition(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

    private var editDialogListener = Observer<TaskEntity> {
        val dialog = TaskEditStatusDialog(requireContext(), it.isWorking == 1)
        dialog.show()
        dialog.setEditListener { b ->
            viewModel.updateTask(it.copy(isWorking = if (b) 1 - it.isWorking else it.isWorking))
            cancelWork(requireContext(), it.uuid)
        }
    }
    private var updateTaskObserver = Observer<TaskEntity> {
        findNavController().navigate(
            HomeScreenDirections.actionHomeScreenToUpdateScreen(it)
        )
    }

    private var addTaskObserver = Observer<Unit> {
        findNavController().navigate(HomeScreenDirections.actionHomeScreenToAddTaskScreen())
    }

    private val openBottomMenuObserver = Observer<Unit> {
        val dialog = BottomMenuDialog()
        dialog.show(childFragmentManager, "menu")
    }
}