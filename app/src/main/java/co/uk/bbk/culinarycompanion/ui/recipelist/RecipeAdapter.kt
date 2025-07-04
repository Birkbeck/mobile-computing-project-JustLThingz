package co.uk.bbk.culinarycompanion.ui.recipelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion.data.Recipe
import co.uk.bbk.culinarycompanion.databinding.ItemRecipeCardBinding

/**
 * RecyclerView adapter for displaying recipe cards
 * Handles both normal and delete modes
 */
class RecipeAdapter(
    private val onRecipeClick: (Recipe) -> Unit,
    private val onDeleteClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    private var isDeleteMode = false

    fun setDeleteMode(enabled: Boolean) {
        isDeleteMode = enabled
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(
        private val binding: ItemRecipeCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.tvRecipeName.text = recipe.title

            // Show/hide delete button based on mode
            binding.btnDelete.visibility = if (isDeleteMode) View.VISIBLE else View.GONE

            // Set click listeners
            binding.root.setOnClickListener {
                if (!isDeleteMode) {
                    onRecipeClick(recipe)
                }
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(recipe)
            }

            // TODO: Load recipe image when image loading is implemented
            // For now, just show the placeholder background
        }
    }

    /**
     * DiffUtil callback for efficient list updates
     */
    private class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}