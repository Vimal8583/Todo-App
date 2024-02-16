package com.example.todolist
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Model.Item
import com.example.todolist.database.AppDatabase
import com.example.todolist.databinding.ItemListBinding

class ItemListAdapter(var context: Context , var itemlist : MutableList<Item>):
    RecyclerView.Adapter<ItemListAdapter.MyViewHolder>() {
    var itemEditClickListener: ((position: Int, item: Item) -> Unit)? = null
    var itemDeleteClickListener: ((position: Int, item: Item) -> Unit)? = null

    class MyViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var binding = ItemListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var items = itemlist[position]
        holder.binding.tvTitle.setText(items.title)
        holder.binding.tvDescription.setText(items.description)
        holder.binding.ivEdit.setOnClickListener {
          itemEditClickListener?.invoke(position,items)
            notifyDataSetChanged()
        }

        holder.binding.ivDelete.setOnClickListener {
            itemDeleteClickListener?.invoke(position,items)
            notifyDataSetChanged()
        }

    }




    fun setItems(mutableList: MutableList<Item>) {
        this.itemlist = mutableList
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        itemlist.removeAt(position)
        notifyItemRemoved(position)
    }
}