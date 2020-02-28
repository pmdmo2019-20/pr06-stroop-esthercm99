package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.entity.Page
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.assistant_page_fragment.*
import kotlinx.android.synthetic.main.assistant_page_fragment.view.*

class AssistantFragmentAdapter(val pageList: List<Page>) : RecyclerView.Adapter<AssistantFragmentAdapter.ViewHolder>() {

    private var currentPosition : Int =  0
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun getItemCount(): Int = pageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentPosition = position
        holder.bind(pageList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.assistant_page_fragment, parent, false)

        return ViewHolder(itemView)
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {  btnFinish.setOnClickListener { onItemClickListener?.invoke(adapterPosition) } }

        fun bind(page: Page) {
            page.run {
                containerView.txtDesc.text = page.desc
                containerView.icon.setImageResource(page.img)
                containerView.setBackgroundResource(page.color)

                if (adapterPosition == pageList.size - 1) {
                    containerView.btnFinish.visibility = View.VISIBLE
                } else {
                    containerView.btnFinish.visibility = View.INVISIBLE
                }
            }
        }

    }
}