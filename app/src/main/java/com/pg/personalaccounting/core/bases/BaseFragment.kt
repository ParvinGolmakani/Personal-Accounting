package com.pg.personalaccounting.core.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment(private val viewID: Int) : Fragment() {



    lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = LayoutInflater.from(context)
            .inflate(viewID, container, false)
        mView = inflater.inflate(viewID, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterLoadView()
    }

    abstract fun afterLoadView()

    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}