package es.iessaladillo.pedrojoya.stroop.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.ui.dialogInfo.DialogInfoFragment
import kotlinx.android.synthetic.main.dashboard_fragment.*

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
    }
    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        toolbar.inflateMenu(R.menu.menu_info)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnInfoMenu -> DialogInfoFragment.newInstance(
                    getString(R.string.settings_help_description)
                )
                    .show(requireFragmentManager(), "InfoDialog")
            }
            true
        }
    }

}