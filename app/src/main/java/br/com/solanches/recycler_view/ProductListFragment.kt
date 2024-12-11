import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.solanches.recycler_view.ProductEditFragment
import br.com.solanches.recycler_view.R
import br.com.solanches.recycler_view.adapter.ProductAdapter
import br.com.solanches.recycler_view.data.Product

class ProductListFragment : Fragment() {

    val products = mutableListOf(
        Product(R.drawable.ic_placeholder_image, "Product A", 10.99),
        Product(R.drawable.ic_placeholder_image, "Product B", 20.99),
        Product(R.drawable.ic_placeholder_image, "Product C", 30.99),
        Product(R.drawable.ic_placeholder_image, "Product D", 40.99),
        Product(R.drawable.ic_placeholder_image, "Product E", 50.99),
        Product(R.drawable.ic_placeholder_image, "Product F", 60.99),
    )

    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewProducts)
        val fabAddProduct: View = view.findViewById(R.id.fabAddProduct)

        // Initialize the adapter and set it to the property
        adapter = ProductAdapter(
            products = products,
            onEdit = { product -> navigateToEditFragment(product) },
            onDelete = { product -> deleteProduct(product) }
        )

        // Set LayoutManager and Adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fabAddProduct.setOnClickListener { navigateToEditFragment(null) }

        // Handle result from ProductEditFragment
        parentFragmentManager.setFragmentResultListener("productResult", viewLifecycleOwner) { _, bundle ->
            val updatedProduct = bundle.getParcelable<Product>("product") ?: return@setFragmentResultListener

            // Find the product by object identity
            val index = products.indexOf(updatedProduct)

            if (index != -1) {
                // Edit existing product
                products[index] = updatedProduct
                adapter.notifyItemChanged(index)
            } else {
                // Add new product
                products.add(updatedProduct)
                adapter.notifyItemInserted(products.size - 1)
            }
        }

    }

    private fun navigateToEditFragment(product: Product?) {
        val fragment = ProductEditFragment().apply {
            arguments = Bundle().apply { putParcelable("product", product) }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun deleteProduct(product: Product) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete ${product.name}?")
            .setPositiveButton("Yes") { _, _ ->
                // Proceed with deletion
                val index = products.indexOf(product)
                if (index != -1) {
                    products.removeAt(index)
                    adapter.notifyItemRemoved(index)
                }
            }
            .setNegativeButton("No", null) // Dismiss the dialog
            .create()
            .show()
    }

}

