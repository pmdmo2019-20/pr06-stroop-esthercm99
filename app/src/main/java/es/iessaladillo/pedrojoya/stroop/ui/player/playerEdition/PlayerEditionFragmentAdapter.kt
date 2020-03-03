package es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.avatar_item.*
import es.iessaladillo.pedrojoya.stroop.avatars

class PlayerEditionFragmentAdapter() : RecyclerView.Adapter<PlayerEditionFragmentAdapter.ViewHolder>() {

    private var dataList: List<Int> = avatars
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun getItemCount(): Int = dataList.size
    override fun getItemId(position: Int): Long = dataList[position].toLong()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(dataList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.avatar_item, parent, false)
        return ViewHolder(itemView)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init { containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) } }
        fun bind(avatar: Int) = imgAvatar.setImageResource(avatar)
    }
}
