package com.takehome.sauravrp.views


import javax.inject.Inject
//
//class EmployeeDetailFragment : Fragment() {
//
//    @Inject
//    lateinit var employeeDirectoryViewModelFactory: EmployeeDirectoryViewModelFactory
//
//    private var binding: EmployeeDetailViewBinding? = null
//
//    private lateinit var model: EmployeeDirectoryViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        DaggerFragmentComponent
//            .factory()
//            .create((requireActivity().applicationContext as DirectoryComponentProvider).directoryComponent())
//            .inject(this)
//
//        model = ViewModelProvider(requireActivity(), employeeDirectoryViewModelFactory).get(
//            EmployeeDirectoryViewModel::class.java
//        )
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val _binding = EmployeeDetailViewBinding.inflate(inflater, container, false)
//        binding = _binding
//        return _binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        model.selection.observe(viewLifecycleOwner, {
//            showDetail(it)
//        })
//    }
//
//    private fun showDetail(employee: Employee) {
//        binding?.apply {
//            Picasso.get().load(employee.largePhotoUrl).into(image)
//        }
//    }
//}