package com.apps.ahfreelancing.toprated.presentation.viewModel

import android.arch.lifecycle.ViewModel
import com.apps.ahfreelancing.toprated.domain.usecase.BaseUseCase

/**
 * Created by Ahmed Hassan on 2/14/2019.
 */

/**
 * BaseViewModel's generic final type is set as The BaseUseCase Type automatically
 * Once Any ViewModel inherit from BaseViewModel<T> it determines the Type
*/
abstract class BaseViewModel<T>(val useCase: BaseUseCase<T>) : ViewModel() {
}