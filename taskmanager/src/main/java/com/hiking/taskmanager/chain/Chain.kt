package com.hiking.taskmanager.chain

open class Chain(private var interceptors: MutableList<Interceptor>) {

    companion object {
        fun create(initialCapacity: Int = 0): Builder {
            return Builder(initialCapacity)
        }
    }


    private var index: Int = 0
    fun process() {
        when (index) {
            in interceptors.indices -> {
                val interceptor = interceptors[index]
                index++
                interceptor.intercept(this)
            }
            else -> {
                clearAllInterceptors()
                chainEnd()
            }
        }
    }

    open fun chainEnd() {

    }


    private fun clearAllInterceptors() {
        interceptors.clear()
    }

}

class Builder(private val initialCapacity: Int = 0) {
    private val interceptors by lazy(LazyThreadSafetyMode.NONE) {
        ArrayList<Interceptor>(
            initialCapacity
        )
    }


    fun addInterceptor(interceptor: Interceptor): Builder {
        if (!interceptors.contains(interceptor)) {
            interceptors.add(interceptor)
        }
        return this
    }


    fun build(): Chain {
        return Chain(interceptors)
    }

    fun <T : Chain?> build(service: Class<T>): T? {
        try {
            return service.getConstructor(MutableList::class.java).newInstance(interceptors)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}


interface Interceptor {
    fun intercept(chain: Chain)
}