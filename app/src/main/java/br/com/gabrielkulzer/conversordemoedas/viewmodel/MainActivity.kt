package br.com.gabrielkulzer.conversordemoedas.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import br.com.gabrielkulzer.conversordemoedas.model.IObserver
import br.com.gabrielkulzer.conversordemoedas.model.Price
import br.com.gabrielkulzer.conversordemoedas.repository.RateAPI
import br.com.gabrielkulzer.myapplication.R
import br.com.gabrielkulzer.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),IObserver {
    private lateinit var binding:ActivityMainBinding
    private var dollarPrice:Price = Price()
    private var euroPrice:Price = Price()
    private lateinit var alert:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        alert = AlertDialog.Builder(this).create()
        alert.setTitle("Aguarde...")
        alert.setMessage("Sua solicitação está sendo processada.")

        binding.dollarPrice = dollarPrice
        binding.euroPrice = euroPrice
        binding.lifecycleOwner = this

        binding.btnConvert.setOnClickListener{
            alert.show()
            val rateAPI = RateAPI()
            rateAPI.getCurrency(applicationContext,this)
        }
    }

    override fun updateUI(data: MutableMap<String, Double>) {
        val realValue = binding.edtReal.editText?.text.toString().toDouble()
        dollarPrice.setPrice(
            realValue / data["dollar"]!!
        )
        euroPrice.setPrice(
            realValue / data["euro"]!!
        )
        alert.dismiss()
    }
}