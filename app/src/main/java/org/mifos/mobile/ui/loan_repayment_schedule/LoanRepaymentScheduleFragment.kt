package org.mifos.mobile.ui.loan_repayment_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import org.mifos.mobile.R
import org.mifos.mobile.models.accounts.loan.LoanWithAssociations
import org.mifos.mobile.models.accounts.loan.Periods
import org.mifos.mobile.models.accounts.loan.tableview.Cell
import org.mifos.mobile.models.accounts.loan.tableview.ColumnHeader
import org.mifos.mobile.models.accounts.loan.tableview.RowHeader
import org.mifos.mobile.ui.adapters.LoanRepaymentScheduleAdapter
import org.mifos.mobile.ui.fragments.base.BaseFragment
import org.mifos.mobile.utils.Constants
import org.mifos.mobile.utils.ParcelableAndSerializableUtils.getCheckedParcelable
import javax.inject.Inject

/**
 * Created by Rajan Maurya on 03/03/17.
 */
@AndroidEntryPoint
class LoanRepaymentScheduleFragment : BaseFragment() {

    private var loanId: Long? = 0
    private var loanWithAssociations: LoanWithAssociations? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) loanId = arguments?.getLong(Constants.LOAN_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
//        _binding = FragmentLoanRepaymentScheduleBinding.inflate(inflater, container, false)
//        sweetUIErrorHandler = SweetUIErrorHandler(context, binding.root)
//        showUserInterface()
//        if (savedInstanceState == null) {
//            viewModel.loanLoanWithAssociations(loanId)
//        }
//        return binding.root
        return ComposeView(requireContext()).apply {
            setContent {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                LoanRepaymentScheduleScreen(navigateBack = { activity?.supportFragmentManager?.popBackStack() }, loanId = loanId ?: 1)
            }
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.loanUiState.collect {
//                    when (it) {
//                        is LoanUiState.Loading -> showProgress()
//                        is LoanUiState.ShowError -> {
//                            hideProgress()
////                            showError(getString(it.message))
//                        }
//
//                        is LoanUiState.ShowLoan -> {
//                            hideProgress()
//                            showLoanRepaymentSchedule(it.loanWithAssociations)
//                        }
//
//                        is LoanUiState.ShowEmpty -> {
//                            hideProgress()
////                            showEmptyRepaymentsSchedule(loanWithAssociations)
//                        }
//
//                        else -> throw IllegalStateException("Unexpected state: $it")
//                    }
//                }
//            }
//        }
//
////        binding.layoutError.btnTryAgain.setOnClickListener {
////            retryClicked()
////        }
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.LOAN_ACCOUNT, loanWithAssociations)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            showLoanRepaymentSchedule(
                savedInstanceState.getCheckedParcelable(
                    LoanWithAssociations::class.java,
                    Constants.LOAN_ACCOUNT
                )
            )
        }
    }

    /**
     * Fetches [LoanWithAssociations] for a loan with `loanId`
     *
     * @param loanWithAssociations Contains details about Repayment Schedule
     */
    fun showLoanRepaymentSchedule(loanWithAssociations: LoanWithAssociations?) {
        /**
         * Handled this logic in compose UI
         */
//        this.loanWithAssociations = loanWithAssociations
//        var currencyRepresentation =
//            loanWithAssociations?.currency?.displaySymbol ?: loanWithAssociations?.currency?.code
//        loanRepaymentScheduleAdapter
//            ?.setCurrency(currencyRepresentation)
//        setTableViewList(loanWithAssociations?.repaymentSchedule?.periods)
//        binding.tvAccountNumber.text = loanWithAssociations?.accountNo
//        binding.tvDisbursementDate.text =
//            DateHelper.getDateAsString(loanWithAssociations?.timeline?.expectedDisbursementDate)
//        binding.tvNumberOfPayments.text = loanWithAssociations?.numberOfRepayments.toString()
    }

    private fun setTableViewList(periods: List<Periods>?) {
        /**
         * Handled this logic in compose UI
         */
//        val mColumnHeaderList: MutableList<ColumnHeader?> = ArrayList()
//        val mRowHeaders: MutableList<RowHeader?> = ArrayList()
//        val mCellList: MutableList<List<Cell?>> = ArrayList()
//        mColumnHeaderList.add(ColumnHeader(getString(R.string.date)))
//        mColumnHeaderList.add(ColumnHeader(getString(R.string.loan_balance)))
//        mColumnHeaderList.add(ColumnHeader(getString(R.string.repayment)))
//        if (periods != null) {
//            for ((i, period) in periods.withIndex()) {
//                val cells: MutableList<Cell> = ArrayList()
//                cells.add(Cell(period))
//                cells.add(Cell(period))
//                cells.add(Cell(period))
//                mCellList.add(cells)
//                mRowHeaders.add(RowHeader(i + 1))
//            }
//        }
//        loanRepaymentScheduleAdapter?.setAllItems(mColumnHeaderList, mRowHeaders, mCellList)
    }

    /**
     * Shows an empty layout for a loan with `loanId` which has no Repayment Schedule
     *
     * @param loanWithAssociations Contains details about Repayment Schedule
     */
    fun showEmptyRepaymentsSchedule(loanWithAssociations: LoanWithAssociations?) {
        /**
         * Handled this logic in compose UI
         */
//        binding.tvAccountNumber.text = loanWithAssociations?.accountNo
//        binding.tvDisbursementDate.text =
//            DateHelper.getDateAsString(loanWithAssociations?.timeline?.expectedDisbursementDate)
//        binding.tvNumberOfPayments.text = loanWithAssociations?.numberOfRepayments.toString()
//        sweetUIErrorHandler?.showSweetEmptyUI(
//            getString(R.string.repayment_schedule),
//            R.drawable.ic_charges,
//            binding.tvRepaymentSchedule,
//            binding.layoutError.root,
//        )
    }

    /**
     * It is called whenever any error occurs while executing a request
     *
     * @param message Error message that tells the user about the problem.
     */
//    fun showError(message: String?) {
//        if (!Network.isConnected(activity)) {
//            sweetUIErrorHandler?.showSweetNoInternetUI(
//                binding.tvRepaymentSchedule,
//                binding.layoutError.root,
//            )
//        } else {
//            sweetUIErrorHandler?.showSweetErrorUI(
//                message,
//                binding.tvRepaymentSchedule,
//                binding.layoutError.root,
//            )
//            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun retryClicked() {
//        if (Network.isConnected(context)) {
//            sweetUIErrorHandler?.hideSweetErrorLayoutUI(
//                binding.tvRepaymentSchedule,
//                binding.layoutError.root,
//            )
//            viewModel.loanLoanWithAssociations(loanId)
//        } else {
//            Toast.makeText(
//                context,
//                getString(R.string.internet_not_connected),
//                Toast.LENGTH_SHORT,
//            ).show()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        hideProgressBar()
//        _binding = null
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        showUserInterface()
//    }

    companion object {
        fun newInstance(loanId: Long?): LoanRepaymentScheduleFragment {
            val loanRepaymentScheduleFragment = LoanRepaymentScheduleFragment()
            val args = Bundle()
            if (loanId != null) args.putLong(Constants.LOAN_ID, loanId)
            loanRepaymentScheduleFragment.arguments = args
            return loanRepaymentScheduleFragment
        }
    }
}
