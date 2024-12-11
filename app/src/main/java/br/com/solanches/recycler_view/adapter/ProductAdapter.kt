package br.com.solanches.recycler_view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.solanches.recycler_view.data.Product
import br.com.solanches.recycler_view.databinding.ItemProductBinding

class ProductAdapter(
    private val products: List<Product>,
    private val onEdit: (Product) -> Unit,
    private val onDelete: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // ViewHolder class using binding
    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            textViewProductName.text = product.name
            textViewProductPrice.text = "$${product.price}"
            imageViewThumbnail.setImageResource(product.imageResId)

            // Set up click listeners
            buttonEdit.setOnClickListener { onEdit(product) }
            buttonDelete.setOnClickListener { onDelete(product) }
        }
    }

    override fun getItemCount(): Int = products.size
}
