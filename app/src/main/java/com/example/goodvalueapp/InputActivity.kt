package com.example.goodvalueapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.example.goodvalueapp.ProductData



class InputActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var dotsContainer: LinearLayout
    private lateinit var calculateButton: Button
    private var itemCount = 1
    private lateinit var adapter: InputPagerAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        
        itemCount = intent.getIntExtra("COUNTER_VALUE", 1)
        
        initViews()
        setupViewPager()
        setupDots()
        setupCalculateButton()
    }
    
    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        dotsContainer = findViewById(R.id.dotsContainer)
        calculateButton = findViewById(R.id.calculateButton)
    }
    
    private fun setupViewPager() {
        adapter = InputPagerAdapter(this, itemCount)
        viewPager.adapter = adapter
        
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })
    }
    
    private fun setupCalculateButton() {
        calculateButton.setOnClickListener {
            val productDataList = mutableListOf<ProductData>()
            
            for (i in 0 until itemCount) {
                val fragment = supportFragmentManager.findFragmentByTag("f$i") as? InputFragment
                fragment?.let {
                    val productData = it.getProductData()
                    if (productData != null) {
                        productDataList.add(productData)
                    }
                }
            }
            
            if (productDataList.isNotEmpty()) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putParcelableArrayListExtra("PRODUCT_DATA_LIST", ArrayList(productDataList))
                startActivity(intent)
            }
        }
    }
    
    private fun setupDots() {
        dotsContainer.removeAllViews()
        
        for (i in 0 until itemCount) {
            val dot = View(this)
            val layoutParams = LinearLayout.LayoutParams(24, 24)
            layoutParams.setMargins(8, 0, 8, 0)
            dot.layoutParams = layoutParams
            dot.background = getDrawable(R.drawable.dot_inactive)
            dotsContainer.addView(dot)
        }
        
        updateDots(0)
    }
    
    private fun updateDots(currentPosition: Int) {
        for (i in 0 until dotsContainer.childCount) {
            val dot = dotsContainer.getChildAt(i)
            if (i == currentPosition) {
                dot.background = getDrawable(R.drawable.dot_active)
                val layoutParams = dot.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = 32
                layoutParams.height = 32
                dot.layoutParams = layoutParams
            } else {
                dot.background = getDrawable(R.drawable.dot_inactive)
                val layoutParams = dot.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = 24
                layoutParams.height = 24
                dot.layoutParams = layoutParams
            }
        }
    }
}

class InputPagerAdapter(fragmentActivity: FragmentActivity, private val itemCount: Int) : 
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        return InputFragment.newInstance(position + 1)
    }
}

class InputFragment : Fragment() {
    private lateinit var productNameInput: EditText
    private lateinit var priceInput: EditText
    private lateinit var weightInput: EditText

    companion object {
        fun newInstance(itemNumber: Int): InputFragment {
            val fragment = InputFragment()
            val args = Bundle()
            args.putInt("ITEM_NUMBER", itemNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemNumber = arguments?.getInt("ITEM_NUMBER", 1) ?: 1
        val itemTitle: TextView = view.findViewById(R.id.itemTitle)
        itemTitle.text = "${itemNumber}個目"

        productNameInput = view.findViewById(R.id.productNameInput)
        priceInput = view.findViewById(R.id.priceInput)
        weightInput = view.findViewById(R.id.weightInput)
    }

    fun getProductData(): ProductData? {
        val name = productNameInput.text.toString().trim()
        val priceText = priceInput.text.toString().trim()
        val weightText = weightInput.text.toString().trim()

        if (name.isEmpty() || priceText.isEmpty() || weightText.isEmpty()) {
            return null
        }

        return try {
            val price = priceText.toDouble()
            val weight = weightText.toDouble()
            ProductData(name, price, weight)
        } catch (e: NumberFormatException) {
            null
        }
    }
}