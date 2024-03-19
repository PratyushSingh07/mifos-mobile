package org.mifos.mobile.ui.loan_account

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.mifos.mobile.R
import org.mifos.mobile.api.local.PreferencesHelper
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.models.accounts.loan.LoanWithAssociations
import org.mifos.mobile.ui.activities.LoanAccountContainerActivity
import org.mifos.mobile.ui.activities.SavingsAccountContainerActivity
import org.mifos.mobile.ui.activities.base.BaseActivity
import org.mifos.mobile.ui.enums.AccountType
import org.mifos.mobile.ui.enums.ChargeType
import org.mifos.mobile.ui.enums.LoanState
import org.mifos.mobile.ui.fragments.ClientChargeFragment
import org.mifos.mobile.ui.fragments.GuarantorListFragment
import org.mifos.mobile.ui.fragments.LoanAccountSummaryFragment
import org.mifos.mobile.ui.fragments.LoanAccountTransactionFragment
import org.mifos.mobile.ui.fragments.LoanAccountWithdrawFragment
import org.mifos.mobile.ui.fragments.LoanApplicationFragment
import org.mifos.mobile.ui.fragments.LoanRepaymentScheduleFragment
import org.mifos.mobile.ui.fragments.QrCodeDisplayFragment
import org.mifos.mobile.ui.fragments.SavingsMakeTransferFragment
import org.mifos.mobile.ui.fragments.base.BaseFragment
import org.mifos.mobile.ui.savings_account.SavingsAccountDetailScreen
import org.mifos.mobile.utils.*
import org.mifos.mobile.utils.ParcelableAndSerializableUtils.getCheckedParcelable
import javax.inject.Inject

/*
~This project is licensed under the open source MPL V2.
~See https://github.com/openMF/self-service-app/blob/master/LICENSE.md
*/ /**
 * @author Vishwajeet
 * @since 19/08/16
 */
@AndroidEntryPoint
class LoanAccountsDetailFragment : BaseFragment() {
    @Inject
    lateinit var preferencesHelper: PreferencesHelper
    private val viewModel: LoanAccountsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            viewModel.setLoanId(arguments?.getLong(Constants.LOAN_ID))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel.loadLoanAccountDetails(viewModel.loanId)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MifosMobileTheme {
                    LoanAccountDetailScreen(
                        uiState = viewModel.loanUiState.value,
                        navigateBack = { activity?.finish() },
                        viewGuarantor = { viewGuarantor() },
                        updateLoan = { updateLoan() },
                        withdrawLoan = { withdrawLoan() },
                        viewLoanSummary = {  onLoanSummaryClicked() },
                        viewCharges = { chargesClicked() },
                        viewRepaymentSchedule = { onRepaymentScheduleClicked() },
                        viewTransactions = { onTransactionsClicked() },
                        viewQr = {  onQrCodeClicked() },
                        makePayment = { onMakePaymentClicked() },
                        retryConnection = { retryClicked() }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? BaseActivity)?.hideToolbar()
    }

    /**
     * Opens [SavingsMakeTransferFragment] to Make a payment for loan account with given
     * `loanId`
     */
    private fun onMakePaymentClicked() {
        (activity as BaseActivity?)?.replaceFragment(
            SavingsMakeTransferFragment.newInstance(
                viewModel.loanId,
                viewModel.loanWithAssociations?.summary?.totalOutstanding,
                Constants.TRANSFER_PAY_TO,
            ),
            true,
            R.id.container,
        )
    }

    /**
     * Opens [LoanAccountSummaryFragment]
     */
    private fun onLoanSummaryClicked() {
        (activity as BaseActivity?)?.replaceFragment(
            LoanAccountSummaryFragment.newInstance(
                viewModel.loanWithAssociations,
            ),
            true,
            R.id.container,
        )
    }

    /**
     * Opens [LoanRepaymentScheduleFragment]
     */
    private fun onRepaymentScheduleClicked() {
        (activity as BaseActivity?)?.replaceFragment(
            LoanRepaymentScheduleFragment.newInstance(
                viewModel.loanId,
            ),
            true,
            R.id.container,
        )
    }

    /**
     * Opens [LoanAccountTransactionFragment]
     */
    private fun onTransactionsClicked() {
        (activity as BaseActivity?)?.replaceFragment(
            LoanAccountTransactionFragment.newInstance(
                viewModel.loanId,
            ),
            true,
            R.id.container,
        )
    }

    private fun chargesClicked() {
        (activity as BaseActivity?)?.replaceFragment(
            ClientChargeFragment.newInstance(
                viewModel.loanWithAssociations?.id?.toLong(),
                ChargeType.LOAN,
            ),
            true,
            R.id.container,
        )
    }

    private fun onQrCodeClicked() {
        val accountDetailsInJson = QrCodeGenerator.getAccountDetailsInString(
            viewModel.loanWithAssociations?.accountNo,
            preferencesHelper.officeName,
            AccountType.LOAN,
        )
        (activity as BaseActivity?)?.replaceFragment(
            QrCodeDisplayFragment.newInstance(
                accountDetailsInJson,
            ),
            true,
            R.id.container,
        )
    }

    private fun retryClicked() {
        if (Network.isConnected(context)) {
            viewModel.loadLoanAccountDetails(viewModel.loanId)
        } else {
            Toast.makeText(
                context,
                getString(R.string.internet_not_connected),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun updateLoan() {
        (activity as BaseActivity?)?.replaceFragment(
            LoanApplicationFragment.newInstance(
                LoanState.UPDATE,
                viewModel.loanWithAssociations,
            ),
            true,
            R.id.container,
        )
    }

    private fun withdrawLoan() {
        (activity as BaseActivity?)?.replaceFragment(
            LoanAccountWithdrawFragment.newInstance(
                viewModel.loanWithAssociations,
            ),
            true,
            R.id.container,
        )
    }

    private fun viewGuarantor() {
        (activity as BaseActivity?)?.replaceFragment(
            GuarantorListFragment.newInstance(
                viewModel.loanId,
            ),
            true,
            R.id.container,
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(loanId: Long): LoanAccountsDetailFragment {
            val fragment = LoanAccountsDetailFragment()
            val args = Bundle()
            args.putLong(Constants.LOAN_ID, loanId)
            fragment.arguments = args
            return fragment
        }
    }
}
