package com.example.wallpaperapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.domain.entity.WallpaperLink

class ImagesRecyclerViewAdapter(private var dataSet: List<WallpaperLink>) :
    RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AppCompatImageView

        init {
            // Define click listener for the ViewHolder's View
            imageView = view.findViewById(R.id.imageView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

//    fun setItems(dataSet: List<WallpaperLink>) {
//        this.dataSet = dataSet // one item less you will be able to -> animation
//        notifyDataSetChanged() // less efficient you're chanign the entire list
//    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.imageView.text = dataSet[position]

        // update the imageview with glide
        Glide.with(viewHolder.imageView.context)
            .load(dataSet[position].wallpaperLink)
            .into(viewHolder.imageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
