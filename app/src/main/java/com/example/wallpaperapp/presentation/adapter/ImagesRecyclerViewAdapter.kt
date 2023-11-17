package com.example.wallpaperapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.domain.entities.PicSumWallpaperURL

class ImagesRecyclerViewAdapter(
    private var dataSet: List<PicSumWallpaperURL>,
    private var onClickListener: WallPaperClickListener
) : RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder>() {


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

//    fun setItems(dataSet: Array<String>) {
//        this.dataSet = dataSet // one item less you will be able to -> animation
//        notifyDataSetChanged() // less efficient you're chanign the entire list
//    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = dataSet[position]
        Glide.with(viewHolder.imageView.context).load(currentItem.wallpaperURL)
            .into(viewHolder.imageView)
        // todo assign the image from the list
        viewHolder.imageView.setOnClickListener {
            onClickListener.onWallpaperClick(currentItem.wallpaperURL)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}

interface WallPaperClickListener {
    fun onWallpaperClick(wallpaperLink: String)
}
