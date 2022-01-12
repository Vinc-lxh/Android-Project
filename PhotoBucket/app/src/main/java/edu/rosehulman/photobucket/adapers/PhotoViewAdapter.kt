package edu.rosehulman.photobucket.adapers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import edu.rosehulman.photobucket.R
import edu.rosehulman.photobucket.photoview.Photo
import edu.rosehulman.photobucket.photoview.PhotoViewModel
import edu.rosehulman.photobucket.ui.PhotoListFragment

class PhotoViewAdapter(fragment: PhotoListFragment):RecyclerView.Adapter<PhotoViewAdapter.PhotoViewHolder>() {
    val model = ViewModelProvider(fragment.requireActivity()).get(PhotoViewModel::class.java)
    //requireActivity() for fragment, this for in a activity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_layout,parent,false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(model.getPhotoAt(position)) //build the view, scrolling and bind over and over again without reconstruction
    }

    override fun getItemCount(): Int {
        return model.size() //its gonna calculate the
    }

    fun addPhoto(photo: Photo?) {
        model.addPhoto(photo)
        Log.d("PV","Size: ${model.size()}")
        notifyDataSetChanged()
    }

    //define ViewHolder Class
    inner class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener{
                //navigate to edit
                model.updatePos(adapterPosition)//tell what current position
                itemView.findNavController().navigate(R.id.nav_photos)
            }
            itemView.setOnLongClickListener {
                model.updatePos(adapterPosition)
                model.toggleCurrentPhoto()
                notifyItemChanged(adapterPosition)//only notify item
                true
            }
        }

        val photoCaption:TextView = itemView.findViewById(R.id.smallcaption)
        val photoImage: ImageView = itemView.findViewById(R.id.small_url_image)
        val photoIcon: ImageView = itemView.findViewById(R.id.small_icon)

        fun bind(photo: Photo) {
            photoCaption.text = photo.caption.subSequence(0,5)
            photoImage.load(photo.URL) {
                crossfade(true)
                transformations(RoundedCornersTransformation())
            }
            if(photo.isSelected){
                photoIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                photoIcon.setImageResource(R.drawable.ic_baseline_image_24)
            }

        }
    }

}