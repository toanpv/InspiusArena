package vn.toanpv.sample.arena.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<T : ViewBinding>(private val initVb: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {

    private var _vb: T? = null
    val vb: T
        get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = initVb.invoke(inflater, container, false)
        return vb.root
    }

    override fun onDestroyView() {
        _vb = null
        super.onDestroyView()
    }
}
