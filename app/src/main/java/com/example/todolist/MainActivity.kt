package com.example.todolist
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Model.Item
import com.example.todolist.database.AppDatabase
import com.example.todolist.databinding.ActivityMainBinding
import java.lang.Exception
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var itemList = mutableListOf<Item>()
    private lateinit var itemListAdapter: ItemListAdapter
    var db: AppDatabase? = null
    private var item: Item? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemListAdapter = ItemListAdapter(this,itemList)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter = itemListAdapter
        db = AppDatabase.getInstance(this)

        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item", Item::class.java)
        } else {
            intent.getParcelableExtra("item")
        }


        item?.let {
            // update
            binding.btnAdd.text = "Update cuisine"
            binding.etTitle.setText(it.title)
            binding.etDescription.setText(it.description)
        }
        itemListAdapter.itemDeleteClickListener = {
                position, item ->
            showAlertDialog(item,position)
        }
        binding.btnAdd.setOnClickListener {
            var title = binding.etTitle.text.toString().trim()
            var desc = binding.etDescription.text.toString().trim()

            updateRecord(title, desc)

        }
        itemListAdapter.itemEditClickListener= {position, item ->
            intent = Intent(this,MainActivity::class.java)
            intent.putExtra("item",item)
            startActivity(intent)
            itemListAdapter
        }


    }
    private fun readItemList(){
        itemList = AppDatabase.getInstance(this)?.todoDao()!!.getAllItems()
        itemListAdapter.setItems(itemList)
    }

    override fun onResume() {
        super.onResume()
        readItemList()
    }

    private fun showAlertDialog(item: Item, position: Int) {

        var builder = AlertDialog.Builder(this)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure want to delete this task?")
        builder.setPositiveButton("Delete",DialogInterface.OnClickListener{dialog, which ->
            AppDatabase.getInstance(this)?.todoDao()!!.deleteItem(item)
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
            itemListAdapter.deleteItem(position)

        })
        builder.setNegativeButton("cancel", DialogInterface.OnClickListener{dialog, which ->

        })
        var dialog = builder.create()
        dialog.show()

    }
    private fun updateRecord(title: String, desc: String) {
        var message = ""

        thread(start = true) {

            var todoObject = Item(
                title = title,
                description = desc,

                id = if (item != null) item!!.id else 0,
                createdAt = if (item != null) item!!.createdAt else System.currentTimeMillis(),
            )

            try {
                if(item!=null){
                    //update
                    db!!.todoDao().updateItem(todoObject)
                    message = "Record updated successfully"
                }else{
                    //add
                    db!!.todoDao().insertItem(todoObject)
                    message = "Record added successfully"
                }

                runOnUiThread {
                    Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}