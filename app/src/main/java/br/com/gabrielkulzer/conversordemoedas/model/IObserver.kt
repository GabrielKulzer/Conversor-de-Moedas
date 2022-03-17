package br.com.gabrielkulzer.conversordemoedas.model

interface IObserver {
    fun updateUI(data:MutableMap<String,Double>)
}