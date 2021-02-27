package com.lain.baseapp.view.activity

import android.os.Bundle
import com.lain.baseapp.R
import com.lain.baseapp.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.loader.*
import javax.inject.Inject

/**
 * This class is the main activity.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    /**
     * The base view model.
     */
    @Inject
    lateinit var baseViewModel: BaseViewModel

    /*==============================================================================================
    ANDROID METHODS
    ==============================================================================================*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseViewModel!!.loading.observe(this, {
            showLoader(loader = loader, loading =  it)
        })

        baseViewModel!!.error.observe(this, {
            handleApiError(error = it)
        })

        baseViewModel!!.model.observe(this){
            //TODO do something
        }

        baseViewModel.requestGet(name = "ditto")
    }
}