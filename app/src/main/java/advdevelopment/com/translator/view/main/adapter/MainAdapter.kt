package advdevelopment.com.translator.view.main.adapter

import advdevelopment.com.model.data.userdata.DataModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import advdevelopment.com.translator.R
import advdevelopment.com.translator.utils.convertMeaningsToSingleString
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*

class MainAdapter(private var onListItemClickListener: OnListItemClickListener) :
        RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {
    
    private var data: List<DataModel> = arrayListOf()
    
    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }
    
    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }
    
    override fun getItemCount(): Int {
        return data.size
    }
    
    //ViewHolder должен имплементировать интерфейс LayoutContainer, иначе view не будет браться из кеша
    inner class RecyclerItemViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text =
                        convertMeaningsToSingleString(data.meanings)
                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }
    
    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClickListener.onItemClick(listItemData)
    }
    
    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}
