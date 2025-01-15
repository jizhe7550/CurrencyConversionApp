package com.joeji.core.data.di.qualifier

import org.koin.core.qualifier.Qualifier

object IoDispatcherQualifier : Qualifier {
    override val value: String
        get() = "IoDispatcher"
}

object DefaultDispatcherQualifier : Qualifier {
    override val value: String
        get() = "DefaultDispatcher"
}

object MainDispatcherQualifier : Qualifier {
    override val value: String
        get() = "MainDispatcher"
}

object MainImmediateDispatcherQualifier : Qualifier {
    override val value: String
        get() = "MainImmediateDispatcher"
}