package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.entity.Page
import kotlinx.android.synthetic.main.assistant_fragment.*

class AssistantFragment : Fragment() {

    private lateinit var assistantAdapter: AssistantFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.assistant_fragment, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        setupAdapter()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupToolbar() { (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar) }
    private fun setupAdapter() {
        val pages = listOf(
            Page(
                R.color.stroopOption,
                R.drawable.logo,
                getString(R.string.assistant_stroopDescription)
            ),
            Page(
                R.color.playOption,
                R.drawable.ic_play_black_24dp,
                getString(R.string.assistant_playDescription)
            ),
            Page(
                R.color.settingsOption,
                R.drawable.ic_settings_black_24dp,
                getString(R.string.assistant_settingsDescription)
            ),
            Page(
                R.color.rankingOption,
                R.drawable.ic_ranking_black_48dp,
                getString(R.string.assistant_rankingDescription)
            ),
            Page(
                R.color.assistantOption,
                R.drawable.ic_assistant_black_24dp,
                getString(R.string.assistant_assistantDescription)
            ),
            Page(
                R.color.playerOption,
                R.drawable.ic_player_black_24dp,
                getString(R.string.assistant_playerDescription)
            ),
            Page(
                R.color.aboutOption,
                R.drawable.ic_about_black_24dp,
                getString(R.string.assistant_aboutDescription)
            ),
            Page(
                R.color.finishOption,
                R.drawable.ic_finish_black_24dp,
                getString(R.string.assistant_finishDescription)
            )
        )
        assistantAdapter = AssistantFragmentAdapter(pages).also {
            it.onItemClickListener = { activity?.onBackPressed() }
        }
    }
    private fun setupViewPager() { viewPager.adapter = assistantAdapter }
    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }
}
