package br.com.solanches.recycler_view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import br.com.solanches.recycler_view.data.Product
import br.com.solanches.recycler_view.databinding.FragmentProductEditBinding

class ProductEditFragment : Fragment() {

    private var _binding: FragmentProductEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the product passed from ProductListFragment
        var product = arguments?.getParcelable<Product>("product")

        // If editing an existing product, populate the fields
        product?.let {
            binding.editTextProductName.setText(it.name)
            binding.editTextProductPrice.setText(it.price.toString())
        }

        // Handle Save button click
        binding.buttonSaveProduct.setOnClickListener {
            val name = binding.editTextProductName.text.toString()
            val price = binding.editTextProductPrice.text.toString().toDoubleOrNull()

            if (name.isBlank() || price == null) {
                Toast.makeText(requireContext(), "Please enter valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if we are editing an existing product
            if (product != null) {
                // Edit mode: Directly update the existing product
                product.name = name
                product.price = price
            } else {
                // Add mode: Create a new product
                val newProduct = Product(
                    imageResId = R.drawable.ic_placeholder_image,
                    name = name,
                    price = price
                )

                // Pass the new product back
                parentFragmentManager.setFragmentResult(
                    "productResult",
                    Bundle().apply { putParcelable("product", newProduct) }
                )
            }

            // Navigate back
            parentFragmentManager.popBackStack()
        }


        binding.buttonCancelProduct.setOnClickListener {
            // Simply navigate back without saving
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}