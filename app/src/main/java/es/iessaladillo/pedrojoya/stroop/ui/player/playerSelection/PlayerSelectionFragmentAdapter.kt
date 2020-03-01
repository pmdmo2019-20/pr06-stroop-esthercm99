package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import kotlinx.android.extensions.LayoutContainer
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlinx.android.synthetic.main.player_item.*

class PlayerSelectionFragmentAdapter() : RecyclerView.Adapter<PlayerSelectionFragmentAdapter.ViewHolder>() {
    var dataList: List<Player> = mutableListOf()
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(dataList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.player_item, parent, false)
        return ViewHolder(itemView)
    }

    fun submitList(newList : List<Player>){
        dataList = newList
        notifyDataSetChanged()
    }
    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init { containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) } }
        fun bind(player: Player) {
            player.run {
                imgAvatar.setImageResource(image.toInt())
                usernamePlayer.text = username
            }
        }
    }
}
