package com.ldlywt.androidadvancedemo.ui.fragment.maintab

import android.content.Intent
import android.os.Bundle
import com.ldlywt.androidadvancedemo.R
import com.ldlywt.androidadvancedemo.ui.TestActivity
import com.ldlywt.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * <pre>
 * author : lex
 * e-mail : ldlywt@163.com
 * time   : 2018/08/28
 * desc   :
 * version: 1.0
</pre> *
 */
class MineFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initData(savedInstanceState: Bundle?) {}
    override fun initView() {
        startActivity.setOnClickListener {
            startActivity(Intent(activity, TestActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(from: String?): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            bundle.putString("from", from)
            fragment.arguments = bundle
            return fragment
        }
    }
}