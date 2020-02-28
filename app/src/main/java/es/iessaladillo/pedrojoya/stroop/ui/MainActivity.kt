package es.iessaladillo.pedrojoya.stroop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), OnToolbarAvailableListener {

    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    override fun onToolbarCreated(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))
    }

    override fun onToolbarDestroyed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(AppBarConfiguration(navController.graph)) || super.onSupportNavigateUp()
    }


}
