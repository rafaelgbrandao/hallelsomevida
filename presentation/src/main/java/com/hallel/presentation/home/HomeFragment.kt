package com.hallel.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hallel.presentation.R
import com.hallel.presentation.base.BaseFragment
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment() {

    private val viewModel by inject<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarVisible(true)
        setActionBarTitle(getString(R.string.title_home))
    }
}