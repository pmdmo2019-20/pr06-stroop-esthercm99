package es.iessaladillo.pedrojoya.stroop.ui.dialogInfo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import es.iessaladillo.pedrojoya.stroop.R

class DialogInfoFragment : DialogFragment() {

    private var desc: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.help_title)
            .setMessage(desc)
            .setPositiveButton(R.string.help_accept) { dialog, _ ->  dialog.dismiss()}
            .create()
    }

    companion object {
        fun newInstance(desc: String): DialogInfoFragment =
            DialogInfoFragment().apply {
                this.desc = desc
            }
    }
}