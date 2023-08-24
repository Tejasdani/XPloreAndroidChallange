package com.xplor.android.challenge.di.keys

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class MainViewModelKey(val value: KClass<out ViewModel>)
